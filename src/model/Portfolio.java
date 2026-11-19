package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Portfolio holds all fund holdings of a single investor.
 *
 * Collections used:
 *   HashMap<String, Double>   holdings         → fundId → units (O(1) lookup)
 *   TreeMap<String, Double>   investedAmounts  → fundId → ₹ invested (auto-sorted)
 *   ArrayList<Transaction>    transactions     → full ordered buy history
 *   LinkedList<Redemption>    redemptions      → sell history (queue-like processing)
 */
public class Portfolio {
    private String                   portfolioId;
    private String                   investorId;
    private HashMap<String, Double>  holdings;        // fundId → units held
    private TreeMap<String, Double>  investedAmounts; // fundId → ₹ invested (sorted)
    private ArrayList<Transaction>   transactions;    // All BUY history
    private LinkedList<Redemption>   redemptions;     // All SELL history

    public Portfolio() {
        this.holdings        = new HashMap<>();
        this.investedAmounts = new TreeMap<>();
        this.transactions    = new ArrayList<>();
        this.redemptions     = new LinkedList<>();
    }

    public Portfolio(String portfolioId, String investorId) {
        this();
        this.portfolioId = portfolioId;
        this.investorId  = investorId;
    }

    // ── Core Operations ────────────────────────────────────────────────────────

    /** Add units to a fund holding after a BUY. */
    public void addUnits(String fundId, double units, double amount) {
        holdings.merge(fundId, units, Double::sum);
        investedAmounts.merge(fundId, amount, Double::sum);
    }

    /** Remove units from a fund holding after a SELL. */
    public void removeUnits(String fundId, double units, double amount) {
        holdings.merge(fundId, -units, Double::sum);
        investedAmounts.merge(fundId, -amount, Double::sum);
        // Clean up zero-balance entries
        holdings.entrySet().removeIf(e -> e.getValue() <= 0.0001);
        investedAmounts.entrySet().removeIf(e -> e.getValue() <= 0.0001);
    }

    public void addTransaction(Transaction t)  { transactions.add(t);       }
    public void addRedemption(Redemption r)    { redemptions.addLast(r);    }

    /** Get current units held in a specific fund. Returns 0 if not held. */
    public double getUnitsForFund(String fundId) {
        return holdings.getOrDefault(fundId, 0.0);
    }

    /** Get total ₹ invested in a specific fund. */
    public double getInvestedAmountForFund(String fundId) {
        return investedAmounts.getOrDefault(fundId, 0.0);
    }

    // ── Getters ────────────────────────────────────────────────────────────────
    public String                   getPortfolioId()     { return portfolioId;     }
    public String                   getInvestorId()      { return investorId;      }
    public HashMap<String, Double>  getHoldings()        { return holdings;        }
    public TreeMap<String, Double>  getInvestedAmounts() { return investedAmounts; }
    public ArrayList<Transaction>   getTransactions()    { return transactions;    }
    public LinkedList<Redemption>   getRedemptions()     { return redemptions;     }

    // ── Setters ────────────────────────────────────────────────────────────────
    public void setPortfolioId(String portfolioId)                     { this.portfolioId     = portfolioId;     }
    public void setInvestorId(String investorId)                       { this.investorId      = investorId;      }
    public void setHoldings(HashMap<String, Double> holdings)          { this.holdings        = holdings;        }
    public void setInvestedAmounts(TreeMap<String, Double> invested)   { this.investedAmounts = invested;        }
    public void setTransactions(ArrayList<Transaction> transactions)   { this.transactions    = transactions;    }
    public void setRedemptions(LinkedList<Redemption> redemptions)     { this.redemptions     = redemptions;     }

    @Override
    public String toString() {
        return "Portfolio[" + portfolioId + "] | Investor: " + investorId
                + " | Funds held: " + holdings.size()
                + " | Transactions: " + transactions.size()
                + " | Redemptions: " + redemptions.size();
    }
}
