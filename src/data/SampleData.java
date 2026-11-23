package data;

import enums.*;
import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * SampleData — pre-loaded test data for development and demo.
 * Call SampleData.load() to get a populated DataStore.
 */
public class SampleData {

    public static DataStore load() {
        DataStore ds = new DataStore();

        // ── Fund Managers ──────────────────────────────────────────────
        FundManager fm1 = new FundManager("FM001", "Prashant Jain", 22, "MBA Finance, CFA");
        FundManager fm2 = new FundManager("FM002", "Nilesh Shah",   18, "MBA, CA");
        FundManager fm3 = new FundManager("FM003", "Radhika Gupta", 15, "MBA, CFA");

        // ── Funds ──────────────────────────────────────────────────────
        Fund f1 = new Fund("F001", "HDFC Top 100",         "HDFC AMC",
                FundType.EQUITY, RiskLevel.HIGH,   985.75, 1.05, "2000-01-01");
        f1.setFundManager(fm1);

        Fund f2 = new Fund("F002", "SBI Blue Chip",        "SBI MF",
                FundType.EQUITY, RiskLevel.MEDIUM, 72.30,  1.02, "2006-02-15");

        Fund f3 = new Fund("F003", "HDFC Short Term Debt", "HDFC AMC",
                FundType.DEBT,   RiskLevel.LOW,    25.60,  0.40, "2010-06-01");
        f3.setFundManager(fm1);

        Fund f4 = new Fund("F004", "Kotak Balanced",       "Kotak AMC",
                FundType.HYBRID, RiskLevel.MEDIUM, 48.90,  0.75, "2012-03-10");
        f4.setFundManager(fm2);

        Fund f5 = new Fund("F005", "Edelweiss Liquid",     "Edelweiss AMC",
                FundType.LIQUID, RiskLevel.LOW,    1021.50,0.15, "2008-09-20");
        f5.setFundManager(fm3);

        ds.addFund(f1); ds.addFund(f2); ds.addFund(f3); ds.addFund(f4); ds.addFund(f5);

        // ── Addresses ─────────────────────────────────────────────────
        Address addr1 = new Address("12, MG Road",        "Bengaluru", "Karnataka",   "560001");
        Address addr2 = new Address("45, Andheri West",   "Mumbai",    "Maharashtra", "400053");
        Address addr3 = new Address("7, Connaught Place", "New Delhi", "Delhi",       "110001");

        // ── Bank Accounts ─────────────────────────────────────────────
        BankAccount ba1 = new BankAccount("HDFC0012345678", "HDFC Bank",   "HDFC0001234", "Savings");
        ba1.setVerified(true);
        BankAccount ba2 = new BankAccount("ICICI0087654321","ICICI Bank",  "ICIC0005678", "Savings");
        ba2.setVerified(true);
        BankAccount ba3 = new BankAccount("SBI009876543",   "SBI",         "SBIN0003456", "Savings");
        ba3.setVerified(true);

        // ── Nominees ──────────────────────────────────────────────────
        Nominee n1 = new Nominee("Rohit Sharma", "Spouse",  "1990-05-15", 100.0);
        Nominee n2 = new Nominee("Anjali Mehta", "Father",  "1965-08-20", 100.0);
        Nominee n3 = new Nominee("Riya Kapoor",  "Sibling", "1995-03-10", 100.0);

        // ── Risk Profiles ─────────────────────────────────────────────
        RiskProfile rp1 = new RiskProfile("RP001", RiskLevel.HIGH,   75, 25, 80, "2024-01-10");
        RiskProfile rp2 = new RiskProfile("RP002", RiskLevel.MEDIUM, 55, 45, 60, "2024-02-15");
        RiskProfile rp3 = new RiskProfile("RP003", RiskLevel.LOW,    30, 70, 35, "2024-03-01");

        // ── Investors ─────────────────────────────────────────────────
        Investor inv1 = new Investor("INV001", "Ritika Sharma",
                "ritika@email.com", "9876543210",
                addr1, ba1, n1, "2024-01-15");
        inv1.setRiskProfile(rp1);

        Investor inv2 = new Investor("INV002", "Arjun Mehta",
                "arjun@email.com", "9123456789",
                addr2, ba2, n2, "2024-02-20");
        inv2.setRiskProfile(rp2);

        Investor inv3 = new Investor("INV003", "Sneha Kapoor",
                "sneha@email.com", "9988776655",
                addr3, ba3, n3, "2024-03-05");
        inv3.setRiskProfile(rp3);

        ds.addInvestor(inv1); ds.addInvestor(inv2); ds.addInvestor(inv3);

        // ── SIPs ──────────────────────────────────────────────────────
        SIP sip1 = new SIP("SIP001", "INV001", "F001", 5000.0,  5,  "2024-01-05", null, 0);
        SIP sip2 = new SIP("SIP002", "INV001", "F003", 2000.0,  5,  "2024-01-05", null, 0);
        SIP sip3 = new SIP("SIP003", "INV002", "F004", 3000.0,  10, "2024-02-10", null, 12);
        SIP sip4 = new SIP("SIP004", "INV003", "F002", 1500.0,  15, "2024-03-15", null, 0);
        SIP sip5 = new SIP("SIP005", "INV002", "F005", 10000.0, 1,  "2024-02-01", null, 0);

        // Simulate some installments already paid
        sip1.recordInstallment(960.00);  sip1.recordInstallment(985.75);
        sip2.recordInstallment(25.10);   sip2.recordInstallment(25.60);
        sip3.recordInstallment(47.50);   sip3.recordInstallment(48.90);
        sip4.recordInstallment(70.20);   sip4.recordInstallment(72.30);
        sip5.recordInstallment(1020.00); sip5.recordInstallment(1021.50);

        ds.addSIP(sip1); ds.addSIP(sip2); ds.addSIP(sip3);
        ds.addSIP(sip4); ds.addSIP(sip5);

        // ── Transactions ──────────────────────────────────────────────
        Transaction t1 = new Transaction("TXN001", "INV001", "F001", "SIP001",
                TransactionType.BUY, 5000.0, 960.00, "2024-01-05");
        Transaction t2 = new Transaction("TXN002", "INV001", "F001", "SIP001",
                TransactionType.BUY, 5000.0, 985.75, "2024-02-05");
        Transaction t3 = new Transaction("TXN003", "INV001", "F003", "SIP002",
                TransactionType.BUY, 2000.0, 25.10,  "2024-01-05");
        Transaction t4 = new Transaction("TXN004", "INV001", "F003", "SIP002",
                TransactionType.BUY, 2000.0, 25.60,  "2024-02-05");
        Transaction t5 = new Transaction("TXN005", "INV002", "F004", "SIP003",
                TransactionType.BUY, 3000.0, 47.50,  "2024-02-10");
        Transaction t6 = new Transaction("TXN006", "INV002", "F004", "SIP003",
                TransactionType.BUY, 3000.0, 48.90,  "2024-03-10");
        Transaction t7 = new Transaction("TXN007", "INV003", "F002", "SIP004",
                TransactionType.BUY, 1500.0, 70.20,  "2024-03-15");
        Transaction t8 = new Transaction("TXN008", "INV003", "F002", "SIP004",
                TransactionType.BUY, 1500.0, 72.30,  "2024-04-15");

        ds.addTransaction(t1); ds.addTransaction(t2); ds.addTransaction(t3);
        ds.addTransaction(t4); ds.addTransaction(t5); ds.addTransaction(t6);
        ds.addTransaction(t7); ds.addTransaction(t8);

        // ── NAV History ───────────────────────────────────────────────
        ds.addNAVHistory(new NAVHistory("NAV001", "F001", "2024-01-05", 960.00));
        ds.addNAVHistory(new NAVHistory("NAV002", "F001", "2024-02-05", 985.75));
        ds.addNAVHistory(new NAVHistory("NAV003", "F001", "2024-03-05", 1012.30));
        ds.addNAVHistory(new NAVHistory("NAV004", "F002", "2024-03-15", 70.20));
        ds.addNAVHistory(new NAVHistory("NAV005", "F002", "2024-04-15", 72.30));
        ds.addNAVHistory(new NAVHistory("NAV006", "F003", "2024-01-05", 25.10));
        ds.addNAVHistory(new NAVHistory("NAV007", "F003", "2024-02-05", 25.60));
        ds.addNAVHistory(new NAVHistory("NAV008", "F004", "2024-02-10", 47.50));
        ds.addNAVHistory(new NAVHistory("NAV009", "F004", "2024-03-10", 48.90));
        ds.addNAVHistory(new NAVHistory("NAV010", "F005", "2024-02-01",1020.00));
        ds.addNAVHistory(new NAVHistory("NAV011", "F005", "2024-03-01",1021.50));

        return ds;
    }
}
