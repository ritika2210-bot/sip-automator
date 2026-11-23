# 💹 SIP Automater

> Coordinates recurring monthly investor SIPs, NAV unit allocations, portfolio rebalancing reports, and capital gain tax calculations.

[![Java](https://img.shields.io/badge/Java-17+-orange?logo=java)](https://www.oracle.com/java/)
[![GitHub](https://img.shields.io/badge/GitHub-ritika2210--bot-blue?logo=github)](https://github.com/ritika2210-bot/sip-automater)

---

## 📅 5-Day Build Plan

| Day | Focus | What Gets Built |
|-----|-------|-----------------|
| ✅ **Day 1** | **Foundation** | 7 Enums + 7 Model classes (Investor, Fund, Address, BankAccount, Nominee, RiskProfile, FundManager) |
| 🔲 **Day 2** | **More Models + Data** | SIP, Transaction, Portfolio, NAVHistory, CapitalGain, Redemption + sample data setup |
| 🔲 **Day 3** | **Core Services** | InvestorService, FundService, SIPService, TransactionService — business logic |
| 🔲 **Day 4** | **Advanced Features** | RedemptionService, TaxService, RebalancingService + File read/write |
| 🔲 **Day 5** | **Console App + Reports** | Main menu, full integration, report printing, final testing |

---

## 📁 Current Structure (Day 1 Complete)

```
sip-automater/
├── src/
│   ├── enums/           ← 7 enums (Day 1 ✅)
│   │   ├── FundType.java
│   │   ├── GainType.java
│   │   ├── RiskLevel.java
│   │   ├── TransactionType.java
│   │   ├── SIPStatus.java
│   │   ├── RebalanceAction.java
│   │   └── TaxCategory.java
│   │
│   └── model/           ← 7 model classes (Day 1 ✅)
│       ├── Investor.java
│       ├── Address.java
│       ├── BankAccount.java
│       ├── Nominee.java
│       ├── RiskProfile.java
│       ├── Fund.java
│       └── FundManager.java
└── data/                ← persistence files (Day 4)
```

---

## 📘 Day 1 — What Was Built

### Enums (7)

| Enum | Values | Purpose |
|------|--------|---------|
| `FundType` | EQUITY, DEBT, HYBRID, LIQUID | Type of mutual fund |
| `GainType` | STCG, LTCG | Capital gain classification |
| `RiskLevel` | LOW, MEDIUM, HIGH | Risk appetite |
| `TransactionType` | BUY, SELL, DIVIDEND | Transaction category |
| `SIPStatus` | ACTIVE, PAUSED, CANCELLED, COMPLETED | SIP lifecycle |
| `RebalanceAction` | BUY, SELL, HOLD | Rebalancing decision |
| `TaxCategory` | EQUITY_STCG, EQUITY_LTCG, DEBT_STCG, DEBT_LTCG | Tax rules |

---

### Model Classes (7)

#### `Investor.java` — The main person who invests
```java
Investor investor = new Investor(
    "INV001", "Ritika", "ritika@email.com", "9876543210",
    address, bankAccount, nominee, "2024-01-01"
);
```

| Field | Type | What it stores |
|-------|------|----------------|
| `investorId` | `String` | Unique ID: `INV001` |
| `name` | `String` | Full name |
| `email` | `String` | Email |
| `phone` | `String` | 10-digit number |
| `address` | `Address` | Home address |
| `bankAccount` | `BankAccount` | Bank for SIP debit |
| `nominee` | `Nominee` | Beneficiary |
| `riskProfile` | `RiskProfile` | LOW/MEDIUM/HIGH risk |
| `isActive` | `boolean` | Active or not |
| `registrationDate` | `String` | Date joined |

---

#### `Fund.java` — A mutual fund scheme
```java
Fund fund = new Fund(
    "F001", "HDFC Top 100", "HDFC AMC",
    FundType.EQUITY, RiskLevel.HIGH,
    985.75, 1.05, "2000-01-01"
);
```

| Field | Type | What it stores |
|-------|------|----------------|
| `fundId` | `String` | Unique ID: `F001` |
| `fundName` | `String` | Fund name |
| `fundHouse` | `String` | AMC name |
| `fundType` | `FundType` | EQUITY/DEBT/HYBRID/LIQUID |
| `riskLevel` | `RiskLevel` | LOW/MEDIUM/HIGH |
| `currentNAV` | `double` | Current price per unit |
| `expenseRatio` | `double` | Annual fee % |
| `fundManager` | `FundManager` | Who manages it |

---

#### Other Day 1 Classes

| Class | Purpose |
|-------|---------|
| `Address` | Street, city, state, pincode |
| `BankAccount` | A/C number, bank name, IFSC |
| `Nominee` | Beneficiary name, relation, % share |
| `RiskProfile` | Risk level, equity %, debt %, score |
| `FundManager` | Manager name, experience, qualification |

---

## 🚀 How to Compile & Test (Day 1)

```bash
# Compile
javac -d out src/enums/*.java src/model/*.java

# No main class yet — Day 5 will add the menu
# For now, test by writing a simple TestDay1.java
```

---

## 🧮 Tax Reference (for Day 4)

| Type | Tax Rate | Condition |
|------|----------|-----------|
| Equity STCG | 20% | Held < 1 year |
| Equity LTCG | 12.5% above ₹1.25L | Held ≥ 1 year |
| Debt STCG | Income slab | Held < 3 years |
| Debt LTCG | 20% with indexation | Held ≥ 3 years |

---

## 👩‍💻 Author

**GitHub**: [@ritika2210-bot](https://github.com/ritika2210-bot)
**Email**: ritika.46452210@gmail.com
