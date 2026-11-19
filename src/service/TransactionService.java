package service;

import enums.TransactionType;
import model.Transaction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for recording and retrieving investment transactions.
 *
 * Collections used:
 *   ArrayList<Transaction>  → full ordered history (all transactions ever)
 *   ArrayDeque<Transaction> → last 10 transactions buffer (recent activity)
 */
public class TransactionService {

    private static final int RECENT_BUFFER_SIZE = 10;

    private final ArrayList<Transaction>  transactionHistory = new ArrayList<>();
    private final ArrayDeque<Transaction> recentTransactions = new ArrayDeque<>(RECENT_BUFFER_SIZE);

    /** Records a new transaction in history and updates the recent buffer. */
    public void recordTransaction(Transaction txn) {
        transactionHistory.add(txn);

        if (recentTransactions.size() >= RECENT_BUFFER_SIZE) {
            recentTransactions.pollFirst(); // remove oldest
        }
        recentTransactions.addLast(txn);
        System.out.println("[TransactionService] Recorded: " + txn);
    }

    public List<Transaction> getTransactionsByInvestor(String investorId) {
        return transactionHistory.stream()
                .filter(t -> t.getInvestorId().equals(investorId))
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByFund(String fundId) {
        return transactionHistory.stream()
                .filter(t -> t.getFundId().equals(fundId))
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsBetweenDates(String from, String to) {
        return transactionHistory.stream()
                .filter(t -> t.getDate().compareTo(from) >= 0 && t.getDate().compareTo(to) <= 0)
                .collect(Collectors.toList());
    }

    public List<Transaction> getBuyTransactionsByInvestorAndFund(String investorId, String fundId) {
        return transactionHistory.stream()
                .filter(t -> t.getInvestorId().equals(investorId)
                          && t.getFundId().equals(fundId)
                          && t.getTransactionType() == TransactionType.BUY)
                .collect(Collectors.toList());
    }

    /** Returns the last ≤10 transactions buffer (most recent first). */
    public ArrayDeque<Transaction> getRecentTransactions() { return recentTransactions; }

    /** Returns the entire transaction history list. */
    public ArrayList<Transaction> getAllTransactions() { return transactionHistory; }
}
