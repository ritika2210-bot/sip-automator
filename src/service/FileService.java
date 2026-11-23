package service;

import data.DataStore;
import model.*;

import java.io.*;
import java.nio.file.*;
import java.util.List;

/**
 * FileService — saves and loads core data as CSV flat files in the /data directory.
 *
 * File layout:
 *   data/investors.csv
 *   data/funds.csv
 *   data/sips.csv
 *   data/transactions.csv
 *   data/nav_history.csv
 *   data/redemptions.csv
 *   data/capital_gains.csv
 */
public class FileService {

    private static final String DATA_DIR = "data";

    public FileService() {
        // Ensure the data directory exists
        new File(DATA_DIR).mkdirs();
    }

    // ══════════════════════════════════════════════════════════════════
    //  SAVE methods
    // ══════════════════════════════════════════════════════════════════

    public void saveAll(DataStore ds) {
        saveInvestors(ds.getInvestors());
        saveFunds(ds.getFunds());
        saveSIPs(ds.getSIPs());
        saveTransactions(ds.getTransactions());
        saveNAVHistory(ds.getNAVHistory());
        saveRedemptions(ds.getRedemptions());
        saveCapitalGains(ds.getCapitalGains());
        System.out.println("✅ All data saved to /" + DATA_DIR + "/");
    }

    public void saveInvestors(List<Investor> investors) {
        try (PrintWriter pw = new PrintWriter(DATA_DIR + "/investors.csv")) {
            pw.println("investorId,name,email,phone,isActive,registrationDate");
            for (Investor inv : investors) {
                pw.printf("%s,%s,%s,%s,%b,%s%n",
                        inv.getInvestorId(), inv.getName(),
                        inv.getEmail(), inv.getPhone(),
                        inv.isActive(), inv.getRegistrationDate());
            }
        } catch (IOException e) {
            System.err.println("❌ Error saving investors: " + e.getMessage());
        }
    }

    public void saveFunds(List<Fund> funds) {
        try (PrintWriter pw = new PrintWriter(DATA_DIR + "/funds.csv")) {
            pw.println("fundId,fundName,fundHouse,fundType,riskLevel,currentNAV,expenseRatio,inceptionDate,isActive");
            for (Fund f : funds) {
                pw.printf("%s,%s,%s,%s,%s,%.4f,%.2f,%s,%b%n",
                        f.getFundId(), f.getFundName(), f.getFundHouse(),
                        f.getFundType(), f.getRiskLevel(),
                        f.getCurrentNAV(), f.getExpenseRatio(),
                        f.getInceptionDate(), f.isActive());
            }
        } catch (IOException e) {
            System.err.println("❌ Error saving funds: " + e.getMessage());
        }
    }

    public void saveSIPs(List<SIP> sips) {
        try (PrintWriter pw = new PrintWriter(DATA_DIR + "/sips.csv")) {
            pw.println("sipId,investorId,fundId,monthlyAmount,sipDay,startDate,endDate," +
                       "status,totalInstallments,paidInstallments,totalInvested,totalUnits");
            for (SIP s : sips) {
                pw.printf("%s,%s,%s,%.2f,%d,%s,%s,%s,%d,%d,%.2f,%.4f%n",
                        s.getSipId(), s.getInvestorId(), s.getFundId(),
                        s.getMonthlyAmount(), s.getSipDay(),
                        s.getStartDate(), (s.getEndDate() != null ? s.getEndDate() : ""),
                        s.getStatus(), s.getTotalInstallments(),
                        s.getPaidInstallments(), s.getTotalInvested(), s.getTotalUnits());
            }
        } catch (IOException e) {
            System.err.println("❌ Error saving SIPs: " + e.getMessage());
        }
    }

    public void saveTransactions(List<Transaction> txns) {
        try (PrintWriter pw = new PrintWriter(DATA_DIR + "/transactions.csv")) {
            pw.println("transactionId,investorId,fundId,sipId,type,amount,nav,units,transactionDate");
            for (Transaction t : txns) {
                pw.printf("%s,%s,%s,%s,%s,%.2f,%.4f,%.4f,%s%n",
                        t.getTransactionId(), t.getInvestorId(), t.getFundId(),
                        (t.getSipId() != null ? t.getSipId() : ""),
                        t.getType(), t.getAmount(), t.getNav(),
                        t.getUnits(), t.getTransactionDate());
            }
        } catch (IOException e) {
            System.err.println("❌ Error saving transactions: " + e.getMessage());
        }
    }

    public void saveNAVHistory(List<NAVHistory> navList) {
        try (PrintWriter pw = new PrintWriter(DATA_DIR + "/nav_history.csv")) {
            pw.println("navId,fundId,date,nav");
            for (NAVHistory n : navList) {
                pw.printf("%s,%s,%s,%.4f%n",
                        n.getNavId(), n.getFundId(), n.getDate(), n.getNav());
            }
        } catch (IOException e) {
            System.err.println("❌ Error saving NAV history: " + e.getMessage());
        }
    }

    public void saveRedemptions(List<Redemption> redemptions) {
        try (PrintWriter pw = new PrintWriter(DATA_DIR + "/redemptions.csv")) {
            pw.println("redemptionId,investorId,fundId,redeemedUnits,redeemedAmount," +
                       "navAtRedemption,redemptionDate,isProcessed");
            for (Redemption r : redemptions) {
                pw.printf("%s,%s,%s,%.4f,%.2f,%.4f,%s,%b%n",
                        r.getRedemptionId(), r.getInvestorId(), r.getFundId(),
                        r.getRedeemedUnits(), r.getRedeemedAmount(),
                        r.getNavAtRedemption(), r.getRedemptionDate(), r.isProcessed());
            }
        } catch (IOException e) {
            System.err.println("❌ Error saving redemptions: " + e.getMessage());
        }
    }

    public void saveCapitalGains(List<CapitalGain> gains) {
        try (PrintWriter pw = new PrintWriter(DATA_DIR + "/capital_gains.csv")) {
            pw.println("gainId,investorId,fundId,redemptionId,buyNAV,sellNAV,units," +
                       "gainAmount,gainType,taxCategory,taxRate,taxAmount,holdingDays,computedDate");
            for (CapitalGain cg : gains) {
                pw.printf("%s,%s,%s,%s,%.4f,%.4f,%.4f,%.2f,%s,%s,%.2f,%.2f,%d,%s%n",
                        cg.getGainId(), cg.getInvestorId(), cg.getFundId(),
                        cg.getRedemptionId(), cg.getBuyNAV(), cg.getSellNAV(),
                        cg.getUnits(), cg.getGainAmount(), cg.getGainType(),
                        cg.getTaxCategory(), cg.getTaxRate(), cg.getTaxAmount(),
                        cg.getHoldingDays(), cg.getComputedDate());
            }
        } catch (IOException e) {
            System.err.println("❌ Error saving capital gains: " + e.getMessage());
        }
    }

    // ══════════════════════════════════════════════════════════════════
    //  LOAD methods (simple CSV readers for persistence round-trip)
    // ══════════════════════════════════════════════════════════════════

    public void loadNAVHistory(DataStore ds) {
        File f = new File(DATA_DIR + "/nav_history.csv");
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 4) continue;
                ds.addNAVHistory(new NAVHistory(p[0], p[1], p[2], Double.parseDouble(p[3])));
            }
            System.out.println("✅ NAV history loaded from file.");
        } catch (IOException e) {
            System.err.println("❌ Error loading NAV history: " + e.getMessage());
        }
    }

    // ── Generic: list files in data dir ─────────────────────────────
    public void listDataFiles() {
        File dir = new File(DATA_DIR);
        System.out.println("📁 Data files in /" + DATA_DIR + ":");
        if (!dir.exists() || dir.listFiles() == null) {
            System.out.println("  (empty)");
            return;
        }
        for (File file : dir.listFiles()) {
            System.out.printf("  %-30s %,d bytes%n", file.getName(), file.length());
        }
    }
}
