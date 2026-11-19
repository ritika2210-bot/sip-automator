package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Rebalancing report comparing an investor's current allocation with their target.
 * Uses LinkedHashMap to maintain fund insertion order in the display.
 */
public class RebalancingReport {
    private String                      reportId;
    private String                      investorId;
    private String                      generatedDate;
    private double                      totalPortfolioValue;
    private LinkedHashMap<String, Double> targetAllocation; // fundId → target %
    private ArrayList<RebalancingEntry> entries;            // Per-fund suggestions

    public RebalancingReport() {
        this.targetAllocation = new LinkedHashMap<>();
        this.entries          = new ArrayList<>();
    }

    public RebalancingReport(String reportId, String investorId, String generatedDate) {
        this();
        this.reportId      = reportId;
        this.investorId    = investorId;
        this.generatedDate = generatedDate;
    }

    public void addEntry(RebalancingEntry entry) { entries.add(entry); }
    public void setTarget(String fundId, double targetPct) { targetAllocation.put(fundId, targetPct); }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String                        getReportId()           { return reportId;           }
    public String                        getInvestorId()         { return investorId;         }
    public String                        getGeneratedDate()      { return generatedDate;      }
    public double                        getTotalPortfolioValue(){ return totalPortfolioValue;}
    public LinkedHashMap<String, Double> getTargetAllocation()   { return targetAllocation;   }
    public ArrayList<RebalancingEntry>   getEntries()            { return entries;            }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setReportId(String reportId)                                  { this.reportId           = reportId;           }
    public void setInvestorId(String investorId)                              { this.investorId         = investorId;         }
    public void setGeneratedDate(String generatedDate)                        { this.generatedDate      = generatedDate;      }
    public void setTotalPortfolioValue(double totalPortfolioValue)            { this.totalPortfolioValue = totalPortfolioValue;}
    public void setTargetAllocation(LinkedHashMap<String, Double> target)     { this.targetAllocation   = target;             }
    public void setEntries(ArrayList<RebalancingEntry> entries)               { this.entries            = entries;            }

    @Override
    public String toString() {
        return "Rebalancing Report [" + reportId + "] | " + investorId
                + " | Portfolio Value: ₹" + String.format("%.2f", totalPortfolioValue)
                + " | Generated: " + generatedDate
                + " | Suggestions: " + entries.size();
    }
}
