package util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates unique IDs for all entities in the SIP Automater.
 * Uses AtomicInteger counters for thread-safety.
 */
public class IDGenerator {

    private static final AtomicInteger investorCounter    = new AtomicInteger(1);
    private static final AtomicInteger fundCounter        = new AtomicInteger(1);
    private static final AtomicInteger sipCounter         = new AtomicInteger(1);
    private static final AtomicInteger transactionCounter = new AtomicInteger(1);
    private static final AtomicInteger redemptionCounter  = new AtomicInteger(1);
    private static final AtomicInteger reportCounter      = new AtomicInteger(1);
    private static final AtomicInteger dividendCounter    = new AtomicInteger(1);
    private static final AtomicInteger scheduleCounter    = new AtomicInteger(1);

    public static String generateInvestorId() {
        return String.format("INV%03d", investorCounter.getAndIncrement());
    }

    public static String generateFundId() {
        return String.format("F%03d", fundCounter.getAndIncrement());
    }

    public static String generateSIPId() {
        return String.format("SIP%03d", sipCounter.getAndIncrement());
    }

    public static String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + "_" + transactionCounter.getAndIncrement();
    }

    public static String generateRedemptionId() {
        return String.format("RED%03d", redemptionCounter.getAndIncrement());
    }

    public static String generateReportId(String prefix) {
        return prefix + String.format("%03d", reportCounter.getAndIncrement());
    }

    public static String generateDividendId() {
        return String.format("DIV%03d", dividendCounter.getAndIncrement());
    }

    public static String generateScheduleId() {
        return String.format("SCH%03d", scheduleCounter.getAndIncrement());
    }
}
