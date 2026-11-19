package enums;

/**
 * Tax category based on fund type and holding period.
 * Tax rates (India, 2024):
 *   EQUITY_STCG  = 20%
 *   EQUITY_LTCG  = 12.5% above ₹1.25L exemption
 *   DEBT_STCG    = Added to income slab
 *   DEBT_LTCG    = 20% with indexation
 */
public enum TaxCategory {
    EQUITY_STCG,
    EQUITY_LTCG,
    DEBT_STCG,
    DEBT_LTCG
}
