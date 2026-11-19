package enums;

/**
 * Type of transaction in an investor's portfolio.
 */
public enum TransactionType {
    BUY,       // Investor buys units (SIP execution or lump sum)
    SELL,      // Investor sells/redeems units
    DIVIDEND   // Dividend reinvestment or payout
}
