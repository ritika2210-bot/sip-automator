package service;

import enums.SIPStatus;
import enums.TransactionType;
import exception.SIPAlreadyActiveException;
import model.*;
import util.DateUtil;
import util.IDGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for creating, managing, and executing SIPs.
 *
 * Uses LinkedList<SIP> as a queue — SIPs are dequeued and processed one by one.
 */
public class SIPService {

    private final LinkedList<SIP>    sipQueue    = new LinkedList<>();
    private final TransactionService txnService;
    private final FundService        fundService;

    public SIPService(TransactionService txnService, FundService fundService) {
        this.txnService  = txnService;
        this.fundService = fundService;
    }

    // ── Create / Modify SIPs ───────────────────────────────────────────────────

    /**
     * Creates a new ACTIVE SIP for an investor.
     * @throws SIPAlreadyActiveException if an ACTIVE SIP already exists for the same fund.
     */
    public SIP createSIP(Investor investor, Fund fund, double monthlyAmount, int executionDay) {
        // Check for duplicate active SIP
        boolean duplicate = sipQueue.stream().anyMatch(s ->
                s.getInvestor().getInvestorId().equals(investor.getInvestorId())
                && s.getFund().getFundId().equals(fund.getFundId())
                && s.getSipStatus() == SIPStatus.ACTIVE);
        if (duplicate) throw new SIPAlreadyActiveException(investor.getInvestorId(), fund.getFundId());

        SIP sip = new SIP(IDGenerator.generateSIPId(), investor, fund, monthlyAmount,
                          DateUtil.today(), executionDay);
        sipQueue.addLast(sip);
        System.out.println("[SIPService] Created: " + sip);
        return sip;
    }

    /**
     * Executes one SIP installment: buys units at current NAV.
     * Creates a Transaction and updates the investor's Portfolio.
     */
    public Transaction executeSIP(SIP sip) {
        if (sip.getSipStatus() != SIPStatus.ACTIVE) {
            System.out.println("[SIPService] Skipped (not active): " + sip.getSipId());
            return null;
        }

        Fund   fund    = sip.getFund();
        double nav     = fundService.getLatestNAV(fund.getFundId());
        double amount  = sip.getMonthlyAmount();
        double units   = fundService.calculateUnits(amount, nav);
        String today   = DateUtil.today();

        Transaction txn = new Transaction(
                IDGenerator.generateTransactionId(),
                sip.getInvestor().getInvestorId(),
                fund.getFundId(),
                TransactionType.BUY,
                amount, nav, today, sip.getSipId()
        );

        // Update portfolio
        Portfolio portfolio = sip.getInvestor().getPortfolio();
        portfolio.addUnits(fund.getFundId(), units, amount);
        portfolio.addTransaction(txn);
        sip.recordInstallment(amount);

        txnService.recordTransaction(txn);
        System.out.printf("[SIPService] Executed %s | ₹%.2f @ NAV ₹%.4f = %.4f units%n",
                sip.getSipId(), amount, nav, units);
        return txn;
    }

    /**
     * Processes the entire active-SIP queue (all ACTIVE SIPs).
     */
    public void executeAllActiveSIPs() {
        System.out.println("\n[SIPService] Running all active SIPs...");
        int count = 0;
        for (SIP sip : sipQueue) {
            if (sip.getSipStatus() == SIPStatus.ACTIVE) {
                executeSIP(sip);
                count++;
            }
        }
        System.out.println("[SIPService] Executed " + count + " SIP(s).\n");
    }

    public void pauseSIP(SIP sip)  { sip.setSipStatus(SIPStatus.PAUSED);    System.out.println("[SIPService] Paused: "    + sip.getSipId()); }
    public void resumeSIP(SIP sip) { sip.setSipStatus(SIPStatus.ACTIVE);    System.out.println("[SIPService] Resumed: "   + sip.getSipId()); }
    public void cancelSIP(SIP sip) { sip.setSipStatus(SIPStatus.CANCELLED); System.out.println("[SIPService] Cancelled: " + sip.getSipId()); }

    public List<SIP> getSIPsByInvestor(String investorId) {
        return sipQueue.stream()
                .filter(s -> s.getInvestor().getInvestorId().equals(investorId))
                .collect(Collectors.toList());
    }

    public List<SIP> getAllSIPs() { return new LinkedList<>(sipQueue); }
}
