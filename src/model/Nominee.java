package model;

/**
 * Represents a nominee (beneficiary) for an investor's portfolio.
 * In case of investor's death, nominee receives the proceeds.
 */
public class Nominee {
    private String nomineeName;
    private String relationship;      // "Spouse", "Child", "Parent"
    private String dateOfBirth;       // yyyy-MM-dd
    private double sharePercentage;   // 0–100

    public Nominee() {}

    public Nominee(String nomineeName, String relationship, String dateOfBirth, double sharePercentage) {
        this.nomineeName       = nomineeName;
        this.relationship      = relationship;
        this.dateOfBirth       = dateOfBirth;
        this.sharePercentage   = sharePercentage;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String getNomineeName()      { return nomineeName;      }
    public String getRelationship()     { return relationship;     }
    public String getDateOfBirth()      { return dateOfBirth;      }
    public double getSharePercentage()  { return sharePercentage;  }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setNomineeName(String nomineeName)         { this.nomineeName      = nomineeName;      }
    public void setRelationship(String relationship)       { this.relationship     = relationship;     }
    public void setDateOfBirth(String dateOfBirth)         { this.dateOfBirth      = dateOfBirth;      }
    public void setSharePercentage(double sharePercentage) { this.sharePercentage  = sharePercentage;  }

    @Override
    public String toString() {
        return nomineeName + " (" + relationship + ") — Share: " + sharePercentage + "%";
    }
}
