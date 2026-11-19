package model;

import enums.TransactionType;

/**
 * Records one investment event: a BUY, SELL, or DIVIDEND.
 * Units bought = amount ÷ NAV at time of transaction.
 */
public class Transaction {
    private String          transactionId;
    private String          investorId;
    private String          fundId;
    private TransactionType transactionType;
    private double          amount;             // ₹ transacted
    private double          navAtTransaction;   // NAV on that day
    private double          units;              // amount ÷ nav
    private String          date;               // yyyy-MM-dd
    private String          sipId;              // null if lump sum

    public Transaction() {}

    public Transaction(String transactionId, String investorId, String fundId,
                       TransactionType transactionType, double amount,
                       double navAtTransaction, String date, String sipId) {
        this.transactionId     = transactionId;
        this.investorId        = investorId;
        this.fundId            = fundId;
        this.transactionType   = transactionType;
        this.amount            = amount;
        this.navAtTransaction  = navAtTransaction;
        this.units             = (navAtTransaction > 0) ? amount / navAtTransaction : 0;
        this.date              = date;
        this.sipId             = sipId;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String          getTransactionId()    { return transactionId;    }
    public String          getInvestorId()       { return investorId;       }
    public String          getFundId()           { return fundId;           }
    public TransactionType getTransactionType()  { return transactionType;  }
    public double          getAmount()           { return amount;           }
    public double          getNavAtTransaction() { return navAtTransaction; }
    public double          getUnits()            { return units;            }
    public String          getDate()             { return date;             }
    public String          getSipId()            { return sipId;            }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setTransactionId(String transactionId)         { this.transactionId     = transactionId;    }
    public void setInvestorId(String investorId)               { this.investorId        = investorId;       }
    public void setFundId(String fundId)                       { this.fundId            = fundId;           }
    public void setTransactionType(TransactionType type)       { this.transactionType   = type;             }
    public void setAmount(double amount)                       { this.amount            = amount;           }
    public void setNavAtTransaction(double navAtTransaction)   { this.navAtTransaction  = navAtTransaction; }
    public void setUnits(double units)                         { this.units             = units;            }
    public void setDate(String date)                           { this.date              = date;             }
    public void setSipId(String sipId)                         { this.sipId             = sipId;            }

    @Override
    public String toString() {
        return "[" + transactionId + "] " + transactionType + " | Investor: " + investorId
                + " | Fund: " + fundId + " | ₹" + String.format("%.2f", amount)
                + " @ NAV ₹" + String.format("%.4f", navAtTransaction)
                + " = " + String.format("%.4f", units) + " units | " + date;
    }
}
