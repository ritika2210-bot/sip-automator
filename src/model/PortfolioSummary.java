package model;

/**
 * A snapshot of an investor's portfolio value at a specific point in time.
 */
public class PortfolioSummary {
    private String summaryId;
    private String investorId;
    private double totalInvested;    // Total ₹ put in
    private double currentValue;     // Current ₹ value
    private double totalGain;        // currentValue - totalInvested
    private double gainPercent;      // Gain as %
    private String generatedDate;    // yyyy-MM-dd

    public PortfolioSummary() {}

    public PortfolioSummary(String summaryId, String investorId,
                            double totalInvested, double currentValue, String generatedDate) {
        this.summaryId     = summaryId;
        this.investorId    = investorId;
        this.totalInvested = totalInvested;
        this.currentValue  = currentValue;
        this.totalGain     = currentValue - totalInvested;
        this.gainPercent   = totalInvested > 0 ? (totalGain / totalInvested) * 100 : 0;
        this.generatedDate = generatedDate;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String getSummaryId()     { return summaryId;     }
    public String getInvestorId()    { return investorId;    }
    public double getTotalInvested() { return totalInvested; }
    public double getCurrentValue()  { return currentValue;  }
    public double getTotalGain()     { return totalGain;     }
    public double getGainPercent()   { return gainPercent;   }
    public String getGeneratedDate() { return generatedDate; }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setSummaryId(String summaryId)         { this.summaryId     = summaryId;     }
    public void setInvestorId(String investorId)       { this.investorId    = investorId;    }
    public void setTotalInvested(double totalInvested) { this.totalInvested = totalInvested; }
    public void setCurrentValue(double currentValue)   { this.currentValue  = currentValue;  }
    public void setTotalGain(double totalGain)         { this.totalGain     = totalGain;     }
    public void setGainPercent(double gainPercent)     { this.gainPercent   = gainPercent;   }
    public void setGeneratedDate(String generatedDate) { this.generatedDate = generatedDate; }

    @Override
    public String toString() {
        String sign = totalGain >= 0 ? "+" : "";
        return "[Summary: " + summaryId + "] " + investorId
                + " | Invested: ₹" + String.format("%.2f", totalInvested)
                + " | Current: ₹" + String.format("%.2f", currentValue)
                + " | Gain: " + sign + "₹" + String.format("%.2f", totalGain)
                + " (" + sign + String.format("%.2f", gainPercent) + "%)"
                + " | As of: " + generatedDate;
    }
}
