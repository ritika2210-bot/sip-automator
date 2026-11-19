package model;

/**
 * Records the NAV (Net Asset Value) of a fund on a specific date.
 * Used to build historical NAV charts and calculate past returns.
 */
public class NAVHistory {
    private String navHistoryId;
    private String fundId;
    private double nav;
    private String date;            // yyyy-MM-dd
    private double changePercent;   // % change from previous day's NAV

    public NAVHistory() {}

    public NAVHistory(String navHistoryId, String fundId, double nav,
                      String date, double changePercent) {
        this.navHistoryId  = navHistoryId;
        this.fundId        = fundId;
        this.nav           = nav;
        this.date          = date;
        this.changePercent = changePercent;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String getNavHistoryId()  { return navHistoryId;  }
    public String getFundId()        { return fundId;        }
    public double getNav()           { return nav;           }
    public String getDate()          { return date;          }
    public double getChangePercent() { return changePercent; }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setNavHistoryId(String navHistoryId)   { this.navHistoryId  = navHistoryId;  }
    public void setFundId(String fundId)               { this.fundId        = fundId;        }
    public void setNav(double nav)                     { this.nav           = nav;           }
    public void setDate(String date)                   { this.date          = date;          }
    public void setChangePercent(double changePercent) { this.changePercent = changePercent; }

    @Override
    public String toString() {
        String sign = changePercent >= 0 ? "+" : "";
        return date + " | " + fundId + " | NAV: ₹" + String.format("%.4f", nav)
                + " | Change: " + sign + String.format("%.2f", changePercent) + "%";
    }
}
