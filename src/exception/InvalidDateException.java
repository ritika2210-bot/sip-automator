package exception;

/** Thrown when a date string is invalid or end date is before start date. */
public class InvalidDateException extends RuntimeException {
    public InvalidDateException(String message) {
        super(message);
    }

    public static InvalidDateException badFormat(String date) {
        return new InvalidDateException("Invalid date format: '" + date + "'. Use YYYY-MM-DD.");
    }

    public static InvalidDateException endBeforeStart(String start, String end) {
        return new InvalidDateException("End date '" + end + "' cannot be before start date '" + start + "'.");
    }
}
