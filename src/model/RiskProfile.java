package model;

import enums.RiskLevel;

// Tells how much risk the investor can handle
public class RiskProfile {

    private String    profileId;
    private RiskLevel riskLevel;        // LOW / MEDIUM / HIGH
    private int       equityPercent;    // Suggested % in equity (e.g. 70)
    private int       debtPercent;      // Suggested % in debt   (e.g. 30)
    private int       riskScore;        // Score out of 100
    private String    assessmentDate;   // When this was assessed

    // Constructor
    public RiskProfile(String profileId, RiskLevel riskLevel,
                       int equityPercent, int debtPercent,
                       int riskScore, String assessmentDate) {
        this.profileId      = profileId;
        this.riskLevel      = riskLevel;
        this.equityPercent  = equityPercent;
        this.debtPercent    = debtPercent;
        this.riskScore      = riskScore;
        this.assessmentDate = assessmentDate;
    }

    // Getters
    public String    getProfileId()      { return profileId;      }
    public RiskLevel getRiskLevel()      { return riskLevel;      }
    public int       getEquityPercent()  { return equityPercent;  }
    public int       getDebtPercent()    { return debtPercent;    }
    public int       getRiskScore()      { return riskScore;      }
    public String    getAssessmentDate() { return assessmentDate; }

    // Setters
    public void setProfileId(String profileId)           { this.profileId      = profileId;      }
    public void setRiskLevel(RiskLevel riskLevel)        { this.riskLevel      = riskLevel;      }
    public void setEquityPercent(int equityPercent)      { this.equityPercent  = equityPercent;  }
    public void setDebtPercent(int debtPercent)          { this.debtPercent    = debtPercent;    }
    public void setRiskScore(int riskScore)              { this.riskScore      = riskScore;      }
    public void setAssessmentDate(String assessmentDate) { this.assessmentDate = assessmentDate; }

    @Override
    public String toString() {
        return riskLevel + " risk (Score: " + riskScore + "/100)" +
               " | Equity: " + equityPercent + "% | Debt: " + debtPercent + "%";
    }
}
