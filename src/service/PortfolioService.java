package service;

import data.DataStore;
import model.*;

import java.util.List;

/**
 * PortfolioService — builds and displays portfolio snapshots for an investor.
 */
public class PortfolioService {

    private DataStore          ds;
    private TransactionService txnService;

    public PortfolioService(DataStore ds, TransactionService txnService) {
        this.ds         = ds;
        this.txnService = txnService;
    }

    // ── Build a portfolio snapshot ───────────────────────────────────
    public Portfolio buildPortfolio(String investorId) {
        if (ds.findInvestorById(investorId) == null)
            throw new IllegalArgumentException("Investor not found: " + investorId);

        String portfolioId = "PF-" + investorId;
        Portfolio portfolio = new Portfolio(portfolioId, investorId);

        for (Fund fund : ds.getFunds()) {
            double units = txnService.getTotalUnitsHeld(investorId, fund.getFundId());
            if (units <= 0) continue;

            double invested = txnService.getTotalInvested(investorId, fund.getFundId());
            Portfolio.Holding holding = portfolio.getOrCreateHolding(fund.getFundId(), fund.getFundName());
            holding.addUnits(units, 0, invested);            // set invested
            holding.setCurrentNAV(fund.getCurrentNAV());
        }

        portfolio.setLastUpdated(java.time.LocalDate.now().toString());
        return portfolio;
    }

    // ── Print portfolio ──────────────────────────────────────────────
    public void printPortfolio(String investorId) {
        Portfolio p = buildPortfolio(investorId);
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  PORTFOLIO SNAPSHOT — Investor: " + investorId);
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println(p);
        System.out.println("═══════════════════════════════════════════════════════");
    }

    // ── Diversification breakdown ────────────────────────────────────
    public void printDiversification(String investorId) {
        Portfolio p = buildPortfolio(investorId);
        double total = p.getTotalCurrentValue();

        System.out.println("═══════════════════════════════════════");
        System.out.println("  DIVERSIFICATION — Investor: " + investorId);
        System.out.println("═══════════════════════════════════════");

        for (Portfolio.Holding h : p.getHoldings()) {
            Fund fund = ds.findFundById(h.getFundId());
            double pct = total > 0 ? (h.getCurrentValue() / total) * 100 : 0;
            System.out.printf("  %-25s [%-6s] %5.1f%%%n",
                    h.getFundName(),
                    fund != null ? fund.getFundType() : "?",
                    pct);
        }

        System.out.printf("%n  Total Portfolio Value: ₹%,.2f%n", total);
        System.out.println("═══════════════════════════════════════");
    }
}
