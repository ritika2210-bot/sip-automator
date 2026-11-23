package model;

// Stores historical NAV for a fund on a given date
public class NAVHistory {

    private String navId;    // Unique ID like "NAV001"
    private String fundId;
    private String date;     // yyyy-MM-dd
    private double nav;      // NAV on that date

    // Constructor
    public NAVHistory(String navId, String fundId, String date, double nav) {
        this.navId  = navId;
        this.fundId = fundId;
        this.date   = date;
        this.nav    = nav;
    }

    // Getters
    public String getNavId()  { return navId;  }
    public String getFundId() { return fundId; }
    public String getDate()   { return date;   }
    public double getNav()    { return nav;    }

    // Setters
    public void setNavId(String navId)   { this.navId  = navId;  }
    public void setFundId(String fundId) { this.fundId = fundId; }
    public void setDate(String date)     { this.date   = date;   }
    public void setNav(double nav)       { this.nav    = nav;    }

    @Override
    public String toString() {
        return "[" + navId + "] Fund: " + fundId + " | Date: " + date + " | NAV: ₹" + nav;
    }
}
