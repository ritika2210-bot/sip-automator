package model;

import enums.TransactionType;

// Represents a single buy/sell/dividend transaction
public class Transaction {

    private String          transactionId;   // Unique ID like "TXN001"
    private String          investorId;
    private String          fundId;
    private String          sipId;           // null if not from a SIP
    private TransactionType type;            // BUY / SELL / DIVIDEND
    private double          amount;          // ₹ amount
    private double          nav;             // NAV on transaction date
    private double          units;           // units bought/sold
    private String          transactionDate; // yyyy-MM-dd
    private String          remarks;

    // Constructor
    public Transaction(String transactionId, String investorId, String fundId,
                       String sipId, TransactionType type,
                       double amount, double nav, String transactionDate) {
        this.transactionId   = transactionId;
        this.investorId      = investorId;
        this.fundId          = fundId;
        this.sipId           = sipId;
        this.type            = type;
        this.amount          = amount;
        this.nav             = nav;
        this.units           = (nav > 0) ? amount / nav : 0;
        this.transactionDate = transactionDate;
        this.remarks         = "";
    }

    // Getters
    public String          getTransactionId()   { return transactionId;   }
    public String          getInvestorId()      { return investorId;      }
    public String          getFundId()          { return fundId;          }
    public String          getSipId()           { return sipId;           }
    public TransactionType getType()            { return type;            }
    public double          getAmount()          { return amount;          }
    public double          getNav()             { return nav;             }
    public double          getUnits()           { return units;           }
    public String          getTransactionDate() { return transactionDate; }
    public String          getRemarks()         { return remarks;         }

    // Setters
    public void setTransactionId(String transactionId)     { this.transactionId   = transactionId;   }
    public void setInvestorId(String investorId)           { this.investorId      = investorId;      }
    public void setFundId(String fundId)                   { this.fundId          = fundId;          }
    public void setSipId(String sipId)                     { this.sipId           = sipId;           }
    public void setType(TransactionType type)              { this.type            = type;            }
    public void setAmount(double amount)                   { this.amount          = amount;          }
    public void setNav(double nav)                         { this.nav             = nav;             }
    public void setUnits(double units)                     { this.units           = units;           }
    public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }
    public void setRemarks(String remarks)                 { this.remarks         = remarks;         }

    @Override
    public String toString() {
        return "[" + transactionId + "] " + type +
               " | Fund: " + fundId +
               " | ₹" + String.format("%.2f", amount) +
               " @ NAV ₹" + String.format("%.4f", nav) +
               " | Units: " + String.format("%.4f", units) +
               " | Date: " + transactionDate;
    }
}
