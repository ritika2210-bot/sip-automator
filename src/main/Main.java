package main;

import enums.FundType;
import enums.RiskLevel;
import model.*;
import service.*;
import util.DateUtil;
import util.ReportFormatter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

/**
 * ════════════════════════════════════════════════════════
 *   SIP AUTOMATER — Console Application Entry Point
 *   Author : ritika2210-bot
 *   Email  : ritika.46452210@gmail.com
 * ════════════════════════════════════════════════════════
 *
 * This is the main menu that ties all services together.
 * Run this class to launch the full application.
 */
public class Main {

    // ── Service layer ──────────────────────────────────────────────────────────
    private static final TransactionService  txnService     = new TransactionService();
    private static final FundService         fundService    = new FundService();
    private static final TaxService          taxService     = new TaxService();
    private static final SIPService          sipService     = new SIPService(txnService, fundService);
    private static final RedemptionService   redemptionSvc  = new RedemptionService(taxService, fundService);
    private static final InvestorService     investorSvc    = new InvestorService();
    private static final RebalancingService  rebalanceSvc   = new RebalancingService(fundService);

    private static final Scanner scanner = new Scanner(System.in);

    // ── Sample data loaded at startup ──────────────────────────────────────────
    private static Investor sampleInvestor;
    private static Fund     sampleFund1;
    private static Fund     sampleFund2;

    public static void main(String[] args) {
        loadSampleData();
        mainMenu();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  MAIN MENU
    // ══════════════════════════════════════════════════════════════════════════
    private static void mainMenu() {
        while (true) {
            ReportFormatter.printHeader("SIP AUTOMATER — Investment Manager");
            System.out.println("  1. Investor Management");
            System.out.println("  2. Fund Management");
            System.out.println("  3. SIP Operations");
            System.out.println("  4. Portfolio View");
            System.out.println("  5. Rebalancing Report");
            System.out.println("  6. Redemption (Sell Units)");
            System.out.println("  7. Capital Gain Tax Report");
            System.out.println("  8. Transaction History");
            System.out.println("  0. Exit");
            ReportFormatter.printSeparator();
            System.out.print("  Enter choice: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> investorMenu();
                case "2" -> fundMenu();
                case "3" -> sipMenu();
                case "4" -> portfolioMenu();
                case "5" -> rebalancingMenu();
                case "6" -> redemptionMenu();
                case "7" -> taxMenu();
                case "8" -> transactionMenu();
                case "0" -> { System.out.println("\n  Thank you for using SIP Automater. Goodbye!\n"); return; }
                default  -> System.out.println("  [!] Invalid choice. Try again.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  1. INVESTOR MANAGEMENT
    // ══════════════════════════════════════════════════════════════════════════
    private static void investorMenu() {
        ReportFormatter.printHeader("Investor Management");
        System.out.println("  1. Register New Investor");
        System.out.println("  2. View All Investors");
        System.out.println("  3. Assess Risk Profile");
        System.out.println("  0. Back");
        System.out.print("  Choice: ");
        String c = scanner.nextLine().trim();

        switch (c) {
            case "1" -> registerInvestorFlow();
            case "2" -> {
                List<Investor> all = investorSvc.getAllInvestors();
                if (all.isEmpty()) { System.out.println("  No investors registered yet."); return; }
                ReportFormatter.printSeparator();
                all.forEach(inv -> System.out.println("  " + inv));
                ReportFormatter.printSeparator();
            }
            case "3" -> {
                System.out.print("  Enter Investor ID: ");
                String id = scanner.nextLine().trim();
                System.out.print("  Investment horizon (years): ");
                int horizon = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("  Annual income (₹): ");
                double income = Double.parseDouble(scanner.nextLine().trim());
                RiskProfile rp = investorSvc.assessRisk(id, horizon, income);
                System.out.println("  " + rp);
            }
        }
    }

    private static void registerInvestorFlow() {
        System.out.println("  — New Investor Registration —");
        System.out.print("  Name       : "); String name  = scanner.nextLine().trim();
        System.out.print("  Email      : "); String email = scanner.nextLine().trim();
        System.out.print("  Phone      : "); String phone = scanner.nextLine().trim();
        System.out.print("  City       : "); String city  = scanner.nextLine().trim();
        System.out.print("  State      : "); String state = scanner.nextLine().trim();
        System.out.print("  Pincode    : "); String pin   = scanner.nextLine().trim();
        System.out.print("  Bank Name  : "); String bank  = scanner.nextLine().trim();
        System.out.print("  Account No : "); String acc   = scanner.nextLine().trim();
        System.out.print("  IFSC       : "); String ifsc  = scanner.nextLine().trim();
        System.out.print("  Nominee Name: "); String nom  = scanner.nextLine().trim();

        Address     addr     = new Address("N/A", city, state, pin);
        BankAccount bankAcct = new BankAccount(acc, bank, ifsc, "Savings");
        Nominee     nominee  = new Nominee(nom, "Family", "2000-01-01", 100.0);
        Investor inv = investorSvc.registerInvestor(name, email, phone, addr, bankAcct, nominee);
        System.out.println("  ✅ Registered: " + inv.getInvestorId() + " — " + inv.getName());
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  2. FUND MANAGEMENT
    // ══════════════════════════════════════════════════════════════════════════
    private static void fundMenu() {
        ReportFormatter.printHeader("Fund Management");
        List<Fund> funds = fundService.getAllFunds();
        if (funds.isEmpty()) {
            System.out.println("  No funds available.");
        } else {
            String[] headers = { "Fund ID", "Fund Name", "Type", "Risk", "NAV (₹)", "Expense %" };
            String[][] rows  = funds.stream().map(f -> new String[]{
                    f.getFundId(), f.getFundName(), f.getFundType().toString(),
                    f.getRiskLevel().toString(), String.format("%.4f", f.getCurrentNAV()),
                    f.getExpenseRatio() + "%"
            }).toArray(String[][]::new);
            ReportFormatter.printTable(headers, rows);
        }
        System.out.println("  Press ENTER to go back...");
        scanner.nextLine();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  3. SIP OPERATIONS
    // ══════════════════════════════════════════════════════════════════════════
    private static void sipMenu() {
        ReportFormatter.printHeader("SIP Operations");
        System.out.println("  1. Create New SIP");
        System.out.println("  2. Execute All Active SIPs");
        System.out.println("  3. View My SIPs");
        System.out.println("  0. Back");
        System.out.print("  Choice: ");
        String c = scanner.nextLine().trim();

        switch (c) {
            case "1" -> {
                System.out.print("  Investor ID   : "); String invId   = scanner.nextLine().trim();
                System.out.print("  Fund ID       : "); String fundId  = scanner.nextLine().trim();
                System.out.print("  Monthly Amount: ₹"); double amount = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("  Execution Day : "); int day        = Integer.parseInt(scanner.nextLine().trim());
                try {
                    Investor inv  = investorSvc.getInvestorById(invId);
                    Fund     fund = fundService.getFundById(fundId);
                    SIP      sip  = sipService.createSIP(inv, fund, amount, day);
                    System.out.println("  ✅ SIP created: " + sip.getSipId());
                } catch (Exception e) { System.out.println("  ❌ " + e.getMessage()); }
            }
            case "2" -> sipService.executeAllActiveSIPs();
            case "3" -> {
                System.out.print("  Investor ID: "); String invId = scanner.nextLine().trim();
                sipService.getSIPsByInvestor(invId).forEach(s -> System.out.println("  " + s));
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  4. PORTFOLIO VIEW
    // ══════════════════════════════════════════════════════════════════════════
    private static void portfolioMenu() {
        ReportFormatter.printHeader("Portfolio View");
        System.out.print("  Enter Investor ID: ");
        String invId = scanner.nextLine().trim();

        try {
            Investor  inv  = investorSvc.getInvestorById(invId);
            Portfolio port = inv.getPortfolio();

            System.out.println("\n  Investor  : " + inv.getName() + " (" + invId + ")");
            System.out.println("  As of     : " + DateUtil.formatDate(DateUtil.today()));
            ReportFormatter.printSeparator();

            if (port.getHoldings().isEmpty()) {
                System.out.println("  No holdings yet. Create and execute a SIP first.");
            } else {
                double totalInvested = 0, totalCurrent = 0;
                for (var entry : port.getHoldings().entrySet()) {
                    String fundId   = entry.getKey();
                    double units    = entry.getValue();
                    double nav      = fundService.getLatestNAV(fundId);
                    double invested = port.getInvestedAmountForFund(fundId);
                    double value    = units * nav;
                    double gain     = value - invested;
                    totalInvested += invested;
                    totalCurrent  += value;
                    System.out.printf("  %-8s | Units: %10.4f | Invested: %s | Value: %s | Gain: %s%n",
                            fundId, units,
                            ReportFormatter.formatCurrency(invested),
                            ReportFormatter.formatCurrency(value),
                            ReportFormatter.formatCurrency(gain));
                }
                ReportFormatter.printSeparator();
                double totalGain = totalCurrent - totalInvested;
                System.out.printf("  TOTAL     | Invested: %s | Current: %s | Gain: %s (%s)%n",
                        ReportFormatter.formatCurrency(totalInvested),
                        ReportFormatter.formatCurrency(totalCurrent),
                        ReportFormatter.formatCurrency(totalGain),
                        ReportFormatter.formatPercent(totalInvested > 0 ? (totalGain / totalInvested) * 100 : 0));
            }
        } catch (Exception e) { System.out.println("  ❌ " + e.getMessage()); }

        System.out.println("\n  Press ENTER to continue...");
        scanner.nextLine();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  5. REBALANCING
    // ══════════════════════════════════════════════════════════════════════════
    private static void rebalancingMenu() {
        ReportFormatter.printHeader("Portfolio Rebalancing Report");
        System.out.print("  Enter Investor ID: ");
        String invId = scanner.nextLine().trim();
        try {
            Investor inv = investorSvc.getInvestorById(invId);

            // Set example target if not already set
            LinkedHashMap<String, Double> target = new LinkedHashMap<>();
            target.put(sampleFund1.getFundId(), 60.0);
            target.put(sampleFund2.getFundId(), 40.0);
            rebalanceSvc.setTargetAllocation(invId, target);

            RebalancingReport report = rebalanceSvc.generateReport(inv);
            System.out.println("\n  " + report);
            ReportFormatter.printSeparator();
            report.getEntries().forEach(e -> System.out.println("  → " + e));
        } catch (Exception e) { System.out.println("  ❌ " + e.getMessage()); }
        System.out.println("\n  Press ENTER to continue...");
        scanner.nextLine();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  6. REDEMPTION
    // ══════════════════════════════════════════════════════════════════════════
    private static void redemptionMenu() {
        ReportFormatter.printHeader("Redemption (Sell Units)");
        System.out.print("  Investor ID : "); String invId  = scanner.nextLine().trim();
        System.out.print("  Fund ID     : "); String fundId = scanner.nextLine().trim();
        System.out.print("  Units to sell (0 = ALL): ");
        double units = Double.parseDouble(scanner.nextLine().trim());
        try {
            Investor inv = investorSvc.getInvestorById(invId);
            Redemption r = units == 0 ? redemptionSvc.redeemAll(inv, fundId)
                                      : redemptionSvc.redeemUnits(inv, fundId, units);
            System.out.println("  ✅ " + r);
            if (r.getCapitalGain() != null) System.out.println("  Tax: " + r.getCapitalGain());
        } catch (Exception e) { System.out.println("  ❌ " + e.getMessage()); }
        System.out.println("\n  Press ENTER to continue...");
        scanner.nextLine();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  7. TAX REPORT
    // ══════════════════════════════════════════════════════════════════════════
    private static void taxMenu() {
        ReportFormatter.printHeader("Capital Gain Tax Report");
        System.out.print("  Investor ID     : "); String invId = scanner.nextLine().trim();
        System.out.print("  Financial Year (e.g. 2024-25): "); String fy = scanner.nextLine().trim();
        try {
            TaxReport report = taxService.generateTaxReport(invId, fy);
            System.out.println("\n  " + report);
            ReportFormatter.printSeparator();
            report.getGains().forEach(g -> System.out.println("  " + g));
        } catch (Exception e) { System.out.println("  ❌ " + e.getMessage()); }
        System.out.println("\n  Press ENTER to continue...");
        scanner.nextLine();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  8. TRANSACTION HISTORY
    // ══════════════════════════════════════════════════════════════════════════
    private static void transactionMenu() {
        ReportFormatter.printHeader("Transaction History");
        System.out.println("  1. All transactions for an investor");
        System.out.println("  2. Recent 10 transactions");
        System.out.print("  Choice: ");
        String c = scanner.nextLine().trim();
        if (c.equals("1")) {
            System.out.print("  Investor ID: "); String id = scanner.nextLine().trim();
            txnService.getTransactionsByInvestor(id).forEach(t -> System.out.println("  " + t));
        } else {
            txnService.getRecentTransactions().forEach(t -> System.out.println("  " + t));
        }
        System.out.println("\n  Press ENTER to continue...");
        scanner.nextLine();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  SAMPLE DATA BOOTSTRAP
    // ══════════════════════════════════════════════════════════════════════════
    private static void loadSampleData() {
        System.out.println("  [Bootstrap] Loading sample data...");

        // ── 2 Sample Funds ─────────────────────────────────────────────────────
        sampleFund1 = new Fund("F001", "HDFC Top 100 Fund", "HDFC AMC",
                FundType.EQUITY, RiskLevel.HIGH, 985.75, "2000-01-01", 1.05);
        sampleFund2 = new Fund("F002", "SBI Short Duration Fund", "SBI MF",
                FundType.DEBT, RiskLevel.LOW, 46.30, "2010-06-01", 0.65);
        fundService.addFund(sampleFund1);
        fundService.addFund(sampleFund2);
        fundService.updateNAV("F001", 985.75, DateUtil.today());
        fundService.updateNAV("F002", 46.30,  DateUtil.today());

        // ── 1 Sample Investor ──────────────────────────────────────────────────
        Address addr     = new Address("12 MG Road", "Chandigarh", "Punjab", "160001");
        BankAccount bank = new BankAccount("12345678901", "HDFC Bank", "HDFC0001234", "Savings");
        Nominee nom      = new Nominee("Ramesh", "Spouse", "1992-04-15", 100.0);

        sampleInvestor = investorSvc.registerInvestor(
                "Ritika Sharma", "ritika.46452210@gmail.com", "9876543210",
                addr, bank, nom);

        investorSvc.assessRisk(sampleInvestor.getInvestorId(), 10, 1500000.0);

        // ── 2 Sample SIPs ──────────────────────────────────────────────────────
        SIP sip1 = sipService.createSIP(sampleInvestor, sampleFund1, 5000.0, 1);
        SIP sip2 = sipService.createSIP(sampleInvestor, sampleFund2, 3000.0, 5);

        // ── Run 1 month of SIPs ────────────────────────────────────────────────
        sipService.executeAllActiveSIPs();

        System.out.println("  [Bootstrap] Sample data ready. Investor ID: "
                + sampleInvestor.getInvestorId() + "\n");
    }
}
