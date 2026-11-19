package util;

import java.util.regex.Pattern;

/**
 * Input validation utilities for the SIP Automater.
 */
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN  = Pattern.compile("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN  = Pattern.compile("^[6-9]\\d{9}$");   // Indian mobile
    private static final Pattern PAN_PATTERN    = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

    /** Validates email address format. */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /** Validates Indian 10-digit mobile number (starts with 6-9). */
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    /** Validates Indian PAN format: ABCDE1234F */
    public static boolean isValidPAN(String pan) {
        return pan != null && PAN_PATTERN.matcher(pan).matches();
    }

    /** Validates that an investment amount is a positive number. */
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    /** Validates that a date string is in yyyy-MM-dd format. */
    public static boolean isValidDate(String date) {
        return DateUtil.isValidDate(date);
    }

    /** Validates that a pincode is 6 digits. */
    public static boolean isValidPincode(String pincode) {
        return pincode != null && pincode.matches("^[1-9]\\d{5}$");
    }
}
