package model;

import enums.RebalanceAction;

/**
 * One line in a rebalancing report — represents a single fund's rebalancing suggestion.
 */
public class RebalancingEntry {
    private String          fundId;
    private String          fundName;
    private double          currentUnits;
    private double          currentValue;
    private double          currentPercent;  // Actual % of portfolio
    private double          targetPercent;   // Desired % of portfolio
    private RebalanceAction action;          // BUY / SELL / HOLD
    private double          actionAmount;    // ₹ to buy or sell

    public RebalancingEntry() {}

    public RebalancingEntry(String fundId, String fundName, double currentUnits,
                            double currentValue, double currentPercent, double targetPercent) {
        this.fundId          = fundId;
        this.fundName        = fundName;
        this.currentUnits    = currentUnits;
        this.currentValue    = currentValue;
        this.currentPercent  = currentPercent;
        this.targetPercent   = targetPercent;
        this.determineAction();
    }

    /** Decide BUY / SELL / HOLD based on deviation. */
    private void determineAction() {
        double deviation = targetPercent - currentPercent;
        if (Math.abs(deviation) < 2.0) {
            this.action = RebalanceAction.HOLD;
            this.actionAmount = 0;
        } else if (deviation > 0) {
            this.action = RebalanceAction.BUY;
            this.actionAmount = (deviation / 100.0) * currentValue;
        } else {
            this.action = RebalanceAction.SELL;
            this.actionAmount = Math.abs(deviation / 100.0) * currentValue;
        }
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String          getFundId()         { return fundId;         }
    public String          getFundName()       { return fundName;       }
    public double          getCurrentUnits()   { return currentUnits;   }
    public double          getCurrentValue()   { return currentValue;   }
    public double          getCurrentPercent() { return currentPercent; }
    public double          getTargetPercent()  { return targetPercent;  }
    public RebalanceAction getAction()         { return action;         }
    public double          getActionAmount()   { return actionAmount;   }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setFundId(String fundId)                   { this.fundId         = fundId;         }
    public void setFundName(String fundName)               { this.fundName       = fundName;       }
    public void setCurrentUnits(double currentUnits)       { this.currentUnits   = currentUnits;   }
    public void setCurrentValue(double currentValue)       { this.currentValue   = currentValue;   }
    public void setCurrentPercent(double currentPercent)   { this.currentPercent = currentPercent; }
    public void setTargetPercent(double targetPercent)     { this.targetPercent  = targetPercent;  }
    public void setAction(RebalanceAction action)          { this.action         = action;         }
    public void setActionAmount(double actionAmount)       { this.actionAmount   = actionAmount;   }

    @Override
    public String toString() {
        return fundName + " [" + fundId + "]"
                + " | Current: " + String.format("%.1f", currentPercent) + "%"
                + " | Target: " + String.format("%.1f", targetPercent) + "%"
                + " | Action: " + action
                + (action != RebalanceAction.HOLD ? " ₹" + String.format("%.2f", actionAmount) : "");
    }
}
