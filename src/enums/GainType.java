package enums;

/**
 * Capital Gain type based on holding period.
 * STCG = Short-Term Capital Gain (held < 1 year for equity, < 3 years for debt)
 * LTCG = Long-Term Capital Gain (held >= 1 year for equity)
 */
public enum GainType {
    STCG,  // Short-Term Capital Gain
    LTCG   // Long-Term Capital Gain
}
