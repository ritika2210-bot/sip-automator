package model;

import enums.RiskLevel;

/**
 * Investor's risk tolerance profile, determined via a questionnaire.
 */
public class RiskProfile {
    private String    profileId;
    private RiskLevel riskLevel;        // LOW / MEDIUM / HIGH
    private int       equityAllocation; // Suggested equity % (e.g., 70)
    private int       debtAllocation;   // Suggested debt %  (e.g., 30)
    private String    assessmentDate;   // yyyy-MM-dd
    private int       score;            // 0–100 risk score

    public RiskProfile() {}

    public RiskProfile(String profileId, RiskLevel riskLevel, int equityAllocation,
                       int debtAllocation, String assessmentDate, int score) {
        this.profileId        = profileId;
        this.riskLevel        = riskLevel;
        this.equityAllocation = equityAllocation;
        this.debtAllocation   = debtAllocation;
        this.assessmentDate   = assessmentDate;
        this.score            = score;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String    getProfileId()        { return profileId;        }
    public RiskLevel getRiskLevel()        { return riskLevel;        }
    public int       getEquityAllocation() { return equityAllocation; }
    public int       getDebtAllocation()   { return debtAllocation;   }
    public String    getAssessmentDate()   { return assessmentDate;   }
    public int       getScore()            { return score;            }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setProfileId(String profileId)               { this.profileId        = profileId;        }
    public void setRiskLevel(RiskLevel riskLevel)             { this.riskLevel        = riskLevel;        }
    public void setEquityAllocation(int equityAllocation)    { this.equityAllocation = equityAllocation; }
    public void setDebtAllocation(int debtAllocation)        { this.debtAllocation   = debtAllocation;   }
    public void setAssessmentDate(String assessmentDate)     { this.assessmentDate   = assessmentDate;   }
    public void setScore(int score)                          { this.score            = score;            }

    @Override
    public String toString() {
        return "Risk: " + riskLevel + " (Score: " + score + "/100) | "
                + "Equity: " + equityAllocation + "% | Debt: " + debtAllocation + "%";
    }
}
