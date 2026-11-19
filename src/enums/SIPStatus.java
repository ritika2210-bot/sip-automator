package enums;

/**
 * Lifecycle status of a Systematic Investment Plan (SIP).
 */
public enum SIPStatus {
    ACTIVE,     // SIP is running monthly
    PAUSED,     // Temporarily halted
    CANCELLED,  // Permanently stopped
    COMPLETED   // Reached end date or target installments
}
