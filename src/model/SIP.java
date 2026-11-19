package model;

import enums.SIPStatus;

/**
 * A Systematic Investment Plan — recurring monthly investment by an investor into a fund.
 * Example: Invest ₹5,000 every month into HDFC Top 100 on day 1.
 */
public class SIP {
    private String    sipId;
    private Investor  investor;
    private Fund      fund;
    private double    monthlyAmount;       // ₹ invested each month
    private String    startDate;           // yyyy-MM-dd
    private String    endDate;             // Optional; null = ongoing
    private SIPStatus sipStatus;
    private int       executionDay;        // 1–28 (day of month)
    private int       totalInstallments;   // How many months have run
    private double    totalInvested;       // Running sum of ₹ invested

    public SIP() { this.sipStatus = SIPStatus.ACTIVE; }

    public SIP(String sipId, Investor investor, Fund fund, double monthlyAmount,
               String startDate, int executionDay) {
        this.sipId             = sipId;
        this.investor          = investor;
        this.fund              = fund;
        this.monthlyAmount     = monthlyAmount;
        this.startDate         = startDate;
        this.executionDay      = executionDay;
        this.sipStatus         = SIPStatus.ACTIVE;
        this.totalInstallments = 0;
        this.totalInvested     = 0.0;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String    getSipId()             { return sipId;             }
    public Investor  getInvestor()          { return investor;          }
    public Fund      getFund()              { return fund;              }
    public double    getMonthlyAmount()     { return monthlyAmount;     }
    public String    getStartDate()         { return startDate;         }
    public String    getEndDate()           { return endDate;           }
    public SIPStatus getSipStatus()         { return sipStatus;         }
    public int       getExecutionDay()      { return executionDay;      }
    public int       getTotalInstallments() { return totalInstallments; }
    public double    getTotalInvested()     { return totalInvested;     }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setSipId(String sipId)                       { this.sipId             = sipId;             }
    public void setInvestor(Investor investor)               { this.investor          = investor;          }
    public void setFund(Fund fund)                           { this.fund              = fund;              }
    public void setMonthlyAmount(double monthlyAmount)       { this.monthlyAmount     = monthlyAmount;     }
    public void setStartDate(String startDate)               { this.startDate         = startDate;         }
    public void setEndDate(String endDate)                   { this.endDate           = endDate;           }
    public void setSipStatus(SIPStatus sipStatus)            { this.sipStatus         = sipStatus;         }
    public void setExecutionDay(int executionDay)            { this.executionDay      = executionDay;      }
    public void setTotalInstallments(int totalInstallments)  { this.totalInstallments = totalInstallments; }
    public void setTotalInvested(double totalInvested)       { this.totalInvested     = totalInvested;     }

    /** Convenience: increment installment counter and running total. */
    public void recordInstallment(double amount) {
        this.totalInstallments++;
        this.totalInvested += amount;
    }

    @Override
    public String toString() {
        return "[" + sipId + "] " + investor.getName() + " → " + fund.getFundName()
                + " | ₹" + monthlyAmount + "/mo on day " + executionDay
                + " | Status: " + sipStatus
                + " | Installments: " + totalInstallments
                + " | Total Invested: ₹" + String.format("%.2f", totalInvested);
    }
}
