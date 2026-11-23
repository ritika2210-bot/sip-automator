package data;

import model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DataStore — in-memory storage for all entities.
 * Acts as a simple repository; replaced by file I/O in Day 4.
 */
public class DataStore {

    private List<Investor>   investors   = new ArrayList<>();
    private List<Fund>       funds       = new ArrayList<>();
    private List<SIP>        sips        = new ArrayList<>();
    private List<Transaction>transactions = new ArrayList<>();
    private List<NAVHistory> navHistory  = new ArrayList<>();
    private List<Redemption> redemptions = new ArrayList<>();
    private List<CapitalGain>capitalGains = new ArrayList<>();

    // ── Investors ─────────────────────────────────────────────────────
    public void addInvestor(Investor i)   { investors.add(i); }
    public List<Investor> getInvestors()  { return investors; }
    public Investor findInvestorById(String id) {
        return investors.stream().filter(i -> i.getInvestorId().equals(id)).findFirst().orElse(null);
    }

    // ── Funds ─────────────────────────────────────────────────────────
    public void addFund(Fund f)       { funds.add(f); }
    public List<Fund> getFunds()      { return funds; }
    public Fund findFundById(String id) {
        return funds.stream().filter(f -> f.getFundId().equals(id)).findFirst().orElse(null);
    }

    // ── SIPs ──────────────────────────────────────────────────────────
    public void addSIP(SIP s)        { sips.add(s); }
    public List<SIP> getSIPs()       { return sips; }
    public SIP findSIPById(String id) {
        return sips.stream().filter(s -> s.getSipId().equals(id)).findFirst().orElse(null);
    }
    public List<SIP> getSIPsByInvestor(String investorId) {
        List<SIP> result = new ArrayList<>();
        for (SIP s : sips) if (s.getInvestorId().equals(investorId)) result.add(s);
        return result;
    }

    // ── Transactions ──────────────────────────────────────────────────
    public void addTransaction(Transaction t)       { transactions.add(t); }
    public List<Transaction> getTransactions()      { return transactions; }
    public List<Transaction> getTransactionsByInvestor(String investorId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions)
            if (t.getInvestorId().equals(investorId)) result.add(t);
        return result;
    }
    public List<Transaction> getTransactionsByFund(String fundId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions)
            if (t.getFundId().equals(fundId)) result.add(t);
        return result;
    }

    // ── NAV History ───────────────────────────────────────────────────
    public void addNAVHistory(NAVHistory n)     { navHistory.add(n); }
    public List<NAVHistory> getNAVHistory()     { return navHistory; }
    public List<NAVHistory> getNAVByFund(String fundId) {
        List<NAVHistory> result = new ArrayList<>();
        for (NAVHistory n : navHistory) if (n.getFundId().equals(fundId)) result.add(n);
        return result;
    }
    public double getLatestNAV(String fundId) {
        double latest = 0;
        String latestDate = "";
        for (NAVHistory n : navHistory) {
            if (n.getFundId().equals(fundId) && n.getDate().compareTo(latestDate) >= 0) {
                latestDate = n.getDate();
                latest     = n.getNav();
            }
        }
        return latest;
    }

    // ── Redemptions ───────────────────────────────────────────────────
    public void addRedemption(Redemption r)    { redemptions.add(r); }
    public List<Redemption> getRedemptions()   { return redemptions; }
    public Redemption findRedemptionById(String id) {
        return redemptions.stream().filter(r -> r.getRedemptionId().equals(id)).findFirst().orElse(null);
    }

    // ── Capital Gains ─────────────────────────────────────────────────
    public void addCapitalGain(CapitalGain cg)   { capitalGains.add(cg); }
    public List<CapitalGain> getCapitalGains()    { return capitalGains; }
    public List<CapitalGain> getGainsByInvestor(String investorId) {
        List<CapitalGain> result = new ArrayList<>();
        for (CapitalGain cg : capitalGains)
            if (cg.getInvestorId().equals(investorId)) result.add(cg);
        return result;
    }

    // ── Counters (for ID generation) ──────────────────────────────────
    public String nextInvestorId()   { return String.format("INV%03d", investors.size()   + 1); }
    public String nextFundId()       { return String.format("F%03d",   funds.size()       + 1); }
    public String nextSIPId()        { return String.format("SIP%03d", sips.size()        + 1); }
    public String nextTxnId()        { return String.format("TXN%03d", transactions.size()+ 1); }
    public String nextRedemptionId() { return String.format("RED%03d", redemptions.size() + 1); }
    public String nextGainId()       { return String.format("CG%03d",  capitalGains.size()+ 1); }
    public String nextNAVId()        { return String.format("NAV%03d", navHistory.size()  + 1); }
}
