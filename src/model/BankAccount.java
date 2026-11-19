package model;

// Bank account linked to investor for SIP auto-debit
public class BankAccount {

    private String  accountNumber;
    private String  bankName;
    private String  ifscCode;
    private String  accountType;  // "Savings" or "Current"
    private boolean isVerified;

    // Constructor
    public BankAccount(String accountNumber, String bankName, String ifscCode, String accountType) {
        this.accountNumber = accountNumber;
        this.bankName      = bankName;
        this.ifscCode      = ifscCode;
        this.accountType   = accountType;
        this.isVerified    = false;
    }

    // Getters
    public String  getAccountNumber() { return accountNumber; }
    public String  getBankName()      { return bankName;      }
    public String  getIfscCode()      { return ifscCode;      }
    public String  getAccountType()   { return accountType;   }
    public boolean isVerified()       { return isVerified;    }

    // Setters
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setBankName(String bankName)           { this.bankName      = bankName;      }
    public void setIfscCode(String ifscCode)           { this.ifscCode      = ifscCode;      }
    public void setAccountType(String accountType)     { this.accountType   = accountType;   }
    public void setVerified(boolean verified)          { this.isVerified    = verified;      }

    @Override
    public String toString() {
        return bankName + " | " + accountType + " | A/C: " + accountNumber + " | IFSC: " + ifscCode;
    }
}
