package model;

import java.util.HashSet;

/**
 * Records a dividend declared by a mutual fund and paid to eligible investors.
 * Uses HashSet<String> for investorIds (no duplicates, O(1) lookup).
 */
public class DividendRecord {
    private String          dividendId;
    private String          fundId;
    private double          dividendPerUnit;   // ₹ per unit
    private String          payoutDate;        // yyyy-MM-dd
    private HashSet<String> eligibleInvestors; // Investor IDs who received this
    private double          totalPaidOut;      // Total ₹ distributed

    public DividendRecord() {
        this.eligibleInvestors = new HashSet<>();
    }

    public DividendRecord(String dividendId, String fundId,
                          double dividendPerUnit, String payoutDate) {
        this();
        this.dividendId      = dividendId;
        this.fundId          = fundId;
        this.dividendPerUnit = dividendPerUnit;
        this.payoutDate      = payoutDate;
    }

    public void addEligibleInvestor(String investorId) { eligibleInvestors.add(investorId); }
    public boolean isEligible(String investorId)       { return eligibleInvestors.contains(investorId); }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String          getDividendId()        { return dividendId;        }
    public String          getFundId()            { return fundId;            }
    public double          getDividendPerUnit()   { return dividendPerUnit;   }
    public String          getPayoutDate()        { return payoutDate;        }
    public HashSet<String> getEligibleInvestors() { return eligibleInvestors; }
    public double          getTotalPaidOut()      { return totalPaidOut;      }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setDividendId(String dividendId)               { this.dividendId        = dividendId;        }
    public void setFundId(String fundId)                       { this.fundId            = fundId;            }
    public void setDividendPerUnit(double dividendPerUnit)     { this.dividendPerUnit   = dividendPerUnit;   }
    public void setPayoutDate(String payoutDate)               { this.payoutDate        = payoutDate;        }
    public void setEligibleInvestors(HashSet<String> eligible) { this.eligibleInvestors = eligible;          }
    public void setTotalPaidOut(double totalPaidOut)           { this.totalPaidOut      = totalPaidOut;      }

    @Override
    public String toString() {
        return "[" + dividendId + "] Fund: " + fundId
                + " | ₹" + dividendPerUnit + "/unit"
                + " | Payout: " + payoutDate
                + " | Investors: " + eligibleInvestors.size()
                + " | Total Paid: ₹" + String.format("%.2f", totalPaidOut);
    }
}
