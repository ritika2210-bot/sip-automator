package model;

import enums.GainType;

/**
 * Represents the tax capital gain record for one redemption event.
 * Implements Comparable so it can be stored in a TreeSet (sorted by date).
 */
public class CapitalGain implements Comparable<CapitalGain> {
    private String   gainId;
    private String   transactionId;    // The original BUY transaction
    private String   redemptionId;     // The SELL event
    private double   purchaseNAV;
    private double   redemptionNAV;
    private double   units;
    private double   gainAmount;       // Profit (positive) or loss (negative)
    private GainType gainType;         // STCG or LTCG
    private int      holdingPeriodDays;
    private String   gainDate;         // yyyy-MM-dd (redemption date)

    public CapitalGain() {}

    public CapitalGain(String gainId, String transactionId, String redemptionId,
                       double purchaseNAV, double redemptionNAV, double units,
                       GainType gainType, int holdingPeriodDays, String gainDate) {
        this.gainId            = gainId;
        this.transactionId     = transactionId;
        this.redemptionId      = redemptionId;
        this.purchaseNAV       = purchaseNAV;
        this.redemptionNAV     = redemptionNAV;
        this.units             = units;
        this.gainAmount        = (redemptionNAV - purchaseNAV) * units;
        this.gainType          = gainType;
        this.holdingPeriodDays = holdingPeriodDays;
        this.gainDate          = gainDate;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String   getGainId()             { return gainId;             }
    public String   getTransactionId()      { return transactionId;      }
    public String   getRedemptionId()       { return redemptionId;       }
    public double   getPurchaseNAV()        { return purchaseNAV;        }
    public double   getRedemptionNAV()      { return redemptionNAV;      }
    public double   getUnits()              { return units;              }
    public double   getGainAmount()         { return gainAmount;         }
    public GainType getGainType()           { return gainType;           }
    public int      getHoldingPeriodDays()  { return holdingPeriodDays;  }
    public String   getGainDate()           { return gainDate;           }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setGainId(String gainId)                       { this.gainId            = gainId;            }
    public void setTransactionId(String transactionId)         { this.transactionId     = transactionId;     }
    public void setRedemptionId(String redemptionId)           { this.redemptionId      = redemptionId;      }
    public void setPurchaseNAV(double purchaseNAV)             { this.purchaseNAV       = purchaseNAV;       }
    public void setRedemptionNAV(double redemptionNAV)         { this.redemptionNAV     = redemptionNAV;     }
    public void setUnits(double units)                         { this.units             = units;             }
    public void setGainAmount(double gainAmount)               { this.gainAmount        = gainAmount;        }
    public void setGainType(GainType gainType)                 { this.gainType          = gainType;          }
    public void setHoldingPeriodDays(int holdingPeriodDays)    { this.holdingPeriodDays = holdingPeriodDays; }
    public void setGainDate(String gainDate)                   { this.gainDate          = gainDate;          }

    /**
     * Sorted in a TreeSet by gainDate (ascending).
     * yyyy-MM-dd format supports lexicographic sorting.
     */
    @Override
    public int compareTo(CapitalGain other) {
        int cmp = this.gainDate.compareTo(other.gainDate);
        return cmp != 0 ? cmp : this.gainId.compareTo(other.gainId);
    }

    @Override
    public String toString() {
        return "[" + gainId + "] " + gainType + " | Gain: ₹" + String.format("%.2f", gainAmount)
                + " | Held: " + holdingPeriodDays + " days | Date: " + gainDate;
    }
}
