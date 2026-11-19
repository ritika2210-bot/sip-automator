package exception;

/** Thrown when attempting to redeem more units than the investor holds. */
public class InsufficientUnitsException extends RuntimeException {
    public InsufficientUnitsException(double requested, double available) {
        super("Cannot redeem " + String.format("%.4f", requested)
                + " units. Only " + String.format("%.4f", available) + " available.");
    }
}
