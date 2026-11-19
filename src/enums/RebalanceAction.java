package enums;

/**
 * Recommended action per fund in a rebalancing report.
 */
public enum RebalanceAction {
    BUY,   // Buy more units of this fund (underweight)
    SELL,  // Sell some units of this fund (overweight)
    HOLD   // No action needed (within tolerance)
}
