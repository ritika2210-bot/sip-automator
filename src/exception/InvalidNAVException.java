package exception;

/** Thrown when a NAV value is zero or negative (which is impossible for a real fund). */
public class InvalidNAVException extends RuntimeException {
    public InvalidNAVException(double nav) {
        super("NAV must be greater than zero. Provided: " + nav);
    }
}
