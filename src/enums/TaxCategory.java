package enums;

// Tax category based on fund type + holding period
public enum TaxCategory {
    EQUITY_STCG,  // Equity held < 1 year  → 20% tax
    EQUITY_LTCG,  // Equity held >= 1 year  → 12.5% above ₹1.25L
    DEBT_STCG,    // Debt held < 3 years    → income slab
    DEBT_LTCG     // Debt held >= 3 years   → 20% with indexation
}
