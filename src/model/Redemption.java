package model;

/**
 * Represents the sale (redemption) of fund units by an investor.
 */
public class Redemption {
    private String      redemptionId;
    private String      investorId;
    private String      fundId;
    private double      unitsRedeemed;
    private double      navAtRedemption;
    private double      redemptionAmount;   // unitsRedeemed × navAtRedemption
    private String      redemptionDate;     // yyyy-MM-dd
    private CapitalGain capitalGain;        // Tax record generated

    public Redemption() {}

    public Redemption(String redemptionId, String investorId, String fundId,
                      double unitsRedeemed, double navAtRedemption, String redemptionDate) {
        this.redemptionId      = redemptionId;
        this.investorId        = investorId;
        this.fundId            = fundId;
        this.unitsRedeemed     = unitsRedeemed;
        this.navAtRedemption   = navAtRedemption;
        this.redemptionAmount  = unitsRedeemed * navAtRedemption;
        this.redemptionDate    = redemptionDate;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String      getRedemptionId()     { return redemptionId;     }
    public String      getInvestorId()       { return investorId;       }
    public String      getFundId()           { return fundId;           }
    public double      getUnitsRedeemed()    { return unitsRedeemed;    }
    public double      getNavAtRedemption()  { return navAtRedemption;  }
    public double      getRedemptionAmount() { return redemptionAmount; }
    public String      getRedemptionDate()   { return redemptionDate;   }
    public CapitalGain getCapitalGain()      { return capitalGain;      }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setRedemptionId(String redemptionId)         { this.redemptionId     = redemptionId;     }
    public void setInvestorId(String investorId)             { this.investorId       = investorId;       }
    public void setFundId(String fundId)                     { this.fundId           = fundId;           }
    public void setUnitsRedeemed(double unitsRedeemed)       { this.unitsRedeemed    = unitsRedeemed;    }
    public void setNavAtRedemption(double navAtRedemption)   { this.navAtRedemption  = navAtRedemption;  }
    public void setRedemptionAmount(double redemptionAmount) { this.redemptionAmount = redemptionAmount; }
    public void setRedemptionDate(String redemptionDate)     { this.redemptionDate   = redemptionDate;   }
    public void setCapitalGain(CapitalGain capitalGain)      { this.capitalGain      = capitalGain;      }

    @Override
    public String toString() {
        return "[" + redemptionId + "] " + investorId + " | Fund: " + fundId
                + " | Units: " + String.format("%.4f", unitsRedeemed)
                + " @ NAV ₹" + String.format("%.2f", navAtRedemption)
                + " = ₹" + String.format("%.2f", redemptionAmount)
                + " | Date: " + redemptionDate;
    }
}
