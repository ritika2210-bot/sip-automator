package model;

/**
 * Represents the professional who manages a mutual fund.
 */
public class FundManager {
    private String managerId;
    private String name;
    private int    experience;      // Years of experience
    private String qualification;   // e.g., "MBA Finance", "CFA"
    private int    fundsManaged;    // Number of funds currently managing

    public FundManager() {}

    public FundManager(String managerId, String name, int experience,
                       String qualification, int fundsManaged) {
        this.managerId     = managerId;
        this.name          = name;
        this.experience    = experience;
        this.qualification = qualification;
        this.fundsManaged  = fundsManaged;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String getManagerId()     { return managerId;     }
    public String getName()          { return name;          }
    public int    getExperience()    { return experience;    }
    public String getQualification() { return qualification; }
    public int    getFundsManaged()  { return fundsManaged;  }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setManagerId(String managerId)         { this.managerId     = managerId;     }
    public void setName(String name)                   { this.name          = name;          }
    public void setExperience(int experience)          { this.experience    = experience;    }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public void setFundsManaged(int fundsManaged)      { this.fundsManaged  = fundsManaged;  }

    @Override
    public String toString() {
        return name + " [" + managerId + "] | " + qualification
                + " | " + experience + " yrs exp | Manages " + fundsManaged + " fund(s)";
    }
}
