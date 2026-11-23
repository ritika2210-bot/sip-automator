package model;

// Person who will inherit the investment if investor passes away
public class Nominee {

    private String name;
    private String relationship;      // e.g. "Spouse", "Child", "Parent"
    private String dateOfBirth;       // yyyy-MM-dd
    private double sharePercentage;   // 0 to 100

    // Constructor
    public Nominee(String name, String relationship, String dateOfBirth, double sharePercentage) {
        this.name            = name;
        this.relationship    = relationship;
        this.dateOfBirth     = dateOfBirth;
        this.sharePercentage = sharePercentage;
    }

    // Getters
    public String getName()             { return name;            }
    public String getRelationship()     { return relationship;    }
    public String getDateOfBirth()      { return dateOfBirth;     }
    public double getSharePercentage()  { return sharePercentage; }

    // Setters
    public void setName(String name)                       { this.name            = name;            }
    public void setRelationship(String relationship)       { this.relationship    = relationship;    }
    public void setDateOfBirth(String dateOfBirth)         { this.dateOfBirth     = dateOfBirth;     }
    public void setSharePercentage(double sharePercentage) { this.sharePercentage = sharePercentage; }

    @Override
    public String toString() {
        return name + " (" + relationship + ") — " + sharePercentage + "% share";
    }
}
