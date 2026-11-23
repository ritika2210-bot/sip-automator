package service;

import data.DataStore;
import enums.SIPStatus;
import enums.TransactionType;
import model.*;

import java.util.List;

/**
 * SIPService — create, manage, and process SIP installments.
 */
public class SIPService {

    private DataStore           ds;
    private TransactionService  txnService;

    public SIPService(DataStore ds, TransactionService txnService) {
        this.ds         = ds;
        this.txnService = txnService;
    }

    // ── Create a new SIP ─────────────────────────────────────────────
    public SIP createSIP(String investorId, String fundId,
                         double monthlyAmount, int sipDay,
                         String startDate, String endDate, int totalInstallments) {

        // Validate investor and fund exist
        if (ds.findInvestorById(investorId) == null)
            throw new IllegalArgumentException("Investor not found: " + investorId);
        Fund fund = ds.findFundById(fundId);
        if (fund == null)
            throw new IllegalArgumentException("Fund not found: " + fundId);
        if (monthlyAmount < 500)
            throw new IllegalArgumentException("Minimum SIP amount is ₹500.");
        if (sipDay < 1 || sipDay > 28)
            throw new IllegalArgumentException("SIP day must be between 1 and 28.");

        String sipId = ds.nextSIPId();
        SIP sip = new SIP(sipId, investorId, fundId,
                          monthlyAmount, sipDay, startDate, endDate, totalInstallments);
        ds.addSIP(sip);
        System.out.println("✅ SIP created: " + sip);
        return sip;
    }

    // ── Process one SIP installment ──────────────────────────────────
    public Transaction processInstallment(String sipId, String date) {
        SIP sip = ds.findSIPById(sipId);
        if (sip == null) throw new IllegalArgumentException("SIP not found: " + sipId);
        if (sip.getStatus() != SIPStatus.ACTIVE)
            throw new IllegalStateException("SIP " + sipId + " is not active (status: " + sip.getStatus() + ")");

        Fund fund = ds.findFundById(sip.getFundId());
        if (fund == null) throw new IllegalArgumentException("Fund not found for SIP: " + sipId);

        double nav = fund.getCurrentNAV();
        sip.recordInstallment(nav);

        // Create a transaction record
        Transaction txn = txnService.recordTransaction(
                sip.getInvestorId(), sip.getFundId(), sipId,
                TransactionType.BUY, sip.getMonthlyAmount(), nav, date);

        System.out.println("✅ Installment processed for " + sipId +
                           " | Units bought: " + String.format("%.4f", txn.getUnits()) +
                           " @ NAV ₹" + nav);
        return txn;
    }

    // ── Process ALL active SIPs (monthly run) ────────────────────────
    public void processAllActiveSIPs(String date) {
        System.out.println("\n📅 Processing all active SIPs for date: " + date);
        int count = 0;
        for (SIP sip : ds.getSIPs()) {
            if (sip.getStatus() == SIPStatus.ACTIVE) {
                try {
                    processInstallment(sip.getSipId(), date);
                    count++;
                } catch (Exception e) {
                    System.out.println("⚠️  Failed SIP " + sip.getSipId() + ": " + e.getMessage());
                }
            }
        }
        System.out.println("✅ Processed " + count + " SIP installments.\n");
    }

    // ── Pause a SIP ──────────────────────────────────────────────────
    public void pauseSIP(String sipId) {
        SIP sip = getSIPById(sipId);
        sip.setStatus(SIPStatus.PAUSED);
        System.out.println("⏸️  SIP paused: " + sipId);
    }

    // ── Resume a paused SIP ──────────────────────────────────────────
    public void resumeSIP(String sipId) {
        SIP sip = getSIPById(sipId);
        if (sip.getStatus() != SIPStatus.PAUSED)
            throw new IllegalStateException("SIP is not paused.");
        sip.setStatus(SIPStatus.ACTIVE);
        System.out.println("▶️  SIP resumed: " + sipId);
    }

    // ── Cancel a SIP ─────────────────────────────────────────────────
    public void cancelSIP(String sipId) {
        SIP sip = getSIPById(sipId);
        sip.setStatus(SIPStatus.CANCELLED);
        System.out.println("❌ SIP cancelled: " + sipId);
    }

    // ── Modify SIP amount ────────────────────────────────────────────
    public void modifySIPAmount(String sipId, double newAmount) {
        if (newAmount < 500) throw new IllegalArgumentException("Minimum SIP amount is ₹500.");
        SIP sip = getSIPById(sipId);
        sip.setMonthlyAmount(newAmount);
        System.out.println("✅ SIP " + sipId + " amount updated to ₹" + newAmount);
    }

    // ── Get SIP by ID ────────────────────────────────────────────────
    public SIP getSIPById(String sipId) {
        SIP sip = ds.findSIPById(sipId);
        if (sip == null) throw new IllegalArgumentException("SIP not found: " + sipId);
        return sip;
    }

    // ── Get all SIPs for an investor ─────────────────────────────────
    public List<SIP> getSIPsByInvestor(String investorId) {
        return ds.getSIPsByInvestor(investorId);
    }

    // ── Print SIP summary ────────────────────────────────────────────
    public void printSIPSummary(String investorId) {
        List<SIP> sips = getSIPsByInvestor(investorId);
        Fund fund;
        System.out.println("═══════════════════════════════════════");
        System.out.println("  SIP SUMMARY — Investor: " + investorId);
        System.out.println("═══════════════════════════════════════");
        for (SIP sip : sips) {
            fund = ds.findFundById(sip.getFundId());
            double currentNAV = fund != null ? fund.getCurrentNAV() : 0;
            System.out.println("  " + sip);
            if (currentNAV > 0) {
                System.out.println("    Current Value: ₹" +
                        String.format("%.2f", sip.getCurrentValue(currentNAV)) +
                        " | Gain/Loss: ₹" +
                        String.format("%.2f", sip.getGainLoss(currentNAV)));
            }
        }
        System.out.println("═══════════════════════════════════════");
    }
}
