package service;

import data.DataStore;
import enums.FundType;
import enums.RiskLevel;
import model.Fund;
import model.NAVHistory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * FundService — operations on Fund records and NAV management.
 */
public class FundService {

    private DataStore ds;

    public FundService(DataStore ds) {
        this.ds = ds;
    }

    // ── Add a new fund ───────────────────────────────────────────────
    public Fund addFund(Fund fund) {
        if (fund.getFundName() == null || fund.getFundName().isBlank())
            throw new IllegalArgumentException("Fund name cannot be empty.");
        if (fund.getCurrentNAV() <= 0)
            throw new IllegalArgumentException("NAV must be positive.");
        ds.addFund(fund);
        System.out.println("✅ Fund added: " + fund);
        return fund;
    }

    // ── Get fund by ID ───────────────────────────────────────────────
    public Fund getFundById(String fundId) {
        Fund fund = ds.findFundById(fundId);
        if (fund == null) throw new IllegalArgumentException("Fund not found: " + fundId);
        return fund;
    }

    // ── List all active funds ────────────────────────────────────────
    public List<Fund> getAllActiveFunds() {
        return ds.getFunds().stream().filter(Fund::isActive).toList();
    }

    // ── Filter by type ───────────────────────────────────────────────
    public List<Fund> getFundsByType(FundType type) {
        return ds.getFunds().stream()
                 .filter(f -> f.isActive() && f.getFundType() == type)
                 .toList();
    }

    // ── Filter by risk level ─────────────────────────────────────────
    public List<Fund> getFundsByRisk(RiskLevel risk) {
        return ds.getFunds().stream()
                 .filter(f -> f.isActive() && f.getRiskLevel() == risk)
                 .toList();
    }

    // ── Update NAV and record history ────────────────────────────────
    public void updateNAV(String fundId, double newNAV, String date) {
        if (newNAV <= 0) throw new IllegalArgumentException("NAV must be positive.");
        Fund fund = getFundById(fundId);
        fund.setCurrentNAV(newNAV);
        // Record in history
        String navId = ds.nextNAVId();
        ds.addNAVHistory(new NAVHistory(navId, fundId, date, newNAV));
        System.out.println("✅ NAV updated for " + fundId + " → ₹" + newNAV + " on " + date);
    }

    // ── Get NAV history for a fund ───────────────────────────────────
    public List<NAVHistory> getNAVHistory(String fundId) {
        return ds.getNAVByFund(fundId);
    }

    // ── Deactivate a fund ────────────────────────────────────────────
    public void deactivateFund(String fundId) {
        Fund fund = getFundById(fundId);
        fund.setActive(false);
        System.out.println("⚠️  Fund deactivated: " + fundId);
    }

    // ── Print fund details ───────────────────────────────────────────
    public void printFundDetails(String fundId) {
        Fund f = getFundById(fundId);
        System.out.println("═══════════════════════════════════════");
        System.out.println("  FUND DETAILS");
        System.out.println("═══════════════════════════════════════");
        System.out.println("  ID           : " + f.getFundId());
        System.out.println("  Name         : " + f.getFundName());
        System.out.println("  House        : " + f.getFundHouse());
        System.out.println("  Type         : " + f.getFundType());
        System.out.println("  Risk         : " + f.getRiskLevel());
        System.out.println("  Current NAV  : ₹" + f.getCurrentNAV());
        System.out.println("  Expense Ratio: " + f.getExpenseRatio() + "%");
        System.out.println("  Inception    : " + f.getInceptionDate());
        if (f.getFundManager() != null)
            System.out.println("  Manager      : " + f.getFundManager());
        System.out.println("  Active       : " + (f.isActive() ? "Yes" : "No"));
        System.out.println("═══════════════════════════════════════");
    }

    // ── Print all funds ──────────────────────────────────────────────
    public void listAllFunds() {
        System.out.println("═══════════════════════════════════════");
        System.out.println("  AVAILABLE FUNDS");
        System.out.println("═══════════════════════════════════════");
        for (Fund f : getAllActiveFunds()) {
            System.out.println("  " + f);
        }
        System.out.println("═══════════════════════════════════════");
    }
}
