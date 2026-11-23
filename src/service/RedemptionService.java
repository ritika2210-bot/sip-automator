package service;

import data.DataStore;
import enums.TaxCategory;
import enums.TransactionType;
import model.*;

import java.util.List;

/**
 * RedemptionService — process fund redemptions and generate CapitalGain records.
 */
public class RedemptionService {

    private DataStore          ds;
    private TransactionService txnService;

    public RedemptionService(DataStore ds, TransactionService txnService) {
        this.ds         = ds;
        this.txnService = txnService;
    }

    // ── Redeem by units ──────────────────────────────────────────────
    public Redemption redeemByUnits(String investorId, String fundId,
                                    double units, String date) {
        return processRedemption(investorId, fundId, units, 0, date);
    }

    // ── Redeem by amount ─────────────────────────────────────────────
    public Redemption redeemByAmount(String investorId, String fundId,
                                     double amount, String date) {
        return processRedemption(investorId, fundId, 0, amount, date);
    }

    // ── Core redemption logic ────────────────────────────────────────
    private Redemption processRedemption(String investorId, String fundId,
                                         double units, double amount, String date) {
        // Validate investor and fund
        if (ds.findInvestorById(investorId) == null)
            throw new IllegalArgumentException("Investor not found: " + investorId);
        Fund fund = ds.findFundById(fundId);
        if (fund == null)
            throw new IllegalArgumentException("Fund not found: " + fundId);

        double nav        = fund.getCurrentNAV();
        double unitsHeld  = txnService.getTotalUnitsHeld(investorId, fundId);
        double unitsToSell;

        if (units > 0) {
            unitsToSell = units;
        } else {
            unitsToSell = amount / nav;
        }

        if (unitsToSell > unitsHeld)
            throw new IllegalStateException("Insufficient units. Held: " +
                    String.format("%.4f", unitsHeld) + ", Requested: " +
                    String.format("%.4f", unitsToSell));

        // Create and process redemption
        String redemptionId = ds.nextRedemptionId();
        Redemption redemption;
        if (units > 0) {
            redemption = new Redemption(redemptionId, investorId, fundId, units, date);
        } else {
            redemption = new Redemption(redemptionId, investorId, fundId, date, amount);
        }
        redemption.process(nav);
        ds.addRedemption(redemption);

        // Record SELL transaction
        txnService.recordTransaction(investorId, fundId, null,
                TransactionType.SELL, redemption.getRedeemedAmount(), nav, date);

        // Compute capital gain
        computeCapitalGain(investorId, fundId, redemption, date);

        System.out.println("✅ Redemption processed: " + redemption);
        return redemption;
    }

    // ── Compute capital gain for a redemption ────────────────────────
    private void computeCapitalGain(String investorId, String fundId,
                                    Redemption redemption, String date) {
        // Get earliest BUY transactions to estimate cost basis
        List<Transaction> buys = txnService.getBuyTransactions(investorId, fundId);
        if (buys.isEmpty()) return;

        // Weighted average buy NAV
        double totalAmount = buys.stream().mapToDouble(Transaction::getAmount).sum();
        double totalUnits  = buys.stream().mapToDouble(Transaction::getUnits).sum();
        double avgBuyNAV   = totalUnits > 0 ? totalAmount / totalUnits : 0;

        // Holding period in days (simplified: earliest buy to today)
        String firstBuyDate = buys.get(0).getTransactionDate();
        int holdingDays = computeDaysDiff(firstBuyDate, date);

        // Determine tax category
        Fund fund = ds.findFundById(fundId);
        TaxCategory taxCat = determineTaxCategory(fund, holdingDays);

        String gainId = ds.nextGainId();
        CapitalGain gain = new CapitalGain(gainId, investorId, fundId,
                redemption.getRedemptionId(),
                avgBuyNAV, redemption.getNavAtRedemption(),
                redemption.getRedeemedUnits(),
                holdingDays, taxCat, date);

        ds.addCapitalGain(gain);
        System.out.println("💰 Capital Gain computed: " + gain);
    }

    // ── Determine tax category ───────────────────────────────────────
    private TaxCategory determineTaxCategory(Fund fund, int holdingDays) {
        boolean isEquity = fund.getFundType().name().equals("EQUITY") ||
                           fund.getFundType().name().equals("HYBRID");
        if (isEquity) {
            return holdingDays >= 365 ? TaxCategory.EQUITY_LTCG : TaxCategory.EQUITY_STCG;
        } else {
            return holdingDays >= 1095 ? TaxCategory.DEBT_LTCG : TaxCategory.DEBT_STCG;
        }
    }

    // ── Naive date diff (yyyy-MM-dd strings) ─────────────────────────
    private int computeDaysDiff(String from, String to) {
        try {
            String[] f = from.split("-");
            String[] t = to.split("-");
            long fromDay = Integer.parseInt(f[0]) * 365L + Integer.parseInt(f[1]) * 30 + Integer.parseInt(f[2]);
            long toDay   = Integer.parseInt(t[0]) * 365L + Integer.parseInt(t[1]) * 30 + Integer.parseInt(t[2]);
            return (int) Math.abs(toDay - fromDay);
        } catch (Exception e) {
            return 0;
        }
    }

    // ── Get all redemptions by investor ──────────────────────────────
    public List<Redemption> getRedemptionsByInvestor(String investorId) {
        return ds.getRedemptions().stream()
                 .filter(r -> r.getInvestorId().equals(investorId))
                 .toList();
    }
}
