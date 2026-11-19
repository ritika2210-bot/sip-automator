package exception;

/** Thrown when an investor ID cannot be found in the repository. */
public class InvestorNotFoundException extends RuntimeException {
    public InvestorNotFoundException(String investorId) {
        super("Investor '" + investorId + "' not found.");
    }
}
