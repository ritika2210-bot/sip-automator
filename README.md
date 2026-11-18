# SIP Automater

Coordinates recurring monthly investor SIPs, NAV unit allocations, portfolio rebalancing reports, and capital gain tax calculations.

## Features

- **Automated Recurring SIP Execution**: Coordinates monthly investor systematic investment plans.
- **NAV Unit Allocations**: Calculates and records unit allocations based on prevailing Net Asset Value.
- **Portfolio Rebalancing Reports**: Generates automated rebalancing summaries and portfolio insights.
- **Capital Gain Tax Calculations**: Computes short-term (STCG) and long-term (LTCG) tax implications on redemption.

## Getting Started
SIP Investment Manager — Full Java Project Plan (58 Classes)
🧾 What This Project Is

SIP Investment Manager is a console-based Java application that acts as a personal mutual fund investment tracker for retail investors. It simulates what a basic investment platform (like Zerodha Coin or Groww) does in the backend — but offline, using files for storage.

📖 Detailed Project Description
What Problem It Solves

Retail investors in India invest monthly through SIPs (Systematic Investment Plans) into mutual funds. They often struggle to:

Track how many units they own in each fund
Know the current value of their portfolio
Understand when to rebalance (e.g., if equity grew too much)
Calculate how much tax they owe on gains when they sell

This app solves all 4 problems in one place.

What the App Does — Feature by Feature
1️⃣ Investor Management
Register new investors with personal details, bank account, nominee, and risk profile
Each investor has a unique ID and their own portfolio
Investors can be searched, updated, or deactivated
2️⃣ Fund Management
Maintain a catalogue of mutual funds (Equity, Debt, Hybrid, Liquid)
Each fund has a current NAV, category, risk level, and fund manager details
NAV history is recorded so you can see how the fund price changed over time
3️⃣ SIP Coordination (Monthly)
Investors set up SIPs: "Invest ₹5,000 every month into HDFC Top 100"
The app can simulate running all active SIPs for a given month
Each SIP execution creates a Transaction (units bought = amount ÷ NAV)
SIPs can be paused, resumed, or cancelled
4️⃣ NAV Unit Allocation
When a SIP runs, units are calculated: units = monthlyAmount ÷ currentNAV
Example: ₹5,000 ÷ NAV ₹200 = 25 units bought
These units are added to the investor's Portfolio
Current value = total units × current NAV
5️⃣ Portfolio Tracking
Shows all funds the investor holds, units per fund, invested amount, current value, and profit/loss
Tracks overall portfolio growth percentage
Separates holdings by category (equity/debt/hybrid)
6️⃣ Rebalancing Reports
Investor sets a target allocation: e.g., 60% Equity, 40% Debt
App calculates current actual allocation %
Generates a report: which funds to buy more of, which to sell
7️⃣ Capital Gain Tax Calculation
When investor sells (redeems) units, the app calculates gain per transaction
Classifies gain as STCG (held < 1 year) or LTCG (held ≥ 1 year)
Applies correct tax: Equity STCG = 20%, LTCG = 12.5% above ₹1.25L
8️⃣ Redemption (Selling Units)
Investor can redeem some or all units of a fund
App records the redemption transaction and triggers tax computation
9️⃣ File Persistence
All data saved to .csv / .txt files
App loads data from files on startup
🔟 Reports & Export
Portfolio, tax, rebalancing, SIP, and transaction reports
Export to .txt or .csv
🗂️ Collections Used — Master Reference
Collection	Used In	Why This Collection
ArrayList	Transaction history, dividend records	Ordered, allows duplicates, index access
LinkedList	SIP execution queue	Efficient add/remove from front (Queue behavior)
HashMap	Portfolio holdings (fundId → units)	Fast O(1) lookup by fund ID
TreeMap	NAV history (date → NAV), Fund catalogue	Auto-sorted by key (date/fundId)
LinkedHashMap	Rebalancing target allocation	Maintains insertion order while mapping
HashSet	Unique investor IDs, unique fund categories	Fast lookup, no duplicates
TreeSet	Sorted transaction dates, sorted gain records	Auto-sorted, no duplicates
LinkedHashSet	Ordered unique set of active fund IDs	No duplicates + insertion order
PriorityQueue	SIP schedule (sorted by execution date)	Auto-processes earliest-date SIP first
ArrayDeque	Recent transactions buffer (last 10)	Efficient double-ended queue
Stack	Undo last SIP / undo last action	LIFO — last action first to undo
📦 PACKAGE 1: model — 20 Classes
1. Investor.java

Purpose: Represents a person who invests. Collections used: None (pure data)

Field	Type	Description
investorId	String	Unique ID: "INV001"
name	String	Full name
email	String	Email address
phone	String	Phone number
address	Address	Linked address object
bankAccount	BankAccount	Linked bank details
nominee	Nominee	Linked nominee person
riskProfile	RiskProfile	LOW / MEDIUM / HIGH
portfolio	Portfolio	Their fund holdings
isActive	boolean	Active or deactivated
registrationDate	String	When they joined
2. InvestorProfile.java

Purpose: Detailed personal/financial/KYC info of an investor.

Field	Type	Description
profileId	String	Linked to investor
panNumber	String	PAN card for tax
dateOfBirth	String	For age calculation
occupation	String	"Salaried" / "Business"
annualIncome	double	For risk assessment
investmentGoal	String	"Retirement" / "Education"
investmentHorizon	int	Years they plan to invest
3. Address.java

Purpose: Stores physical address details.

Field	Type	Description
street	String	House/flat, street
city	String	City name
state	String	State name
pincode	String	PIN code
country	String	Default "India"
4. BankAccount.java

Purpose: Investor's bank account for SIP debit.

Field	Type	Description
accountNumber	String	Account number
bankName	String	"HDFC Bank"
ifscCode	String	Branch IFSC
accountType	String	"Savings" / "Current"
isVerified	boolean	KYC verified or not
5. Nominee.java

Purpose: Person who inherits investments.

Field	Type	Description
nomineeName	String	Full name
relationship	String	"Spouse" / "Child"
dateOfBirth	String	DOB
sharePercentage	double	% of portfolio
6. Fund.java

Purpose: Represents one mutual fund scheme.

Field	Type	Description
fundId	String	"F001"
fundName	String	"HDFC Top 100 Fund"
fundHouse	String	"HDFC AMC"
fundType	FundType	EQUITY / DEBT / HYBRID / LIQUID
riskLevel	RiskLevel	LOW / MEDIUM / HIGH
currentNAV	double	Current price per unit
fundManager	FundManager	Who manages it
inceptionDate	String	When fund started
expenseRatio	double	Annual fee %
isActive	boolean	Available to invest or not
7. FundManager.java

Purpose: Details of the person managing the fund.

Field	Type	Description
managerId	String	Unique ID
name	String	Manager's name
experience	int	Years of experience
qualification	String	"MBA Finance"
fundsManaged	int	How many funds they handle
8. NAVHistory.java

Purpose: One historical NAV record (one per day per fund).

Field	Type	Description
navHistoryId	String	Unique ID
fundId	String	Which fund
nav	double	NAV on that date
date	String	"2024-01-15"
changePercent	double	% change from previous day
9. SIP.java

Purpose: A recurring monthly investment plan.

Field	Type	Description
sipId	String	"SIP001"
investor	Investor	Who is investing
fund	Fund	Which fund
monthlyAmount	double	₹5,000
startDate	String	"2024-01-01"
endDate	String	Optional end date
sipStatus	SIPStatus	ACTIVE / PAUSED / CANCELLED
executionDay	int	Day of month (1-28)
totalInstallments	int	How many months run so far
totalInvested	double	Running total invested
10. SIPSchedule.java

Purpose: Monthly execution schedule entry for one SIP.

Field	Type	Description
scheduleId	String	Unique
sipId	String	Linked SIP
scheduledDate	String	"2024-02-01"
isExecuted	boolean	Ran or not
executedDate	String	Actual run date

Collection context: SIPSchedule objects stored in PriorityQueue in SIPScheduleService — sorted by scheduledDate so earliest runs first.

11. Transaction.java

Purpose: One investment event — units bought on a date.

Field	Type	Description
transactionId	String	Unique
investorId	String	Who invested
fundId	String	Which fund
transactionType	TransactionType	BUY / SELL / DIVIDEND
amount	double	₹5,000
navAtTransaction	double	NAV on that day
units	double	amount ÷ nav
date	String	"2024-01-15"
sipId	String	Which SIP triggered this
12. Redemption.java

Purpose: When an investor sells units.

Field	Type	Description
redemptionId	String	Unique
investorId	String	Who is selling
fundId	String	Which fund
unitsRedeemed	double	Units being sold
navAtRedemption	double	NAV when sold
redemptionAmount	double	unitsRedeemed × nav
redemptionDate	String	Date
capitalGain	CapitalGain	Tax object created
13. Portfolio.java

Purpose: All fund holdings of one investor. Collections used:

HashMap<String, Double> holdings → fundId to units (fast lookup)
TreeMap<String, Double> investedAmounts → fundId to ₹ invested (sorted)
ArrayList<Transaction> transactions → full ordered buy history
LinkedList<Redemption> redemptions → sell history (queue-like processing)
Field	Type	Description
portfolioId	String	Unique
investorId	String	Whose portfolio
holdings	HashMap<String, Double>	fundId → units held
investedAmounts	TreeMap<String, Double>	fundId → ₹ invested (sorted)
transactions	ArrayList<Transaction>	Full buy history
redemptions	LinkedList<Redemption>	All sell records
14. PortfolioSummary.java

Purpose: A snapshot of portfolio value at a point in time.

Field	Type	Description
summaryId	String	Unique
investorId	String	Whose
totalInvested	double	Total ₹ put in
currentValue	double	Current ₹ value
totalGain	double	currentValue - totalInvested
gainPercent	double	Gain as %
generatedDate	String	When this was calculated
15. CapitalGain.java

Purpose: Tax gain/loss record for one redemption.

Field	Type	Description
gainId	String	Unique
transactionId	String	The buy transaction
redemptionId	String	The sell redemption
purchaseNAV	double	NAV when bought
redemptionNAV	double	NAV when sold
units	double	Units sold
gainAmount	double	Profit or loss
gainType	GainType	STCG or LTCG
holdingPeriodDays	int	Days held
16. TaxReport.java

Purpose: Full annual tax summary for an investor. Collections used:

TreeSet<CapitalGain> → gains sorted by date automatically
Field	Type	Description
reportId	String	Unique
investorId	String	Whose
financialYear	String	"2024-25"
totalSTCG	double	Total short-term gains
totalLTCG	double	Total long-term gains
stcgTax	double	Tax on STCG
ltcgTax	double	Tax on LTCG
totalTaxPayable	double	Sum
gains	TreeSet<CapitalGain>	All gains, sorted by date
17. RebalancingReport.java

Purpose: Report showing current vs target allocation. Collections used:

LinkedHashMap<String, Double> → maintains order of fund entries in display
Field	Type	Description
reportId	String	Unique
investorId	String	Whose
generatedDate	String	Date
totalPortfolioValue	double	Current total
targetAllocation	LinkedHashMap<String, Double>	fundId → target % (ordered)
entries	ArrayList<RebalancingEntry>	Per-fund suggestions
18. RebalancingEntry.java

Purpose: One line in a rebalancing report (one fund's action).

Field	Type	Description
fundId	String	Which fund
fundName	String	Name
currentUnits	double	Units held
currentValue	double	Current ₹ value
currentPercent	double	% of portfolio
targetPercent	double	Target %
action	RebalanceAction	BUY / SELL / HOLD
actionAmount	double	₹ to buy or sell
19. DividendRecord.java

Purpose: When a fund pays dividend to investors. Collections used:

HashSet<String> → set of investorIds who received this dividend (no duplicates)
Field	Type	Description
dividendId	String	Unique
fundId	String	Which fund
dividendPerUnit	double	₹ per unit
payoutDate	String	Date
eligibleInvestors	HashSet<String>	Investor IDs who received
totalPaidOut	double	Total ₹ distributed
20. RiskProfile.java

Purpose: Investor's risk tolerance assessment.

Field	Type	Description
profileId	String	Unique
riskLevel	RiskLevel	LOW / MEDIUM / HIGH
equityAllocation	int	Suggested equity %
debtAllocation	int	Suggested debt %
assessmentDate	String	When assessed
score	int	Risk score out of 100
📦 PACKAGE 2: enums — 7 Classes
21. FundType.java
java
public enum FundType { EQUITY, DEBT, HYBRID, LIQUID }
22. GainType.java
java
public enum GainType { STCG, LTCG }
23. RiskLevel.java
java
public enum RiskLevel { LOW, MEDIUM, HIGH }
24. TransactionType.java
java
public enum TransactionType { BUY, SELL, DIVIDEND }
25. SIPStatus.java
java
public enum SIPStatus { ACTIVE, PAUSED, CANCELLED, COMPLETED }
26. RebalanceAction.java
java
public enum RebalanceAction { BUY, SELL, HOLD }
27. TaxCategory.java
java
public enum TaxCategory { EQUITY_STCG, EQUITY_LTCG, DEBT_STCG, DEBT_LTCG }
📦 PACKAGE 3: service — 12 Classes
28. InvestorService.java

Purpose: All operations on investors.

Method	Does what
registerInvestor(...)	Creates a new Investor
getInvestorById(id)	Finds and returns investor
updateInvestorDetails(...)	Updates fields
deactivateInvestor(id)	Sets isActive = false
getAllInvestors()	Returns all investors
assessRisk(investorId)	Runs quiz, sets RiskProfile
29. FundService.java

Purpose: All operations on mutual funds.

Method	Does what
addFund(...)	Adds new fund to catalogue
getFundById(id)	Returns fund object
getAllFunds()	Lists all funds
getFundsByType(type)	Filter by EQUITY / DEBT etc.
deactivateFund(id)	Marks fund unavailable
searchFundByName(name)	Search by keyword
30. NAVService.java

Purpose: NAV updates and unit calculations. Collections used: TreeMap<String, Double> → date-sorted NAV history

Method	Does what
updateNAV(fundId, newNAV)	Updates NAV, records history
calculateUnits(amount, nav)	Returns amount / nav
calculateCurrentValue(units, nav)	Returns units × nav
getNAVHistory(fundId)	Returns TreeMap of past NAVs
getNAVOnDate(fundId, date)	Returns NAV for specific date
31. SIPService.java

Purpose: Create, manage, and execute SIPs. Collections used: LinkedList<SIP> as queue — SIPs dequeued and processed one by one

Method	Does what
createSIP(investorId, fundId, amount, day)	Creates SIP
executeSIP(sipId)	Runs one installment
executeAllActiveSIPs()	Processes entire LinkedList queue
pauseSIP(sipId)	Pauses a SIP
resumeSIP(sipId)	Resumes paused SIP
cancelSIP(sipId)	Permanently cancels
getSIPsByInvestor(investorId)	Lists investor's SIPs
32. TransactionService.java

Purpose: Record and retrieve transactions. Collections used:

ArrayList<Transaction> → main history list
ArrayDeque<Transaction> → last 10 transactions buffer (recent activity)
Method	Does what
recordBuy(...)	Creates BUY transaction, pushes to deque
getTransactionsByInvestor(id)	Returns ArrayList
getRecentTransactions()	Returns ArrayDeque (last 10)
getTransactionsBetweenDates(from, to)	Filtered list
33. PortfolioService.java

Purpose: View and compute portfolio values. Collections used: LinkedHashSet<String> → ordered set of unique fund IDs the investor holds

Method	Does what
getPortfolio(investorId)	Returns portfolio
addUnits(investorId, fundId, units)	Adds units to HashMap
removeUnits(investorId, fundId, units)	Removes units
getActiveFundIds(investorId)	Returns LinkedHashSet of fund IDs
getTotalInvested(investorId)	Sum of all amounts
getCurrentValue(investorId)	Sum of all current values
generateSummary(investorId)	Returns PortfolioSummary
34. RedemptionService.java

Purpose: Handle selling (redeeming) of units.

Method	Does what
redeemUnits(investorId, fundId, units)	Sells units
redeemAll(investorId, fundId)	Sells all units
getRedemptionHistory(investorId)	Past redemptions
calculateRedemptionAmount(units, nav)	Returns ₹ value
35. TaxService.java

Purpose: Calculate capital gain taxes. Collections used: TreeSet<CapitalGain> → gains sorted by date for year-wise report

Method	Does what
calculateGain(buyTxn, redemption)	Profit calculation
classifyGain(holdingDays, fundType)	STCG or LTCG
calculateTax(gainAmount, taxCategory)	Applies correct %
generateTaxReport(investorId, year)	Full year summary
getExemptionLimit(gainType)	Returns ₹1.25L for LTCG
36. RebalancingService.java

Purpose: Compare current vs target portfolio allocation. Collections used: LinkedHashMap<String, Double> → target allocation (ordered display)

Method	Does what
setTargetAllocation(investorId, map)	Sets target % per fund
getCurrentAllocation(investorId)	Returns actual % breakdown
generateReport(investorId)	Returns RebalancingReport
calculateDeviation(current, target)	% difference
suggestActions(report)	BUY / SELL / HOLD per fund
37. DividendService.java

Purpose: Handle dividend payouts. Collections used: HashSet<String> → unique investors eligible for dividend

Method	Does what
declareDividend(fundId, perUnit)	Fund declares dividend
distributeToInvestors(fundId)	Calculates each investor's share
getDividendHistory(investorId)	Past dividends received
getTotalDividendReceived(investorId)	Sum
38. ReportService.java

Purpose: Generate formatted text reports.

Method	Does what
printPortfolioReport(investorId)	Portfolio table
printTaxReport(investorId, year)	Tax summary
printRebalancingReport(investorId)	Suggestions
printSIPReport(investorId)	All SIPs
printTransactionHistory(investorId)	All buy/sell records
exportToFile(text, filename)	Saves to .txt
39. SIPScheduleService.java

Purpose: Manage SIP monthly execution calendar. Collections used: PriorityQueue<SIPSchedule> → auto-sorted by scheduledDate, earliest first

Method	Does what
scheduleSIP(sipId, date)	Adds to PriorityQueue
processNextDue()	Polls from PriorityQueue, executes
getPendingForDate(date)	Returns due SIPs
markExecuted(scheduleId)	Marks as done
getMissedSIPs()	Finds unexecuted past schedules
40. UndoService.java

Purpose: Undo the last performed action (like cancelling a wrongly placed SIP). Collections used: Stack<String> → stores action descriptions LIFO — last action undone first

Method	Does what
pushAction(description)	Pushes to Stack
undoLastAction()	Pops from Stack, reverses it
peekLastAction()	Shows what will be undone
getActionHistory()	Shows full Stack
📦 PACKAGE 4: repository — 5 Classes

In-memory data stores. Load from file at startup, save to file at exit.

41. InvestorRepository.java

Collections used: HashMap<String, Investor> → investorId to Investor (O(1) lookup)

Method	Does what
save(investor)	Puts into HashMap
findById(id)	HashMap.get(id)
findAll()	Returns all values
delete(id)	Removes from HashMap
loadFromFile()	Populates map from file
saveToFile()	Writes map to file
42. FundRepository.java

Collections used: TreeMap<String, Fund> → fundId to Fund, auto-sorted by fundId

Method	Does what
save(fund)	Puts into TreeMap
findById(id)	TreeMap.get(id)
findByType(type)	Iterates, filters by type
findAll()	Returns sorted fund list
loadFromFile()	Populates TreeMap
saveToFile()	Writes to file
43. TransactionRepository.java

Collections used: ArrayList<Transaction> → ordered insertion history

Method	Does what
save(txn)	Adds to ArrayList
findByInvestor(id)	Filters by investorId
findByFund(id)	Filters by fundId
findBetweenDates(from, to)	Date range filter
loadFromFile()	Populates list
saveToFile()	Writes to file
44. SIPRepository.java

Collections used: LinkedList<SIP> → queue-style processing of SIPs

Method	Does what
save(sip)	Adds to LinkedList
findById(id)	Searches list
findByInvestor(id)	Filters
findActive()	Returns only ACTIVE SIPs
loadFromFile()	Populates list
saveToFile()	Writes to file
45. NAVRepository.java

Collections used: TreeMap<String, TreeMap<String, Double>> → fundId → (date → NAV), both sorted

Method	Does what
saveNAV(fundId, date, nav)	Nested TreeMap insert
getNAVHistory(fundId)	Returns inner TreeMap
getNAVOnDate(fundId, date)	Direct lookup
getLatestNAV(fundId)	Last entry in TreeMap
loadFromFile()	Populates structure
saveToFile()	Writes to file
📦 PACKAGE 5: exception — 6 Classes
46. InvestorNotFoundException.java
java
// Thrown when: investor ID doesn't exist
// Message: "Investor INV999 not found."
47. FundNotFoundException.java
java
// Thrown when: fund ID doesn't exist
// Message: "Fund F999 not found."
48. InsufficientUnitsException.java
java
// Thrown when: sell more units than held
// Message: "Cannot redeem 100 units. Only 42.5 available."
49. InvalidNAVException.java
java
// Thrown when: NAV is 0 or negative
// Message: "NAV must be greater than zero."
50. SIPAlreadyActiveException.java
java
// Thrown when: creating duplicate SIP for same fund
// Message: "Active SIP already exists for fund F001."
51. InvalidDateException.java
java
// Thrown when: bad date format or end < start
// Message: "Invalid date. Use YYYY-MM-DD format."
📦 PACKAGE 6: util — 7 Classes
52. DateUtil.java
Method	Does what
today()	Returns "YYYY-MM-DD"
daysBetween(d1, d2)	Days apart
monthsBetween(d1, d2)	Months apart
isMoreThanOneYear(date)	true/false
formatDate(date)	"15 Jan 2024"
getFinancialYear(date)	"2024-25"
53. MathUtil.java
Method	Does what
calculateUnits(amount, nav)	amount ÷ nav
calculateValue(units, nav)	units × nav
calculateGainPercent(invested, current)	% gain
roundToTwo(value)	2 decimal places
calculateCAGR(invested, current, years)	Compound growth rate
54. ValidationUtil.java
Method	Does what
isValidEmail(email)	Format check
isValidPhone(phone)	10 digits
isValidPAN(pan)	ABCDE1234F format
isValidAmount(amount)	Must be > 0
isValidDate(date)	YYYY-MM-DD check
55. FileHandler.java
Method	Does what
writeToFile(filename, data)	Full overwrite
readFromFile(filename)	Returns list of lines
appendToFile(filename, line)	Adds one line
fileExists(filename)	Checks existence
56. CSVExporter.java
Method	Does what
exportTransactions(list, filename)	Transactions → CSV
exportPortfolio(summary, filename)	Portfolio → CSV
exportTaxReport(report, filename)	Tax data → CSV
formatRow(fields)	Comma-joins fields
57. ReportFormatter.java
Method	Does what
printTable(headers, rows)	Aligned console table
printSeparator()	"----------" line
printHeader(title)	Boxed title
formatCurrency(amount)	"₹1,25,000.00"
formatPercent(value)	"+12.5%"
58. IDGenerator.java
Method	Does what
generateInvestorId()	"INV001", "INV002"...
generateFundId()	"F001", "F002"...
generateSIPId()	"SIP001"...
generateTransactionId()	"TXN" + timestamp
generateRedemptionId()	"RED001"...
📦 PACKAGE 7: main — 1 Class
59. Main.java

Console menu entry point that calls all services.

========== SIP Investment Manager ==========
1. Investor Management
2. Fund Management
3. SIP Operations
4. Portfolio View
5. Rebalancing Report
6. Redemption (Sell Units)
7. Capital Gain Tax Report
8. Dividend Management
9. Undo Last Action
10. Save & Export
11. Exit
✅ Java Collections Summary (All 11 Used)
Collection	Class Where Used	Why
ArrayList	TransactionRepository, Portfolio	Ordered, indexed history
LinkedList	SIPRepository, SIPService	Queue-style SIP processing
HashMap	InvestorRepository, Portfolio.holdings	Fast ID-based lookup
TreeMap	FundRepository, NAVRepository, Portfolio.investedAmounts	Auto-sorted by key
LinkedHashMap	RebalancingReport, RebalancingService	Ordered map for display
HashSet	DividendRecord.eligibleInvestors	No duplicate investor IDs
TreeSet	TaxReport.gains	Gains sorted by date
LinkedHashSet	PortfolioService.getActiveFundIds	Ordered, unique fund IDs
PriorityQueue	SIPScheduleService	Earliest SIP date processed first
ArrayDeque	TransactionService.recentTransactions	Last 10 transactions buffer
Stack	UndoService	LIFO — undo last action
✅ Java Concepts Coverage
Concept	Where
Classes & Objects	All 20 model classes
Encapsulation	Private fields + getters/setters
Enums	7 enum classes
11 Collection Types	Across service, model, repository classes
Custom Exceptions	6 exception classes
try-catch	All service methods
File I/O	FileHandler, CSVExporter
Static Methods	IDGenerator, MathUtil, DateUtil
Comparable	CapitalGain implements Comparable (for TreeSet)
Scanner	Main.java input
📅 7-Day Plan
Day	Task
1	7 enum classes + model 1–10 (Investor through SIPSchedule)
2	Model 11–20 (Transaction through RiskProfile) + all 5 repositories
3	InvestorService, FundService, NAVService
4	SIPService, TransactionService, PortfolioService + FileHandler
5	RedemptionService, TaxService, RebalancingService
6	DividendService, UndoService, ReportService, CSVExporter, Exceptions
7	Main.java full menu + integration testing + README

TIP

CapitalGain must implement Comparable<CapitalGain> (compare by date) so it works inside TreeSet. This demonstrates Comparable — good for extra marks.

IMPORTANT

FileHandler on Day 4 is non-negotiable — file persistence is explicitly in the grading rubric.
