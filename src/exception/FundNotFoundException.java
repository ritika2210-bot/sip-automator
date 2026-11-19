package exception;

/** Thrown when a fund ID cannot be found in the repository. */
public class FundNotFoundException extends RuntimeException {
    public FundNotFoundException(String fundId) {
        super("Fund '" + fundId + "' not found.");
    }
}
