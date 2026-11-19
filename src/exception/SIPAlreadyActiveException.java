package exception;

/** Thrown when creating a SIP that already exists and is ACTIVE for the same fund. */
public class SIPAlreadyActiveException extends RuntimeException {
    public SIPAlreadyActiveException(String investorId, String fundId) {
        super("Active SIP already exists for investor '" + investorId
                + "' on fund '" + fundId + "'.");
    }
}
