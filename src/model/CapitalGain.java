package model;

import enums.GainType;
import enums.TaxCategory;

// Represents computed capital gain on a fund sale
public class CapitalGain {

    private String       gainId;
    private String       investorId;
    private String       fundId;
    private String       redemptionId;    // Which redemption generated this gain
    private double       buyNAV;          // Average purchase NAV
    private double       sellNAV;         // NAV at sale
    private double       units;           // Units redeemed
    private double       costBasis;       // units * buyNAV
    private double       saleValue;       // units * sellNAV
    private double       gainAmount;      // saleValue - costBasis
    private GainType     gainType;        // STCG / LTCG
    private TaxCategory  taxCategory;     // e.g. EQUITY_LTCG
    private double       taxRate;         // Applicable tax rate %
    private double       taxAmount;       // Tax owed
    private int          holdingDays;     // How long units were held
    private String       computedDate;    // yyyy-MM-dd

    // Constructor
    public CapitalGain(String gainId, String investorId, String fundId, String redemptionId,
                       double buyNAV, double sellNAV, double units,
                       int holdingDays, TaxCategory taxCategory, String computedDate) {
        this.gainId       = gainId;
        this.investorId   = investorId;
        this.fundId       = fundId;
        this.redemptionId = redemptionId;
        this.buyNAV       = buyNAV;
        this.sellNAV      = sellNAV;
        this.units        = units;
        this.holdingDays  = holdingDays;
        this.taxCategory  = taxCategory;
        this.computedDate = computedDate;

        this.costBasis    = units * buyNAV;
        this.saleValue    = units * sellNAV;
        this.gainAmount   = saleValue - costBasis;
        this.gainType     = determineGainType(taxCategory, holdingDays);
        this.taxRate      = determineTaxRate(taxCategory, gainAmount);
        this.taxAmount    = computeTax(taxCategory, gainAmount);
    }

    // Determine STCG vs LTCG
    private GainType determineGainType(TaxCategory cat, int days) {
        switch (cat) {
            case EQUITY_LTCG: return GainType.LTCG;
            case EQUITY_STCG: return GainType.STCG;
            case DEBT_LTCG:   return GainType.LTCG;
            case DEBT_STCG:   return GainType.STCG;
            default:          return days >= 365 ? GainType.LTCG : GainType.STCG;
        }
    }

    // Tax rate lookup
    private double determineTaxRate(TaxCategory cat, double gain) {
        switch (cat) {
            case EQUITY_STCG: return 20.0;
            case EQUITY_LTCG: return 12.5;    // on amount above ₹1.25L
            case DEBT_STCG:   return 30.0;    // income-slab approximation
            case DEBT_LTCG:   return 20.0;    // with indexation (simplified)
            default:          return 0.0;
        }
    }

    // Compute actual tax
    private double computeTax(TaxCategory cat, double gain) {
        if (gain <= 0) return 0.0;
        switch (cat) {
            case EQUITY_LTCG: {
                double exemption = 125000.0; // ₹1.25L exemption
                double taxable   = Math.max(0, gain - exemption);
                return taxable * 0.125;
            }
            case EQUITY_STCG: return gain * 0.20;
            case DEBT_STCG:   return gain * 0.30;
            case DEBT_LTCG:   return gain * 0.20;
            default:          return 0.0;
        }
    }

    // Getters
    public String      getGainId()       { return gainId;       }
    public String      getInvestorId()   { return investorId;   }
    public String      getFundId()       { return fundId;       }
    public String      getRedemptionId() { return redemptionId; }
    public double      getBuyNAV()       { return buyNAV;       }
    public double      getSellNAV()      { return sellNAV;      }
    public double      getUnits()        { return units;        }
    public double      getCostBasis()    { return costBasis;    }
    public double      getSaleValue()    { return saleValue;    }
    public double      getGainAmount()   { return gainAmount;   }
    public GainType    getGainType()     { return gainType;     }
    public TaxCategory getTaxCategory()  { return taxCategory;  }
    public double      getTaxRate()      { return taxRate;      }
    public double      getTaxAmount()    { return taxAmount;    }
    public int         getHoldingDays()  { return holdingDays;  }
    public String      getComputedDate() { return computedDate; }

    @Override
    public String toString() {
        return "[" + gainId + "] Fund: " + fundId +
               " | " + gainType + " (" + taxCategory + ")" +
               " | Gain: ₹" + String.format("%.2f", gainAmount) +
               " | Tax Rate: " + taxRate + "%" +
               " | Tax: ₹" + String.format("%.2f", taxAmount) +
               " | Holding: " + holdingDays + " days";
    }
}
