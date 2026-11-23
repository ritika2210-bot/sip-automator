package model;

import java.util.ArrayList;
import java.util.List;

// Portfolio — holds all fund holdings of one investor
public class Portfolio {

    private String             portfolioId;
    private String             investorId;
    private List<Holding>      holdings;      // one holding per fund
    private String             lastUpdated;   // yyyy-MM-dd

    // Inner class — holding in a single fund
    public static class Holding {
        private String fundId;
        private String fundName;
        private double totalUnits;
        private double averageBuyNAV;   // average cost per unit
        private double currentNAV;
        private double investedAmount;

        public Holding(String fundId, String fundName) {
            this.fundId        = fundId;
            this.fundName      = fundName;
            this.totalUnits    = 0.0;
            this.averageBuyNAV = 0.0;
            this.currentNAV    = 0.0;
            this.investedAmount = 0.0;
        }

        // Add units on a BUY
        public void addUnits(double units, double buyNav, double amount) {
            double oldValue    = totalUnits * averageBuyNAV;
            totalUnits        += units;
            investedAmount    += amount;
            averageBuyNAV      = totalUnits > 0 ? (oldValue + amount) / totalUnits : 0;
        }

        // Remove units on a SELL
        public void removeUnits(double units, double amount) {
            totalUnits     -= units;
            investedAmount -= amount;
            if (totalUnits < 0) totalUnits = 0;
            if (investedAmount < 0) investedAmount = 0;
        }

        public double getCurrentValue()  { return totalUnits * currentNAV; }
        public double getGainLoss()      { return getCurrentValue() - investedAmount; }
        public double getReturnPercent() {
            return investedAmount > 0 ? (getGainLoss() / investedAmount) * 100 : 0;
        }

        // Getters
        public String getFundId()         { return fundId;         }
        public String getFundName()       { return fundName;       }
        public double getTotalUnits()     { return totalUnits;     }
        public double getAverageBuyNAV()  { return averageBuyNAV;  }
        public double getCurrentNAV()     { return currentNAV;     }
        public double getInvestedAmount() { return investedAmount; }

        // Setters
        public void setCurrentNAV(double nav) { this.currentNAV = nav; }

        @Override
        public String toString() {
            return "  Fund: " + fundName + " (" + fundId + ")" +
                   " | Units: " + String.format("%.4f", totalUnits) +
                   " | Avg NAV: ₹" + String.format("%.2f", averageBuyNAV) +
                   " | Current NAV: ₹" + String.format("%.2f", currentNAV) +
                   " | Invested: ₹" + String.format("%.2f", investedAmount) +
                   " | Value: ₹" + String.format("%.2f", getCurrentValue()) +
                   " | P&L: ₹" + String.format("%.2f", getGainLoss()) +
                   " (" + String.format("%.2f", getReturnPercent()) + "%)";
        }
    }

    // Constructor
    public Portfolio(String portfolioId, String investorId) {
        this.portfolioId = portfolioId;
        this.investorId  = investorId;
        this.holdings    = new ArrayList<>();
        this.lastUpdated = "";
    }

    // Find a holding by fundId, or create new
    public Holding getOrCreateHolding(String fundId, String fundName) {
        for (Holding h : holdings) {
            if (h.getFundId().equals(fundId)) return h;
        }
        Holding h = new Holding(fundId, fundName);
        holdings.add(h);
        return h;
    }

    // Total invested across all funds
    public double getTotalInvested() {
        return holdings.stream().mapToDouble(Holding::getInvestedAmount).sum();
    }

    // Total current value
    public double getTotalCurrentValue() {
        return holdings.stream().mapToDouble(Holding::getCurrentValue).sum();
    }

    // Total gain/loss
    public double getTotalGainLoss() {
        return getTotalCurrentValue() - getTotalInvested();
    }

    // Getters
    public String        getPortfolioId() { return portfolioId; }
    public String        getInvestorId()  { return investorId;  }
    public List<Holding> getHoldings()    { return holdings;    }
    public String        getLastUpdated() { return lastUpdated; }

    // Setters
    public void setLastUpdated(String date) { this.lastUpdated = date; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Portfolio [").append(portfolioId).append("] — Investor: ").append(investorId).append("\n");
        for (Holding h : holdings) sb.append(h.toString()).append("\n");
        sb.append("  TOTAL Invested: ₹").append(String.format("%.2f", getTotalInvested()));
        sb.append(" | Value: ₹").append(String.format("%.2f", getTotalCurrentValue()));
        sb.append(" | P&L: ₹").append(String.format("%.2f", getTotalGainLoss()));
        return sb.toString();
    }
}
