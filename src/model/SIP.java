package model;

import enums.SIPStatus;

// Represents a Systematic Investment Plan — a recurring monthly investment
public class SIP {

    private String     sipId;            // Unique ID like "SIP001"
    private String     investorId;       // Which investor owns this SIP
    private String     fundId;           // Which fund to invest in
    private double     monthlyAmount;    // Amount invested each month (₹)
    private int        sipDay;           // Day of month SIP is deducted (1–28)
    private String     startDate;        // yyyy-MM-dd
    private String     endDate;          // yyyy-MM-dd (null if open-ended)
    private SIPStatus  status;           // ACTIVE / PAUSED / CANCELLED / COMPLETED
    private int        totalInstallments;// Total installments planned (0 = unlimited)
    private int        paidInstallments; // How many have been paid so far
    private double     totalInvested;    // Cumulative ₹ invested so far
    private double     totalUnits;       // Cumulative units accumulated so far

    // Constructor
    public SIP(String sipId, String investorId, String fundId,
               double monthlyAmount, int sipDay,
               String startDate, String endDate, int totalInstallments) {
        this.sipId             = sipId;
        this.investorId        = investorId;
        this.fundId            = fundId;
        this.monthlyAmount     = monthlyAmount;
        this.sipDay            = sipDay;
        this.startDate         = startDate;
        this.endDate           = endDate;
        this.totalInstallments = totalInstallments;
        this.status            = SIPStatus.ACTIVE;
        this.paidInstallments  = 0;
        this.totalInvested     = 0.0;
        this.totalUnits        = 0.0;
    }

    // Getters
    public String    getSipId()             { return sipId;             }
    public String    getInvestorId()        { return investorId;        }
    public String    getFundId()            { return fundId;            }
    public double    getMonthlyAmount()     { return monthlyAmount;     }
    public int       getSipDay()            { return sipDay;            }
    public String    getStartDate()         { return startDate;         }
    public String    getEndDate()           { return endDate;           }
    public SIPStatus getStatus()            { return status;            }
    public int       getTotalInstallments() { return totalInstallments; }
    public int       getPaidInstallments()  { return paidInstallments;  }
    public double    getTotalInvested()     { return totalInvested;     }
    public double    getTotalUnits()        { return totalUnits;        }

    // Setters
    public void setSipId(String sipId)                       { this.sipId             = sipId;             }
    public void setInvestorId(String investorId)             { this.investorId        = investorId;        }
    public void setFundId(String fundId)                     { this.fundId            = fundId;            }
    public void setMonthlyAmount(double monthlyAmount)       { this.monthlyAmount     = monthlyAmount;     }
    public void setSipDay(int sipDay)                        { this.sipDay            = sipDay;            }
    public void setStartDate(String startDate)               { this.startDate         = startDate;         }
    public void setEndDate(String endDate)                   { this.endDate           = endDate;           }
    public void setStatus(SIPStatus status)                  { this.status            = status;            }
    public void setTotalInstallments(int totalInstallments)  { this.totalInstallments = totalInstallments; }
    public void setPaidInstallments(int paidInstallments)    { this.paidInstallments  = paidInstallments;  }
    public void setTotalInvested(double totalInvested)       { this.totalInvested     = totalInvested;     }
    public void setTotalUnits(double totalUnits)             { this.totalUnits        = totalUnits;        }

    // Business helpers
    /** Record one SIP installment payment */
    public void recordInstallment(double nav) {
        double units = monthlyAmount / nav;
        this.totalInvested    += monthlyAmount;
        this.totalUnits       += units;
        this.paidInstallments += 1;
        if (totalInstallments > 0 && paidInstallments >= totalInstallments) {
            this.status = SIPStatus.COMPLETED;
        }
    }

    /** Current value at given NAV */
    public double getCurrentValue(double currentNAV) {
        return totalUnits * currentNAV;
    }

    /** Gain/loss so far */
    public double getGainLoss(double currentNAV) {
        return getCurrentValue(currentNAV) - totalInvested;
    }

    @Override
    public String toString() {
        return "[" + sipId + "] Fund: " + fundId +
               " | ₹" + monthlyAmount + "/mo | Day " + sipDay +
               " | Paid: " + paidInstallments +
               (totalInstallments > 0 ? "/" + totalInstallments : "") +
               " | Invested: ₹" + String.format("%.2f", totalInvested) +
               " | Units: " + String.format("%.4f", totalUnits) +
               " | Status: " + status;
    }
}
