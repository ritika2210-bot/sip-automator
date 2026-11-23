package enums;

// What action to take during portfolio rebalancing
public enum RebalanceAction {
    BUY,   // Buy more (you're below target)
    SELL,  // Sell some (you're above target)
    HOLD   // No action needed
}
