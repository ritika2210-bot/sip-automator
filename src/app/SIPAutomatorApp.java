package app;

import data.DataStore;
import data.SampleData;
import enums.RiskLevel;
import model.*;
import service.*;

import java.util.List;
import java.util.Scanner;

/**
 * ╔══════════════════════════════════════════════════╗
 * ║        💹 SIP AUTOMATOR — Console App            ║
 * ║               Day 5 — Full Integration           ║
 * ╚══════════════════════════════════════════════════╝
 *
 * Run:  javac -d out src/enums/*.java src/model/*.java
 *                    src/data/*.java src/service/*.java src/app/*.java
 *       java  -cp out app.SIPAutomatorApp
 */
public class SIPAutomatorApp {

    // ── Shared state ────────────────────────────────────────────────
    private static DataStore          ds;
    private static InvestorService    investorSvc;
    private static FundService        fundSvc;
    private static TransactionService txnSvc;
    private static SIPService         sipSvc;
    private static PortfolioService   portfolioSvc;
    private static RedemptionService  redemptionSvc;
    private static TaxService         taxSvc;
    private static RebalancingService rebalanceSvc;
    private static FileService        fileSvc;
    private static Scanner            sc = new Scanner(System.in);

    // ════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        boot();
        mainMenu();
        System.out.println("\n👋 Goodbye! Your data has been saved.");
    }

    // ── Boot: load sample data and wire services ─────────────────────
    private static void boot() {
        ds           = SampleData.load();
        txnSvc       = new TransactionService(ds);
        investorSvc  = new InvestorService(ds);
        fundSvc      = new FundService(ds);
        sipSvc       = new SIPService(ds, txnSvc);
        portfolioSvc = new PortfolioService(ds, txnSvc);
        redemptionSvc= new RedemptionService(ds, txnSvc);
        taxSvc       = new TaxService(ds);
        rebalanceSvc = new RebalancingService(ds, txnSvc);
        fileSvc      = new FileService();

        printBanner();
        System.out.println("  ✅ Sample data loaded — 3 investors, 5 funds, 5 SIPs ready.\n");
    }

    // ══════════════════════════════════════════════════════════════════
    //  MAIN MENU
    // ══════════════════════════════════════════════════════════════════
    private static void mainMenu() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║         MAIN MENU                ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Investor Management          ║");
            System.out.println("║  2. Fund Management              ║");
            System.out.println("║  3. SIP Management               ║");
            System.out.println("║  4. Portfolio & Reports          ║");
            System.out.println("║  5. Redeem Units                 ║");
            System.out.println("║  6. Tax Report                   ║");
            System.out.println("║  7. Rebalancing Report           ║");
            System.out.println("║  8. Save Data to Files           ║");
            System.out.println("║  9. Quick Demo (auto-run)        ║");
            System.out.println("║  0. Exit                         ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("  Enter choice: ");

            switch (readInt()) {
                case 1  -> investorMenu();
                case 2  -> fundMenu();
                case 3  -> sipMenu();
                case 4  -> reportMenu();
                case 5  -> redeemMenu();
                case 6  -> taxMenu();
                case 7  -> rebalanceMenu();
                case 8  -> saveMenu();
                case 9  -> runDemo();
                case 0  -> { fileSvc.saveAll(ds); return; }
                default -> System.out.println("⚠️  Invalid option.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  1. INVESTOR MENU
    // ══════════════════════════════════════════════════════════════════
    private static void investorMenu() {
        while (true) {
            System.out.println("\n── INVESTOR MANAGEMENT ──────────────");
            System.out.println("  1. List all investors");
            System.out.println("  2. View investor profile");
            System.out.println("  3. Register new investor");
            System.out.println("  4. Update email");
            System.out.println("  5. Update phone");
            System.out.println("  6. Deactivate investor");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (readInt()) {
                case 1 -> listAllInvestors();
                case 2 -> {
                    System.out.print("  Investor ID: ");
                    investorSvc.printInvestorSummary(sc.nextLine().trim());
                }
                case 3 -> registerInvestor();
                case 4 -> {
                    System.out.print("  Investor ID: "); String id = sc.nextLine().trim();
                    System.out.print("  New email  : "); String em = sc.nextLine().trim();
                    investorSvc.updateEmail(id, em);
                }
                case 5 -> {
                    System.out.print("  Investor ID: "); String id = sc.nextLine().trim();
                    System.out.print("  New phone  : "); String ph = sc.nextLine().trim();
                    investorSvc.updatePhone(id, ph);
                }
                case 6 -> {
                    System.out.print("  Investor ID: ");
                    investorSvc.deactivateInvestor(sc.nextLine().trim());
                }
                case 0 -> { return; }
                default -> System.out.println("⚠️  Invalid option.");
            }
        }
    }

    private static void listAllInvestors() {
        List<Investor> list = investorSvc.getAllActiveInvestors();
        System.out.println("\n  ACTIVE INVESTORS (" + list.size() + ")");
        System.out.println("  " + "─".repeat(60));
        for (Investor inv : list) System.out.println("  " + inv);
    }

    private static void registerInvestor() {
        System.out.println("\n  ── Register New Investor ──");
        try {
            System.out.print("  Name        : "); String name  = sc.nextLine().trim();
            System.out.print("  Email       : "); String email = sc.nextLine().trim();
            System.out.print("  Phone       : "); String phone = sc.nextLine().trim();
            System.out.print("  Street      : "); String str   = sc.nextLine().trim();
            System.out.print("  City        : "); String city  = sc.nextLine().trim();
            System.out.print("  State       : "); String state = sc.nextLine().trim();
            System.out.print("  Pincode     : "); String pin   = sc.nextLine().trim();
            System.out.print("  Bank Name   : "); String bank  = sc.nextLine().trim();
            System.out.print("  Acct Number : "); String acct  = sc.nextLine().trim();
            System.out.print("  IFSC Code   : "); String ifsc  = sc.nextLine().trim();
            System.out.print("  Nominee Name: "); String nom   = sc.nextLine().trim();
            System.out.print("  Relationship: "); String rel   = sc.nextLine().trim();
            System.out.print("  Nominee DOB : "); String dob   = sc.nextLine().trim();

            String id = ds.nextInvestorId();
            Address     addr = new Address(str, city, state, pin);
            BankAccount ba   = new BankAccount(acct, bank, ifsc, "Savings");
            Nominee     n    = new Nominee(nom, rel, dob, 100.0);
            Investor    inv  = new Investor(id, name, email, phone, addr, ba, n,
                                            java.time.LocalDate.now().toString());
            investorSvc.registerInvestor(inv);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  2. FUND MENU
    // ══════════════════════════════════════════════════════════════════
    private static void fundMenu() {
        while (true) {
            System.out.println("\n── FUND MANAGEMENT ──────────────────");
            System.out.println("  1. List all funds");
            System.out.println("  2. View fund details");
            System.out.println("  3. Update NAV");
            System.out.println("  4. View NAV history");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (readInt()) {
                case 1 -> fundSvc.listAllFunds();
                case 2 -> {
                    System.out.print("  Fund ID: ");
                    fundSvc.printFundDetails(sc.nextLine().trim());
                }
                case 3 -> {
                    System.out.print("  Fund ID  : "); String fid = sc.nextLine().trim();
                    System.out.print("  New NAV  : "); double nav = readDouble();
                    System.out.print("  Date (yyyy-MM-dd): "); String dt = sc.nextLine().trim();
                    fundSvc.updateNAV(fid, nav, dt);
                }
                case 4 -> {
                    System.out.print("  Fund ID: "); String fid = sc.nextLine().trim();
                    List<NAVHistory> history = fundSvc.getNAVHistory(fid);
                    System.out.println("  NAV History for " + fid + ":");
                    for (NAVHistory n : history) System.out.println("    " + n);
                }
                case 0 -> { return; }
                default -> System.out.println("⚠️  Invalid option.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  3. SIP MENU
    // ══════════════════════════════════════════════════════════════════
    private static void sipMenu() {
        while (true) {
            System.out.println("\n── SIP MANAGEMENT ───────────────────");
            System.out.println("  1. View my SIPs");
            System.out.println("  2. Create new SIP");
            System.out.println("  3. Process one SIP installment");
            System.out.println("  4. Process ALL active SIPs (monthly run)");
            System.out.println("  5. Pause SIP");
            System.out.println("  6. Resume SIP");
            System.out.println("  7. Cancel SIP");
            System.out.println("  8. Modify SIP amount");
            System.out.println("  9. View transaction statement");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.print("  Investor ID: ");
                    sipSvc.printSIPSummary(sc.nextLine().trim());
                }
                case 2 -> createSIP();
                case 3 -> {
                    System.out.print("  SIP ID          : "); String sid = sc.nextLine().trim();
                    System.out.print("  Date (yyyy-MM-dd): "); String dt = sc.nextLine().trim();
                    sipSvc.processInstallment(sid, dt);
                }
                case 4 -> {
                    System.out.print("  Date (yyyy-MM-dd): ");
                    sipSvc.processAllActiveSIPs(sc.nextLine().trim());
                }
                case 5 -> { System.out.print("  SIP ID: "); sipSvc.pauseSIP(sc.nextLine().trim()); }
                case 6 -> { System.out.print("  SIP ID: "); sipSvc.resumeSIP(sc.nextLine().trim()); }
                case 7 -> { System.out.print("  SIP ID: "); sipSvc.cancelSIP(sc.nextLine().trim()); }
                case 8 -> {
                    System.out.print("  SIP ID     : "); String sid = sc.nextLine().trim();
                    System.out.print("  New amount : "); double amt = readDouble();
                    sipSvc.modifySIPAmount(sid, amt);
                }
                case 9 -> {
                    System.out.print("  Investor ID: ");
                    txnSvc.printStatement(sc.nextLine().trim());
                }
                case 0 -> { return; }
                default -> System.out.println("⚠️  Invalid option.");
            }
        }
    }

    private static void createSIP() {
        System.out.println("\n  ── Create New SIP ──");
        try {
            System.out.print("  Investor ID           : "); String inv = sc.nextLine().trim();
            System.out.print("  Fund ID               : "); String fid = sc.nextLine().trim();
            System.out.print("  Monthly Amount (₹)    : "); double amt = readDouble();
            System.out.print("  SIP Debit Day (1-28)  : "); int day    = readInt();
            System.out.print("  Start Date (yyyy-MM-dd): "); String sd = sc.nextLine().trim();
            System.out.print("  End Date (or leave blank): "); String ed = sc.nextLine().trim();
            System.out.print("  Total Installments (0=unlimited): "); int tot = readInt();
            sipSvc.createSIP(inv, fid, amt, day, sd, ed.isBlank() ? null : ed, tot);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  4. PORTFOLIO & REPORTS MENU
    // ══════════════════════════════════════════════════════════════════
    private static void reportMenu() {
        while (true) {
            System.out.println("\n── PORTFOLIO & REPORTS ──────────────");
            System.out.println("  1. View portfolio snapshot");
            System.out.println("  2. View diversification breakdown");
            System.out.println("  3. Full portfolio report (all investors)");
            System.out.println("  0. Back");
            System.out.print("  Choice: ");

            switch (readInt()) {
                case 1 -> { System.out.print("  Investor ID: "); portfolioSvc.printPortfolio(sc.nextLine().trim()); }
                case 2 -> { System.out.print("  Investor ID: "); portfolioSvc.printDiversification(sc.nextLine().trim()); }
                case 3 -> {
                    System.out.println("\n  ═══ FULL PORTFOLIO REPORT — ALL INVESTORS ═══");
                    for (Investor inv : ds.getInvestors()) {
                        portfolioSvc.printPortfolio(inv.getInvestorId());
                    }
                }
                case 0 -> { return; }
                default -> System.out.println("⚠️  Invalid option.");
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  5. REDEEM MENU
    // ══════════════════════════════════════════════════════════════════
    private static void redeemMenu() {
        System.out.println("\n── REDEEM UNITS ─────────────────────");
        System.out.println("  1. Redeem by units");
        System.out.println("  2. Redeem by amount (₹)");
        System.out.println("  0. Back");
        System.out.print("  Choice: ");
        int choice = readInt();
        if (choice == 0) return;
        try {
            System.out.print("  Investor ID           : "); String inv = sc.nextLine().trim();
            System.out.print("  Fund ID               : "); String fid = sc.nextLine().trim();
            System.out.print("  Date (yyyy-MM-dd)     : "); String dt  = sc.nextLine().trim();
            if (choice == 1) {
                System.out.print("  Units to redeem       : "); double units = readDouble();
                redemptionSvc.redeemByUnits(inv, fid, units, dt);
            } else {
                System.out.print("  Amount to redeem (₹)  : "); double amt = readDouble();
                redemptionSvc.redeemByAmount(inv, fid, amt, dt);
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  6. TAX REPORT MENU
    // ══════════════════════════════════════════════════════════════════
    private static void taxMenu() {
        System.out.println("\n── TAX REPORT ───────────────────────");
        System.out.println("  1. Tax report for one investor");
        System.out.println("  2. Tax summary for all investors");
        System.out.println("  0. Back");
        System.out.print("  Choice: ");
        switch (readInt()) {
            case 1 -> { System.out.print("  Investor ID: "); taxSvc.printTaxReport(sc.nextLine().trim()); }
            case 2 -> {
                System.out.println("\n  ═══ TAX SUMMARY — ALL INVESTORS ═══");
                System.out.printf("  %-10s %-20s %12s %12s %12s%n",
                        "ID", "Name", "Total Gain", "Tax Owed", "LTCG");
                System.out.println("  " + "─".repeat(70));
                for (Investor inv : ds.getInvestors()) {
                    String id = inv.getInvestorId();
                    System.out.printf("  %-10s %-20s %12.2f %12.2f %12.2f%n",
                            id, inv.getName(),
                            taxSvc.getTotalGain(id),
                            taxSvc.getTotalTaxLiability(id),
                            taxSvc.getLTCGAmount(id));
                }
            }
            case 0 -> {}
            default -> System.out.println("⚠️  Invalid option.");
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  7. REBALANCING REPORT
    // ══════════════════════════════════════════════════════════════════
    private static void rebalanceMenu() {
        System.out.println("\n── REBALANCING REPORT ───────────────");
        System.out.println("  1. Report for one investor");
        System.out.println("  2. Report for all investors");
        System.out.println("  0. Back");
        System.out.print("  Choice: ");
        switch (readInt()) {
            case 1 -> { System.out.print("  Investor ID: "); rebalanceSvc.printRebalanceReport(sc.nextLine().trim()); }
            case 2 -> { for (Investor inv : ds.getInvestors()) rebalanceSvc.printRebalanceReport(inv.getInvestorId()); }
            case 0 -> {}
            default -> System.out.println("⚠️  Invalid option.");
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  8. SAVE MENU
    // ══════════════════════════════════════════════════════════════════
    private static void saveMenu() {
        fileSvc.saveAll(ds);
        fileSvc.listDataFiles();
    }

    // ══════════════════════════════════════════════════════════════════
    //  9. QUICK DEMO — runs a scripted walkthrough automatically
    // ══════════════════════════════════════════════════════════════════
    private static void runDemo() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║           🎬 QUICK DEMO — AUTO WALKTHROUGH           ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        pause();

        // 1. Show all investors
        System.out.println("\n📋 STEP 1 — All Investors");
        listAllInvestors();
        pause();

        // 2. Show all funds
        System.out.println("\n📋 STEP 2 — All Available Funds");
        fundSvc.listAllFunds();
        pause();

        // 3. Show SIPs for Ritika
        System.out.println("\n📋 STEP 3 — Ritika's SIPs (INV001)");
        sipSvc.printSIPSummary("INV001");
        pause();

        // 4. Process one month of SIPs
        System.out.println("\n📋 STEP 4 — Process All Active SIPs for 2024-05-01");
        sipSvc.processAllActiveSIPs("2024-05-01");
        pause();

        // 5. Transaction statement
        System.out.println("\n📋 STEP 5 — Transaction Statement for Ritika (INV001)");
        txnSvc.printStatement("INV001");
        pause();

        // 6. Portfolio snapshot
        System.out.println("\n📋 STEP 6 — Portfolio Snapshot for Ritika (INV001)");
        portfolioSvc.printPortfolio("INV001");
        pause();

        // 7. Diversification
        System.out.println("\n📋 STEP 7 — Diversification Breakdown for Arjun (INV002)");
        portfolioSvc.printDiversification("INV002");
        pause();

        // 8. Redeem some units for demo
        System.out.println("\n📋 STEP 8 — Redeem 2 units from HDFC Top 100 (INV001, F001)");
        try {
            redemptionSvc.redeemByUnits("INV001", "F001", 2.0, "2024-05-02");
        } catch (Exception e) {
            System.out.println("  (Skipped: " + e.getMessage() + ")");
        }
        pause();

        // 9. Tax report
        System.out.println("\n📋 STEP 9 — Capital Gains Tax Report for Ritika (INV001)");
        taxSvc.printTaxReport("INV001");
        pause();

        // 10. Rebalancing
        System.out.println("\n📋 STEP 10 — Rebalancing Recommendation for Ritika (INV001)");
        rebalanceSvc.printRebalanceReport("INV001");
        pause();

        // 11. Save
        System.out.println("\n📋 STEP 11 — Saving All Data to CSV Files");
        fileSvc.saveAll(ds);
        fileSvc.listDataFiles();

        System.out.println("\n✅ Demo complete! All features demonstrated successfully.");
    }

    // ══════════════════════════════════════════════════════════════════
    //  Helpers
    // ══════════════════════════════════════════════════════════════════
    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║      💹  SIP AUTOMATOR — Investment Manager          ║");
        System.out.println("║         SIP • NAV • Portfolio • Tax • Rebalance      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    private static void pause() {
        System.out.print("\n  [Press ENTER to continue...]");
        sc.nextLine();
    }

    private static int readInt() {
        try {
            String line = sc.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) { return -1; }
    }

    private static double readDouble() {
        try {
            String line = sc.nextLine().trim();
            return Double.parseDouble(line);
        } catch (NumberFormatException e) { return 0; }
    }
}
