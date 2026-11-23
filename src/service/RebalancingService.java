package service;

import data.DataStore;
import enums.FundType;
import enums.RebalanceAction;
import model.Fund;
import model.Investor;
import model.RiskProfile;
import model.SIP;

import java.util.ArrayList;
import java.util.List;

/**
 * RebalancingService — checks portfolio allocation vs. risk profile
 * and recommends BUY / SELL / HOLD actions.
 */
public class RebalancingService {

    private DataStore          ds;
    private TransactionService txnService;

    public RebalancingService(DataStore ds, TransactionService txnService) {
        this.ds         = ds;
        this.txnService = txnService;
    }

    // ── Inner class: one rebalance suggestion ────────────────────────
    public static class RebalanceSuggestion {
        public final String          fundId;
        public final String          fundName;
        public final double          currentPercent;
        public final double          targetPercent;
        public final double          deviation;
        public final RebalanceAction action;
        public final String          reason;

        public RebalanceSuggestion(String fundId, String fundName,
                                   double currentPercent, double targetPercent,
                                   RebalanceAction action, String reason) {
            this.fundId          = fundId;
            this.fundName        = fundName;
            this.currentPercent  = currentPercent;
            this.targetPercent   = targetPercent;
            this.deviation       = currentPercent - targetPercent;
            this.action          = action;
            this.reason          = reason;
        }

        @Override
        public String toString() {
            return String.format("  %-10s %-25s | Current: %5.1f%% | Target: %5.1f%% | Dev: %+5.1f%% | %-4s — %s",
                    fundId, fundName, currentPercent, targetPercent, deviation, action, reason);
        }
    }

    // ── Analyse and suggest rebalancing ─────────────────────────────
    public List<RebalanceSuggestion> analysePortfolio(String investorId) {
        Investor investor = ds.findInvestorById(investorId);
        if (investor == null)
            throw new IllegalArgumentException("Investor not found: " + investorId);

        RiskProfile rp = investor.getRiskProfile();
        if (rp == null)
            throw new IllegalStateException("No risk profile set for investor: " + investorId);

        // Compute current portfolio value per fund type
        double totalEquityValue = 0, totalDebtValue = 0, totalValue = 0;
        for (Fund fund : ds.getFunds()) {
            double units = txnService.getTotalUnitsHeld(investorId, fund.getFundId());
            if (units <= 0) continue;
            double value = units * fund.getCurrentNAV();
            totalValue += value;
            if (fund.getFundType() == FundType.EQUITY || fund.getFundType() == FundType.HYBRID) {
                totalEquityValue += value;
            } else {
                totalDebtValue += value;
            }
        }

        List<RebalanceSuggestion> suggestions = new ArrayList<>();
        if (totalValue == 0) {
            System.out.println("⚠️  No holdings found for investor: " + investorId);
            return suggestions;
        }

        double currentEquityPct = (totalEquityValue / totalValue) * 100;
        double currentDebtPct   = (totalDebtValue   / totalValue) * 100;
        double targetEquityPct  = rp.getEquityPercent();
        double targetDebtPct    = rp.getDebtPercent();
        double threshold        = 5.0; // 5% deviation triggers action

        // Equity suggestion
        RebalanceAction equityAction;
        String equityReason;
        if (currentEquityPct > targetEquityPct + threshold) {
            equityAction = RebalanceAction.SELL;
            equityReason = "Over-allocated in equity by " + String.format("%.1f", currentEquityPct - targetEquityPct) + "%";
        } else if (currentEquityPct < targetEquityPct - threshold) {
            equityAction = RebalanceAction.BUY;
            equityReason = "Under-allocated in equity by " + String.format("%.1f", targetEquityPct - currentEquityPct) + "%";
        } else {
            equityAction = RebalanceAction.HOLD;
            equityReason = "Within acceptable range (±5%)";
        }
        suggestions.add(new RebalanceSuggestion("EQUITY", "Equity Funds",
                currentEquityPct, targetEquityPct, equityAction, equityReason));

        // Debt suggestion
        RebalanceAction debtAction;
        String debtReason;
        if (currentDebtPct > targetDebtPct + threshold) {
            debtAction = RebalanceAction.SELL;
            debtReason = "Over-allocated in debt by " + String.format("%.1f", currentDebtPct - targetDebtPct) + "%";
        } else if (currentDebtPct < targetDebtPct - threshold) {
            debtAction = RebalanceAction.BUY;
            debtReason = "Under-allocated in debt by " + String.format("%.1f", targetDebtPct - currentDebtPct) + "%";
        } else {
            debtAction = RebalanceAction.HOLD;
            debtReason = "Within acceptable range (±5%)";
        }
        suggestions.add(new RebalanceSuggestion("DEBT", "Debt Funds",
                currentDebtPct, targetDebtPct, debtAction, debtReason));

        return suggestions;
    }

    // ── Print rebalance report ───────────────────────────────────────
    public void printRebalanceReport(String investorId) {
        List<RebalanceSuggestion> suggestions = analysePortfolio(investorId);
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  PORTFOLIO REBALANCING REPORT — Investor: " + investorId);
        System.out.println("═══════════════════════════════════════════════════════");
        for (RebalanceSuggestion s : suggestions) System.out.println(s);
        System.out.println("═══════════════════════════════════════════════════════");
    }
}
