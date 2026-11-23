package model;

// Person who manages a mutual fund
public class FundManager {

    private String managerId;
    private String name;
    private int    experienceYears;
    private String qualification;    // e.g. "MBA Finance", "CFA"

    // Constructor
    public FundManager(String managerId, String name, int experienceYears, String qualification) {
        this.managerId       = managerId;
        this.name            = name;
        this.experienceYears = experienceYears;
        this.qualification   = qualification;
    }

    // Getters
    public String getManagerId()       { return managerId;       }
    public String getName()            { return name;            }
    public int    getExperienceYears() { return experienceYears; }
    public String getQualification()   { return qualification;   }

    // Setters
    public void setManagerId(String managerId)           { this.managerId       = managerId;       }
    public void setName(String name)                     { this.name            = name;            }
    public void setExperienceYears(int experienceYears)  { this.experienceYears = experienceYears; }
    public void setQualification(String qualification)   { this.qualification   = qualification;   }

    @Override
    public String toString() {
        return name + " (" + qualification + ", " + experienceYears + " yrs exp)";
    }
}
