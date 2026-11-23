package service;

import data.DataStore;
import enums.TransactionType;
import model.Transaction;

import java.util.List;

/**
 * TransactionService — record and query transactions.
 */
public class TransactionService {

    private DataStore ds;

    public TransactionService(DataStore ds) {
        this.ds = ds;
    }

    // ── Record a transaction ─────────────────────────────────────────
    public Transaction recordTransaction(String investorId, String fundId, String sipId,
                                         TransactionType type, double amount,
                                         double nav, String date) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
        if (nav <= 0)    throw new IllegalArgumentException("NAV must be positive.");

        String txnId = ds.nextTxnId();
        Transaction txn = new Transaction(txnId, investorId, fundId, sipId, type, amount, nav, date);
        ds.addTransaction(txn);
        return txn;
    }

    // ── Get all transactions for an investor ─────────────────────────
    public List<Transaction> getTransactionsByInvestor(String investorId) {
        return ds.getTransactionsByInvestor(investorId);
    }

    // ── Get all transactions for a fund ──────────────────────────────
    public List<Transaction> getTransactionsByFund(String fundId) {
        return ds.getTransactionsByFund(fundId);
    }

    // ── Get all BUY transactions for investor + fund ─────────────────
    public List<Transaction> getBuyTransactions(String investorId, String fundId) {
        return ds.getTransactionsByInvestor(investorId).stream()
                 .filter(t -> t.getFundId().equals(fundId) && t.getType() == TransactionType.BUY)
                 .toList();
    }

    // ── Total invested by investor in a fund ─────────────────────────
    public double getTotalInvested(String investorId, String fundId) {
        return getBuyTransactions(investorId, fundId)
               .stream().mapToDouble(Transaction::getAmount).sum();
    }

    // ── Total units held by investor in a fund ───────────────────────
    public double getTotalUnitsHeld(String investorId, String fundId) {
        double bought = ds.getTransactionsByInvestor(investorId).stream()
                .filter(t -> t.getFundId().equals(fundId) && t.getType() == TransactionType.BUY)
                .mapToDouble(Transaction::getUnits).sum();
        double sold = ds.getTransactionsByInvestor(investorId).stream()
                .filter(t -> t.getFundId().equals(fundId) && t.getType() == TransactionType.SELL)
                .mapToDouble(Transaction::getUnits).sum();
        return bought - sold;
    }

    // ── Print transaction statement ──────────────────────────────────
    public void printStatement(String investorId) {
        List<Transaction> txns = getTransactionsByInvestor(investorId);
        System.out.println("═══════════════════════════════════════");
        System.out.println("  TRANSACTION STATEMENT — " + investorId);
        System.out.println("═══════════════════════════════════════");
        if (txns.isEmpty()) {
            System.out.println("  No transactions found.");
        } else {
            for (Transaction t : txns) System.out.println("  " + t);
        }
        System.out.println("═══════════════════════════════════════");
    }
}
