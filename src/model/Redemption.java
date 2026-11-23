package model;

// Represents a redemption (withdrawal/sell) request by an investor
public class Redemption {

    private String  redemptionId;
    private String  investorId;
    private String  fundId;
    private double  units;           // Units to redeem (0 = full redemption by amount)
    private double  amount;          // ₹ amount to redeem (0 = redeem by units)
    private double  navAtRedemption; // NAV on redemption date
    private double  redeemedUnits;   // Actual units redeemed
    private double  redeemedAmount;  // Actual ₹ credited
    private String  redemptionDate;  // yyyy-MM-dd
    private boolean isProcessed;
    private String  remarks;

    // Constructor — redeem by units
    public Redemption(String redemptionId, String investorId, String fundId,
                      double units, String redemptionDate) {
        this.redemptionId   = redemptionId;
        this.investorId     = investorId;
        this.fundId         = fundId;
        this.units          = units;
        this.amount         = 0;
        this.redemptionDate = redemptionDate;
        this.isProcessed    = false;
        this.remarks        = "";
    }

    // Constructor — redeem by amount
    public Redemption(String redemptionId, String investorId, String fundId,
                      String redemptionDate, double amount) {
        this.redemptionId   = redemptionId;
        this.investorId     = investorId;
        this.fundId         = fundId;
        this.units          = 0;
        this.amount         = amount;
        this.redemptionDate = redemptionDate;
        this.isProcessed    = false;
        this.remarks        = "";
    }

    // Process the redemption with current NAV
    public void process(double nav) {
        this.navAtRedemption = nav;
        if (units > 0) {
            this.redeemedUnits  = units;
            this.redeemedAmount = units * nav;
        } else {
            this.redeemedUnits  = amount / nav;
            this.redeemedAmount = amount;
        }
        this.isProcessed = true;
    }

    // Getters
    public String  getRedemptionId()    { return redemptionId;    }
    public String  getInvestorId()      { return investorId;      }
    public String  getFundId()          { return fundId;          }
    public double  getUnits()           { return units;           }
    public double  getAmount()          { return amount;          }
    public double  getNavAtRedemption() { return navAtRedemption; }
    public double  getRedeemedUnits()   { return redeemedUnits;   }
    public double  getRedeemedAmount()  { return redeemedAmount;  }
    public String  getRedemptionDate()  { return redemptionDate;  }
    public boolean isProcessed()        { return isProcessed;     }
    public String  getRemarks()         { return remarks;         }

    // Setters
    public void setNavAtRedemption(double nav)    { this.navAtRedemption = nav;    }
    public void setRedeemedUnits(double units)    { this.redeemedUnits   = units;  }
    public void setRedeemedAmount(double amount)  { this.redeemedAmount  = amount; }
    public void setProcessed(boolean processed)   { this.isProcessed     = processed; }
    public void setRemarks(String remarks)        { this.remarks         = remarks; }

    @Override
    public String toString() {
        return "[" + redemptionId + "] Fund: " + fundId +
               " | Units: " + String.format("%.4f", redeemedUnits) +
               " | Amount: ₹" + String.format("%.2f", redeemedAmount) +
               " @ NAV ₹" + String.format("%.4f", navAtRedemption) +
               " | Date: " + redemptionDate +
               " | Processed: " + (isProcessed ? "Yes" : "No");
    }
}
