package model;

import enums.FundType;
import enums.RiskLevel;

// A mutual fund scheme available for investment
public class Fund {

    private String      fundId;
    private String      fundName;
    private String      fundHouse;      // e.g. "HDFC AMC", "SBI MF"
    private FundType    fundType;       // EQUITY / DEBT / HYBRID / LIQUID
    private RiskLevel   riskLevel;      // LOW / MEDIUM / HIGH
    private double      currentNAV;     // Current price per unit (₹)
    private double      expenseRatio;   // Annual fee %
    private String      inceptionDate;  // When fund started (yyyy-MM-dd)
    private boolean     isActive;
    private FundManager fundManager;    // Who manages this fund

    // Constructor
    public Fund(String fundId, String fundName, String fundHouse,
                FundType fundType, RiskLevel riskLevel,
                double currentNAV, double expenseRatio, String inceptionDate) {
        this.fundId       = fundId;
        this.fundName     = fundName;
        this.fundHouse    = fundHouse;
        this.fundType     = fundType;
        this.riskLevel    = riskLevel;
        this.currentNAV   = currentNAV;
        this.expenseRatio = expenseRatio;
        this.inceptionDate = inceptionDate;
        this.isActive     = true;
    }

    // Getters
    public String      getFundId()        { return fundId;        }
    public String      getFundName()      { return fundName;      }
    public String      getFundHouse()     { return fundHouse;     }
    public FundType    getFundType()      { return fundType;      }
    public RiskLevel   getRiskLevel()     { return riskLevel;     }
    public double      getCurrentNAV()    { return currentNAV;    }
    public double      getExpenseRatio()  { return expenseRatio;  }
    public String      getInceptionDate() { return inceptionDate; }
    public boolean     isActive()         { return isActive;      }
    public FundManager getFundManager()   { return fundManager;   }

    // Setters
    public void setFundId(String fundId)              { this.fundId        = fundId;        }
    public void setFundName(String fundName)          { this.fundName      = fundName;      }
    public void setFundHouse(String fundHouse)        { this.fundHouse     = fundHouse;     }
    public void setFundType(FundType fundType)        { this.fundType      = fundType;      }
    public void setRiskLevel(RiskLevel riskLevel)     { this.riskLevel     = riskLevel;     }
    public void setCurrentNAV(double currentNAV)      { this.currentNAV    = currentNAV;    }
    public void setExpenseRatio(double expenseRatio)  { this.expenseRatio  = expenseRatio;  }
    public void setInceptionDate(String inceptionDate){ this.inceptionDate = inceptionDate; }
    public void setActive(boolean active)             { this.isActive      = active;        }
    public void setFundManager(FundManager mgr)       { this.fundManager   = mgr;           }

    @Override
    public String toString() {
        return "[" + fundId + "] " + fundName + " | " + fundHouse +
               " | Type: " + fundType + " | Risk: " + riskLevel +
               " | NAV: ₹" + currentNAV + " | Expense: " + expenseRatio + "%";
    }
}
