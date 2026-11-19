package util;

/**
 * Utility methods for financial calculations used across the SIP Automater.
 */
public class MathUtil {

    /**
     * Calculates units bought.
     * units = investmentAmount / NAV
     */
    public static double calculateUnits(double amount, double nav) {
        if (nav <= 0) throw new IllegalArgumentException("NAV must be > 0");
        return roundToTwo(amount / nav);
    }

    /**
     * Calculates current market value of held units.
     * value = units × currentNAV
     */
    public static double calculateValue(double units, double currentNAV) {
        return roundToTwo(units * currentNAV);
    }

    /**
     * Calculates gain percentage.
     * gainPercent = ((currentValue - investedAmount) / investedAmount) × 100
     */
    public static double calculateGainPercent(double invested, double current) {
        if (invested <= 0) return 0;
        return roundToTwo(((current - invested) / invested) * 100);
    }

    /**
     * Calculates Compound Annual Growth Rate (CAGR).
     * CAGR = (currentValue / investedAmount) ^ (1 / years) - 1
     */
    public static double calculateCAGR(double invested, double current, double years) {
        if (invested <= 0 || years <= 0) return 0;
        return roundToTwo((Math.pow(current / invested, 1.0 / years) - 1) * 100);
    }

    /** Rounds a double to 2 decimal places. */
    public static double roundToTwo(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /** Rounds a double to 4 decimal places (for units). */
    public static double roundToFour(double value) {
        return Math.round(value * 10000.0) / 10000.0;
    }
}
