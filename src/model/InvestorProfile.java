package model;

/**
 * Detailed KYC and financial profile of an investor.
 * Linked 1:1 with an Investor via investorId.
 */
public class InvestorProfile {
    private String profileId;
    private String investorId;
    private String panNumber;           // e.g., "ABCDE1234F"
    private String dateOfBirth;         // yyyy-MM-dd
    private String occupation;          // "Salaried", "Business", "Student"
    private double annualIncome;        // in ₹
    private String investmentGoal;      // "Retirement", "Education", "Wealth"
    private int    investmentHorizon;   // years planned to invest

    public InvestorProfile() {}

    public InvestorProfile(String profileId, String investorId, String panNumber,
                           String dateOfBirth, String occupation, double annualIncome,
                           String investmentGoal, int investmentHorizon) {
        this.profileId          = profileId;
        this.investorId         = investorId;
        this.panNumber          = panNumber;
        this.dateOfBirth        = dateOfBirth;
        this.occupation         = occupation;
        this.annualIncome       = annualIncome;
        this.investmentGoal     = investmentGoal;
        this.investmentHorizon  = investmentHorizon;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String getProfileId()         { return profileId;         }
    public String getInvestorId()        { return investorId;        }
    public String getPanNumber()         { return panNumber;         }
    public String getDateOfBirth()       { return dateOfBirth;       }
    public String getOccupation()        { return occupation;        }
    public double getAnnualIncome()      { return annualIncome;      }
    public String getInvestmentGoal()    { return investmentGoal;    }
    public int    getInvestmentHorizon() { return investmentHorizon; }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setProfileId(String profileId)               { this.profileId         = profileId;         }
    public void setInvestorId(String investorId)             { this.investorId        = investorId;        }
    public void setPanNumber(String panNumber)               { this.panNumber         = panNumber;         }
    public void setDateOfBirth(String dateOfBirth)           { this.dateOfBirth       = dateOfBirth;       }
    public void setOccupation(String occupation)             { this.occupation        = occupation;        }
    public void setAnnualIncome(double annualIncome)         { this.annualIncome      = annualIncome;      }
    public void setInvestmentGoal(String investmentGoal)     { this.investmentGoal    = investmentGoal;    }
    public void setInvestmentHorizon(int investmentHorizon)  { this.investmentHorizon = investmentHorizon; }

    @Override
    public String toString() {
        return "Profile[" + profileId + "] | PAN: " + panNumber
                + " | DOB: " + dateOfBirth + " | " + occupation
                + " | Income: ₹" + annualIncome
                + " | Goal: " + investmentGoal + " (" + investmentHorizon + " yrs)";
    }
}
