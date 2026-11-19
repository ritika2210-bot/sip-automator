package util;

/**
 * Formats output for the console: tables, headers, currency, percentages.
 */
public class ReportFormatter {

    private static final int TABLE_WIDTH = 80;
    private static final String SEPARATOR = "-".repeat(TABLE_WIDTH);

    /** Prints a boxed title. */
    public static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(TABLE_WIDTH));
        System.out.printf("  %s%n", title.toUpperCase());
        System.out.println("=".repeat(TABLE_WIDTH));
    }

    /** Prints a thin separator line. */
    public static void printSeparator() {
        System.out.println(SEPARATOR);
    }

    /**
     * Prints a simple aligned table.
     * @param headers Column headers
     * @param rows    2D array of string values
     */
    public static void printTable(String[] headers, String[][] rows) {
        // Calculate column widths
        int[] widths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            widths[i] = headers[i].length();
        }
        for (String[] row : rows) {
            for (int i = 0; i < row.length && i < widths.length; i++) {
                widths[i] = Math.max(widths[i], row[i] == null ? 0 : row[i].length());
            }
        }

        // Print header row
        StringBuilder fmt = new StringBuilder();
        for (int w : widths) fmt.append("%-").append(w + 2).append("s");
        fmt.append("%n");
        String format = fmt.toString();
        System.out.printf(format, (Object[]) headers);
        printSeparator();
        for (String[] row : rows) {
            System.out.printf(format, (Object[]) row);
        }
        printSeparator();
    }

    /** Formats a rupee amount. Example: 125000.0 → "₹1,25,000.00" */
    public static String formatCurrency(double amount) {
        boolean negative = amount < 0;
        long paise = Math.round(Math.abs(amount) * 100);
        long rupees = paise / 100;
        long paiseRem = paise % 100;

        // Indian number formatting (groups of 2 after first 3)
        String rupeeStr = String.valueOf(rupees);
        if (rupeeStr.length() > 3) {
            StringBuilder sb = new StringBuilder(rupeeStr);
            int pos = sb.length() - 3;
            while (pos > 0) {
                sb.insert(pos, ',');
                pos -= 2;
            }
            rupeeStr = sb.toString();
        }
        return (negative ? "-₹" : "₹") + rupeeStr + "." + String.format("%02d", paiseRem);
    }

    /** Formats a percentage with sign. Example: 12.5 → "+12.50%" */
    public static String formatPercent(double value) {
        return (value >= 0 ? "+" : "") + String.format("%.2f", value) + "%";
    }
}
