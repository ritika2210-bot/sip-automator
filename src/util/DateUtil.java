package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Utility methods for date operations used across the SIP Automater project.
 * All dates are handled as Strings in yyyy-MM-dd format.
 */
public class DateUtil {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /** Returns today's date as "yyyy-MM-dd". */
    public static String today() {
        return LocalDate.now().format(FMT);
    }

    /** Returns the number of days between two dates (d2 - d1). */
    public static long daysBetween(String d1, String d2) {
        return ChronoUnit.DAYS.between(LocalDate.parse(d1, FMT), LocalDate.parse(d2, FMT));
    }

    /** Returns the number of full months between two dates. */
    public static long monthsBetween(String d1, String d2) {
        return ChronoUnit.MONTHS.between(LocalDate.parse(d1, FMT), LocalDate.parse(d2, FMT));
    }

    /** Returns true if the gap between two dates is ≥ 365 days (1 year). */
    public static boolean isMoreThanOneYear(String purchaseDate, String redemptionDate) {
        return daysBetween(purchaseDate, redemptionDate) >= 365;
    }

    /** Formats a yyyy-MM-dd string to a human-readable form like "19 Nov 2026". */
    public static String formatDate(String date) {
        LocalDate d = LocalDate.parse(date, FMT);
        return d.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    /**
     * Returns the Indian financial year string for a given date.
     * FY runs April 1 to March 31.
     * Example: "2024-01-15" → "2023-24"
     */
    public static String getFinancialYear(String date) {
        LocalDate d = LocalDate.parse(date, FMT);
        int year = d.getMonthValue() >= 4 ? d.getYear() : d.getYear() - 1;
        return year + "-" + String.format("%02d", (year + 1) % 100);
    }

    /** Validates that a string is a correctly formatted yyyy-MM-dd date. */
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, FMT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
