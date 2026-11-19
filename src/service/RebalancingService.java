package service;

import model.*;
import util.DateUtil;
import util.IDGenerator;
import util.ReportFormatter;

import java.util.*;

/**
 * Service for portfolio rebalancing analysis.
 * Compares actual allocation vs. target and suggests BUY / SELL / HOLD actions.
 * Uses LinkedHashMap for target allocation (preserves fund insertion order).
 */
public class RebalancingService {

    // investorId → (fundId → target %)
    private final Map<String, LinkedHashMap<String, Double>> targets = new HashMap<>();
    private final FundService fundService;

    public RebalancingService(FundService fundService) {
        this.fundService = fundService;
    }

    /** Set the target allocation for an investor. Sum of values should be ~100. */
    public void setTargetAllocation(String investorId, LinkedHashMap<String, Double> target) {
        targets.put(investorId, target);
        System.out.println("[RebalancingService] Target set for " + investorId + ": " + target);
    }

    /** Calculates actual current allocation (fundId → %) from portfolio. */
    public LinkedHashMap<String, Double> getCurrentAllocation(Investor investor) {
        Portfolio portfolio = investor.getPortfolio();
        LinkedHashMap<String, Double> alloc = new LinkedHashMap<>();
        double totalValue = 0;

        // Calculate total portfolio value
        for (Map.Entry<String, Double> e : portfolio.getHoldings().entrySet()) {
            double nav = fundService.getLatestNAV(e.getKey());
            totalValue += e.getValue() * nav;
        }

        if (totalValue == 0) return alloc;

        for (Map.Entry<String, Double> e : portfolio.getHoldings().entrySet()) {
            double nav = fundService.getLatestNAV(e.getKey());
            double value = e.getValue() * nav;
            alloc.put(e.getKey(), Math.round((value / totalValue) * 10000.0) / 100.0);
        }
        return alloc;
    }

    /** Generates a full rebalancing report for an investor. */
    public RebalancingReport generateReport(Investor investor) {
        String investorId = investor.getInvestorId();
        RebalancingReport report = new RebalancingReport(
                IDGenerator.generateReportId("RB"), investorId, DateUtil.today());

        LinkedHashMap<String, Double> target  = targets.getOrDefault(investorId, new LinkedHashMap<>());
        LinkedHashMap<String, Double> current = getCurrentAllocation(investor);
        Portfolio portfolio = investor.getPortfolio();

        double totalValue = 0;
        for (Map.Entry<String, Double> e : portfolio.getHoldings().entrySet()) {
            totalValue += e.getValue() * fundService.getLatestNAV(e.getKey());
        }
        report.setTotalPortfolioValue(totalValue);

        // Create entries for all funds in holdings
        for (Map.Entry<String, Double> e : portfolio.getHoldings().entrySet()) {
            String fundId     = e.getKey();
            double units      = e.getValue();
            double nav        = fundService.getLatestNAV(fundId);
            double value      = units * nav;
            double currPct    = current.getOrDefault(fundId, 0.0);
            double targetPct  = target.getOrDefault(fundId, 0.0);

            Fund fund = fundService.getFundById(fundId);
            RebalancingEntry entry = new RebalancingEntry(fundId, fund.getFundName(),
                    units, value, currPct, targetPct);
            report.addEntry(entry);
        }

        System.out.println("[RebalancingService] Report generated: " + report);
        return report;
    }
}
