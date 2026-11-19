package service;

import exception.FundNotFoundException;
import exception.InvalidNAVException;
import model.Fund;
import model.NAVHistory;
import util.IDGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Service for all mutual fund and NAV operations.
 * Uses TreeMap<String, TreeMap<String, Double>> for nested date-sorted NAV history.
 */
public class FundService {

    // fundId → Fund
    private final TreeMap<String, Fund> fundCatalogue = new TreeMap<>();
    // fundId → (date → NAV)  — both sorted
    private final TreeMap<String, TreeMap<String, Double>> navHistoryMap = new TreeMap<>();

    // ── Fund CRUD ──────────────────────────────────────────────────────────────

    public Fund addFund(Fund fund) {
        if (fund.getFundId() == null || fund.getFundId().isBlank()) {
            fund.setFundId(IDGenerator.generateFundId());
        }
        fundCatalogue.put(fund.getFundId(), fund);
        navHistoryMap.put(fund.getFundId(), new TreeMap<>());
        System.out.println("[FundService] Added fund: " + fund);
        return fund;
    }

    public Fund getFundById(String fundId) {
        Fund fund = fundCatalogue.get(fundId);
        if (fund == null) throw new FundNotFoundException(fundId);
        return fund;
    }

    public List<Fund> getAllFunds() {
        return new ArrayList<>(fundCatalogue.values()); // TreeMap → sorted by fundId
    }

    public List<Fund> getFundsByType(enums.FundType type) {
        return fundCatalogue.values().stream()
                .filter(f -> f.getFundType() == type && f.isActive())
                .toList();
    }

    public void deactivateFund(String fundId) {
        getFundById(fundId).setActive(false);
        System.out.println("[FundService] Deactivated fund: " + fundId);
    }

    public List<Fund> searchFundByName(String keyword) {
        String lower = keyword.toLowerCase();
        return fundCatalogue.values().stream()
                .filter(f -> f.getFundName().toLowerCase().contains(lower))
                .toList();
    }

    // ── NAV Operations ─────────────────────────────────────────────────────────

    /**
     * Updates the NAV of a fund and records the history.
     * @throws InvalidNAVException if newNAV ≤ 0
     */
    public void updateNAV(String fundId, double newNAV, String date) {
        if (newNAV <= 0) throw new InvalidNAVException(newNAV);

        Fund fund = getFundById(fundId);
        double oldNAV = fund.getCurrentNAV();
        double changePct = oldNAV > 0 ? ((newNAV - oldNAV) / oldNAV) * 100 : 0;

        fund.setCurrentNAV(newNAV);
        navHistoryMap.get(fundId).put(date, newNAV);

        System.out.printf("[FundService] NAV updated: %s | ₹%.4f → ₹%.4f (%.2f%%)%n",
                fundId, oldNAV, newNAV, changePct);
    }

    /** Returns the full date-sorted NAV history for a fund. */
    public TreeMap<String, Double> getNAVHistory(String fundId) {
        getFundById(fundId); // throws if not found
        return navHistoryMap.getOrDefault(fundId, new TreeMap<>());
    }

    /** Returns the NAV on a specific date (or null if not recorded). */
    public Double getNAVOnDate(String fundId, String date) {
        return navHistoryMap.getOrDefault(fundId, new TreeMap<>()).get(date);
    }

    /** Returns the latest (most recent) NAV from history. */
    public double getLatestNAV(String fundId) {
        TreeMap<String, Double> history = getNAVHistory(fundId);
        if (history.isEmpty()) return getFundById(fundId).getCurrentNAV();
        return history.lastEntry().getValue();
    }

    /** Calculates units bought: amount ÷ NAV */
    public double calculateUnits(double amount, double nav) {
        if (nav <= 0) throw new InvalidNAVException(nav);
        return Math.round((amount / nav) * 10000.0) / 10000.0;
    }

    /** Calculates current portfolio value: units × NAV */
    public double calculateCurrentValue(double units, double nav) {
        return Math.round(units * nav * 100.0) / 100.0;
    }
}
