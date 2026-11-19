package model;

/**
 * Represents one scheduled execution entry for a SIP in a given month.
 * Stored in a PriorityQueue sorted by scheduledDate so earliest-due SIPs run first.
 */
public class SIPSchedule implements Comparable<SIPSchedule> {
    private String  scheduleId;
    private String  sipId;
    private String  scheduledDate;   // yyyy-MM-dd  (e.g., "2024-03-01")
    private boolean isExecuted;
    private String  executedDate;    // actual date of execution (or null)

    public SIPSchedule() {}

    public SIPSchedule(String scheduleId, String sipId, String scheduledDate) {
        this.scheduleId    = scheduleId;
        this.sipId         = sipId;
        this.scheduledDate = scheduledDate;
        this.isExecuted    = false;
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String  getScheduleId()    { return scheduleId;    }
    public String  getSipId()         { return sipId;         }
    public String  getScheduledDate() { return scheduledDate; }
    public boolean isExecuted()       { return isExecuted;    }
    public String  getExecutedDate()  { return executedDate;  }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setScheduleId(String scheduleId)      { this.scheduleId    = scheduleId;    }
    public void setSipId(String sipId)                { this.sipId         = sipId;         }
    public void setScheduledDate(String scheduledDate){ this.scheduledDate = scheduledDate; }
    public void setExecuted(boolean executed)         { this.isExecuted    = executed;      }
    public void setExecutedDate(String executedDate)  { this.executedDate  = executedDate;  }

    /**
     * PriorityQueue ordering: earliest scheduledDate is processed first.
     * Uses lexicographic comparison which works correctly for yyyy-MM-dd format.
     */
    @Override
    public int compareTo(SIPSchedule other) {
        return this.scheduledDate.compareTo(other.scheduledDate);
    }

    @Override
    public String toString() {
        return "[" + scheduleId + "] SIP: " + sipId + " | Due: " + scheduledDate
                + " | Executed: " + (isExecuted ? "Yes (" + executedDate + ")" : "No");
    }
}
