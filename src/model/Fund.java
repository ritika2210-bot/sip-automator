package model;

import enums.FundType;
import enums.RiskLevel;

/**
 * Represents a Mutual Fund scheme available for investment.
 */
public class Fund {
    private String      fundId;
    private String      fundName;
    private String      fundHouse;      // e.g., "HDFC AMC", "SBI Mutual Fund"
    private FundType    fundType;       // EQUITY / DEBT / HYBRID / LIQUID
    private RiskLevel   riskLevel;
    private double      currentNAV;
    private FundManager fundManager;
    private String      inceptionDate;  // yyyy-MM-dd
    private double      expenseRatio;   // Annual fee %
    private boolean     isActive;

    public Fund() { this.isActive = true; }

    public Fund(String fundId, String fundName, String fundHouse, FundType fundType,
                RiskLevel riskLevel, double currentNAV, String inceptionDate, double expenseRatio) {
        this.fundId       = fundId;
        this.fundName     = fundName;
        this.fundHouse    = fundHouse;
        this.fundType     = fundType;
        this.riskLevel    = riskLevel;
        this.currentNAV   = currentNAV;
        this.inceptionDate = inceptionDate;
        this.expenseRatio = expenseRatio;
        this.isActive     = true;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String      getFundId()       { return fundId;       }
    public String      getFundName()     { return fundName;     }
    public String      getFundHouse()    { return fundHouse;    }
    public FundType    getFundType()     { return fundType;     }
    public RiskLevel   getRiskLevel()    { return riskLevel;    }
    public double      getCurrentNAV()   { return currentNAV;   }
    public FundManager getFundManager()  { return fundManager;  }
    public String      getInceptionDate(){ return inceptionDate;}
    public double      getExpenseRatio() { return expenseRatio; }
    public boolean     isActive()        { return isActive;     }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setFundId(String fundId)             { this.fundId       = fundId;       }
    public void setFundName(String fundName)         { this.fundName     = fundName;     }
    public void setFundHouse(String fundHouse)       { this.fundHouse    = fundHouse;    }
    public void setFundType(FundType fundType)       { this.fundType     = fundType;     }
    public void setRiskLevel(RiskLevel riskLevel)    { this.riskLevel    = riskLevel;    }
    public void setCurrentNAV(double currentNAV)     { this.currentNAV   = currentNAV;   }
    public void setFundManager(FundManager mgr)      { this.fundManager  = mgr;          }
    public void setInceptionDate(String inceptionDate){ this.inceptionDate = inceptionDate;}
    public void setExpenseRatio(double expenseRatio)  { this.expenseRatio = expenseRatio; }
    public void setActive(boolean active)             { this.isActive     = active;       }

    @Override
    public String toString() {
        return "[" + fundId + "] " + fundName + " (" + fundHouse + ") | "
                + fundType + " | Risk: " + riskLevel
                + " | NAV: ₹" + String.format("%.2f", currentNAV)
                + " | Expense: " + expenseRatio + "%";
    }
}
