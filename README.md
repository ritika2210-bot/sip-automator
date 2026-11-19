# 💹 SIP Automater

> Coordinates recurring monthly investor SIPs, NAV unit allocations, portfolio rebalancing reports, and capital gain tax calculations.

[![Java](https://img.shields.io/badge/Java-17+-orange?logo=java)](https://www.oracle.com/java/)
[![GitHub](https://img.shields.io/badge/GitHub-ritika2210--bot-blue?logo=github)](https://github.com/ritika2210-bot/sip-automater)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

---

## 📖 What Is This?

**SIP Automater** is a console-based Java application that acts as a personal mutual fund investment tracker for retail investors. It simulates what a basic investment platform (like Zerodha Coin or Groww) does in the backend — but offline, using in-memory collections and flat-file persistence.

---

## ✨ Features

| # | Feature | Description |
|---|---------|-------------|
| 1 | **Investor Management** | Register, update, deactivate investors with KYC, bank, and nominee details |
| 2 | **Fund Catalogue** | Manage Equity, Debt, Hybrid & Liquid mutual funds with NAV history |
| 3 | **SIP Coordination** | Create, pause, resume, cancel recurring monthly SIPs |
| 4 | **NAV Unit Allocation** | Auto-calculate units bought: `units = amount ÷ NAV` |
| 5 | **Portfolio Tracking** | Live P&L, invested amount, current value per fund |
| 6 | **Rebalancing Reports** | BUY / SELL / HOLD suggestions based on target vs. actual allocation |
| 7 | **Capital Gain Tax** | STCG (20%) and LTCG (12.5% above ₹1.25L) calculations |
| 8 | **Redemption (Sell)** | Redeem some or all units; triggers tax computation automatically |
| 9 | **File Persistence** | Data saved to `/data` directory; loaded on startup |
| 10 | **Transaction History** | Full ordered history + a last-10 recent buffer |

---

## 🗂️ Project Structure

```
sip-automater/
├── src/
│   ├── main/
│   │   └── Main.java               ← Console entry point & full menu
│   ├── model/                      ← 20 entity classes
│   │   ├── Investor.java
│   │   ├── InvestorProfile.java
│   │   ├── Address.java
│   │   ├── BankAccount.java
│   │   ├── Nominee.java
│   │   ├── RiskProfile.java
│   │   ├── Fund.java
│   │   ├── FundManager.java
│   │   ├── NAVHistory.java
│   │   ├── SIP.java
│   │   ├── SIPSchedule.java
│   │   ├── Transaction.java
│   │   ├── Redemption.java
│   │   ├── Portfolio.java
│   │   ├── PortfolioSummary.java
│   │   ├── CapitalGain.java
│   │   ├── TaxReport.java
│   │   ├── RebalancingReport.java
│   │   ├── RebalancingEntry.java
│   │   └── DividendRecord.java
│   ├── enums/                      ← 7 enum types
│   │   ├── FundType.java           (EQUITY / DEBT / HYBRID / LIQUID)
│   │   ├── GainType.java           (STCG / LTCG)
│   │   ├── RiskLevel.java          (LOW / MEDIUM / HIGH)
│   │   ├── TransactionType.java    (BUY / SELL / DIVIDEND)
│   │   ├── SIPStatus.java          (ACTIVE / PAUSED / CANCELLED / COMPLETED)
│   │   ├── RebalanceAction.java    (BUY / SELL / HOLD)
│   │   └── TaxCategory.java
│   ├── service/                    ← Business logic layer
│   │   ├── InvestorService.java
│   │   ├── FundService.java
│   │   ├── SIPService.java
│   │   ├── TransactionService.java
│   │   ├── RedemptionService.java
│   │   ├── TaxService.java
│   │   └── RebalancingService.java
│   ├── exception/                  ← 6 custom exceptions
│   │   ├── InvestorNotFoundException.java
│   │   ├── FundNotFoundException.java
│   │   ├── InsufficientUnitsException.java
│   │   ├── InvalidNAVException.java
│   │   ├── SIPAlreadyActiveException.java
│   │   └── InvalidDateException.java
│   └── util/                       ← Helper utilities
│       ├── DateUtil.java
│       ├── MathUtil.java
│       ├── ValidationUtil.java
│       ├── IDGenerator.java
│       ├── FileHandler.java
│       └── ReportFormatter.java
└── data/                           ← CSV/TXT persistence files
```

---

## 🧩 Key Classes — Investor Domain

### `Investor.java`
The core entity. Holds personal info, bank account, nominee, risk profile, and their portfolio.

```java
Investor investor = new Investor("INV001", "Ritika", "ritika@email.com",
        "9876543210", address, bankAccount, nominee, "2024-01-01");
```

| Field | Type | Description |
|-------|------|-------------|
| `investorId` | `String` | Unique ID: `INV001` |
| `name` | `String` | Full name |
| `email` | `String` | Email address |
| `phone` | `String` | 10-digit mobile |
| `address` | `Address` | Linked address object |
| `bankAccount` | `BankAccount` | For SIP auto-debit |
| `nominee` | `Nominee` | Beneficiary details |
| `riskProfile` | `RiskProfile` | LOW / MEDIUM / HIGH |
| `portfolio` | `Portfolio` | All fund holdings |
| `isActive` | `boolean` | Active or deactivated |
| `registrationDate` | `String` | `yyyy-MM-dd` |

---

### `Portfolio.java`
All fund holdings of one investor. Uses **4 different Java collections**:

```java
Portfolio portfolio = investor.getPortfolio();
portfolio.addUnits("F001", 25.0, 5000.0);  // Add 25 units, ₹5000 invested
```

| Collection | Field | Why |
|-----------|-------|-----|
| `HashMap<String, Double>` | `holdings` | Fast O(1) lookup by fundId → units |
| `TreeMap<String, Double>` | `investedAmounts` | Auto-sorted by fundId → ₹ invested |
| `ArrayList<Transaction>` | `transactions` | Ordered, indexed buy history |
| `LinkedList<Redemption>` | `redemptions` | Queue-like sell history |

---

### `SIP.java`
A recurring monthly investment plan.

```java
SIP sip = sipService.createSIP(investor, fund, 5000.0, 1);  // ₹5000 on day 1
sipService.executeAllActiveSIPs();  // Processes entire queue
```

| Field | Type | Description |
|-------|------|-------------|
| `sipId` | `String` | `SIP001` |
| `investor` | `Investor` | Who is investing |
| `fund` | `Fund` | Which mutual fund |
| `monthlyAmount` | `double` | ₹ per installment |
| `sipStatus` | `SIPStatus` | ACTIVE / PAUSED / CANCELLED |
| `executionDay` | `int` | Day of month (1–28) |
| `totalInstallments` | `int` | Months completed |
| `totalInvested` | `double` | Running ₹ total |

---

### `CapitalGain.java`
Tax record for one redemption. Implements `Comparable<CapitalGain>` for `TreeSet` sorting by date.

```
Gain = (redemptionNAV - purchaseNAV) × units
STCG Tax = gain × 20%          (held < 1 year)
LTCG Tax = (gain − ₹1.25L) × 12.5%  (held ≥ 1 year)
```

---

## 📊 Java Collections Used

| Collection | Used In | Purpose |
|-----------|---------|---------|
| `ArrayList` | `TransactionService`, `Portfolio` | Ordered buy history |
| `LinkedList` | `SIPService`, `Portfolio.redemptions` | Queue-style SIP processing |
| `HashMap` | `InvestorService`, `Portfolio.holdings` | O(1) lookup by ID |
| `TreeMap` | `FundService`, `Portfolio.investedAmounts` | Auto-sorted by key |
| `LinkedHashMap` | `RebalancingReport` | Ordered map for display |
| `HashSet` | `DividendRecord.eligibleInvestors` | Unique investor IDs |
| `TreeSet` | `TaxReport.gains` | Gains sorted by date |
| `LinkedHashSet` | `PortfolioService` | Ordered, unique fund IDs |
| `PriorityQueue` | `SIPScheduleService` | Earliest SIP date first |
| `ArrayDeque` | `TransactionService.recentTransactions` | Last 10 transactions buffer |
| `Stack` | `UndoService` | LIFO — undo last action |

---

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Git

### Setup & Run

```bash
# 1. Clone the repository
git clone https://github.com/ritika2210-bot/sip-automater.git
cd sip-automater

# 2. Compile all source files
javac -d out src/enums/*.java src/model/*.java src/exception/*.java src/util/*.java src/service/*.java src/main/*.java

# 3. Run the application
java -cp out main.Main
```

### Expected Output

```
================================================================================
  SIP AUTOMATER — INVESTMENT MANAGER
================================================================================
  1. Investor Management
  2. Fund Management
  3. SIP Operations
  4. Portfolio View
  5. Rebalancing Report
  6. Redemption (Sell Units)
  7. Capital Gain Tax Report
  8. Transaction History
  0. Exit
--------------------------------------------------------------------------------
  Enter choice:
```

---

## 🧮 Tax Calculation Reference

| Category | Tax Rate | Condition |
|---------|----------|-----------|
| Equity STCG | 20% | Held < 1 year |
| Equity LTCG | 12.5% above ₹1.25L | Held ≥ 1 year |
| Debt STCG | As per income slab | Held < 3 years |
| Debt LTCG | 20% with indexation | Held ≥ 3 years |

> **Note**: Tax rules are based on India's Finance Act 2024-25.

---

## 👩‍💻 Author

| Field | Value |
|-------|-------|
| **GitHub** | [@ritika2210-bot](https://github.com/ritika2210-bot) |
| **Email** | ritika.46452210@gmail.com |

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.
