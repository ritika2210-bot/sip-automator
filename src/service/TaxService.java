package service;

import data.DataStore;
import model.CapitalGain;

import java.util.List;

/**
 * TaxService — computes and reports capital gain taxes for an investor.
 */
public class TaxService {

    private DataStore ds;

    public TaxService(DataStore ds) {
        this.ds = ds;
    }

    // ── Total tax liability for a financial year ─────────────────────
    public double getTotalTaxLiability(String investorId) {
        return ds.getGainsByInvestor(investorId)
                 .stream()
                 .mapToDouble(CapitalGain::getTaxAmount)
                 .sum();
    }

    // ── Total gain (before tax) ──────────────────────────────────────
    public double getTotalGain(String investorId) {
        return ds.getGainsByInvestor(investorId)
                 .stream()
                 .mapToDouble(CapitalGain::getGainAmount)
                 .sum();
    }

    // ── STCG total ───────────────────────────────────────────────────
    public double getSTCGAmount(String investorId) {
        return ds.getGainsByInvestor(investorId).stream()
                 .filter(cg -> cg.getGainType().name().equals("STCG"))
                 .mapToDouble(CapitalGain::getGainAmount).sum();
    }

    // ── LTCG total ───────────────────────────────────────────────────
    public double getLTCGAmount(String investorId) {
        return ds.getGainsByInvestor(investorId).stream()
                 .filter(cg -> cg.getGainType().name().equals("LTCG"))
                 .mapToDouble(CapitalGain::getGainAmount).sum();
    }

    // ── Print full tax report ────────────────────────────────────────
    public void printTaxReport(String investorId) {
        List<CapitalGain> gains = ds.getGainsByInvestor(investorId);

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  CAPITAL GAINS TAX REPORT — Investor: " + investorId);
        System.out.println("═══════════════════════════════════════════════════════");

        if (gains.isEmpty()) {
            System.out.println("  No redemptions/gains recorded.");
        } else {
            System.out.println("  GAIN DETAILS:");
            for (CapitalGain cg : gains) {
                System.out.println("    " + cg);
            }
            System.out.println();
            System.out.printf("  STCG Total       : ₹%,.2f%n", getSTCGAmount(investorId));
            System.out.printf("  LTCG Total       : ₹%,.2f%n", getLTCGAmount(investorId));
            System.out.printf("  Total Gains      : ₹%,.2f%n", getTotalGain(investorId));
            System.out.printf("  Total Tax Owed   : ₹%,.2f%n", getTotalTaxLiability(investorId));
        }

        System.out.println();
        System.out.println("  TAX REFERENCE:");
        System.out.println("  Equity STCG  : 20%  (held < 1 year)");
        System.out.println("  Equity LTCG  : 12.5% above ₹1.25L (held ≥ 1 year)");
        System.out.println("  Debt STCG    : 30%  (held < 3 years)");
        System.out.println("  Debt LTCG    : 20%  (held ≥ 3 years, with indexation)");
        System.out.println("═══════════════════════════════════════════════════════");
    }
}
