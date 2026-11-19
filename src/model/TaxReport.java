package model;

import java.util.TreeSet;

/**
 * Full annual tax summary for an investor.
 * Uses TreeSet<CapitalGain> so gains are auto-sorted by date.
 */
public class TaxReport {
    private String              reportId;
    private String              investorId;
    private String              financialYear;  // e.g., "2024-25"
    private double              totalSTCG;
    private double              totalLTCG;
    private double              stcgTax;
    private double              ltcgTax;
    private double              totalTaxPayable;
    private TreeSet<CapitalGain> gains;         // All gains sorted by date

    // LTCG exemption limit for equity (India 2024)
    private static final double LTCG_EXEMPTION = 125000.0; // ₹1.25 lakh

    public TaxReport() {
        this.gains = new TreeSet<>();
    }

    public TaxReport(String reportId, String investorId, String financialYear) {
        this();
        this.reportId      = reportId;
        this.investorId    = investorId;
        this.financialYear = financialYear;
    }

    /** Add a gain record and recalculate totals. */
    public void addGain(CapitalGain gain) {
        gains.add(gain);
        recalculate();
    }

    /** Recalculate tax totals from all gains in the TreeSet. */
    public void recalculate() {
        totalSTCG = 0; totalLTCG = 0;
        for (CapitalGain g : gains) {
            switch (g.getGainType()) {
                case STCG -> totalSTCG += g.getGainAmount();
                case LTCG -> totalLTCG += g.getGainAmount();
            }
        }
        stcgTax         = totalSTCG > 0 ? totalSTCG * 0.20 : 0;
        double taxableLTCG = Math.max(0, totalLTCG - LTCG_EXEMPTION);
        ltcgTax         = taxableLTCG * 0.125;
        totalTaxPayable = stcgTax + ltcgTax;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String               getReportId()       { return reportId;       }
    public String               getInvestorId()     { return investorId;     }
    public String               getFinancialYear()  { return financialYear;  }
    public double               getTotalSTCG()      { return totalSTCG;      }
    public double               getTotalLTCG()      { return totalLTCG;      }
    public double               getStcgTax()        { return stcgTax;        }
    public double               getLtcgTax()        { return ltcgTax;        }
    public double               getTotalTaxPayable(){ return totalTaxPayable;}
    public TreeSet<CapitalGain> getGains()          { return gains;          }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setReportId(String reportId)           { this.reportId      = reportId;      }
    public void setInvestorId(String investorId)       { this.investorId    = investorId;    }
    public void setFinancialYear(String financialYear) { this.financialYear = financialYear; }
    public void setGains(TreeSet<CapitalGain> gains)   { this.gains = gains; recalculate();  }

    @Override
    public String toString() {
        return "Tax Report [" + reportId + "] FY " + financialYear + " | " + investorId
                + " | STCG: ₹" + String.format("%.2f", totalSTCG)
                + " (Tax: ₹" + String.format("%.2f", stcgTax) + ")"
                + " | LTCG: ₹" + String.format("%.2f", totalLTCG)
                + " (Tax: ₹" + String.format("%.2f", ltcgTax) + ")"
                + " | Total Tax: ₹" + String.format("%.2f", totalTaxPayable);
    }
}
