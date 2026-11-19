package service;

import exception.InvestorNotFoundException;
import model.*;
import util.DateUtil;
import util.IDGenerator;
import util.ValidationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Service for all investor-related operations.
 * Acts as the bridge between Main menu and InvestorRepository.
 */
public class InvestorService {

    // In-memory store: investorId → Investor
    private final HashMap<String, Investor> investorMap = new HashMap<>();

    /**
     * Registers a new investor and assigns a unique ID.
     */
    public Investor registerInvestor(String name, String email, String phone,
                                     Address address, BankAccount bankAccount,
                                     Nominee nominee) {
        if (!ValidationUtil.isValidEmail(email))
            throw new IllegalArgumentException("Invalid email: " + email);
        if (!ValidationUtil.isValidPhone(phone))
            throw new IllegalArgumentException("Invalid phone: " + phone);

        String id       = IDGenerator.generateInvestorId();
        String today    = DateUtil.today();
        Investor investor = new Investor(id, name, email, phone, address, bankAccount, nominee, today);
        investorMap.put(id, investor);
        System.out.println("[InvestorService] Registered: " + investor);
        return investor;
    }

    /**
     * Retrieves an investor by ID.
     * @throws InvestorNotFoundException if ID doesn't exist
     */
    public Investor getInvestorById(String investorId) {
        Investor inv = investorMap.get(investorId);
        if (inv == null) throw new InvestorNotFoundException(investorId);
        return inv;
    }

    /** Updates an investor's contact info. */
    public void updateInvestorDetails(String investorId, String newEmail, String newPhone) {
        Investor inv = getInvestorById(investorId);
        if (newEmail != null && ValidationUtil.isValidEmail(newEmail)) inv.setEmail(newEmail);
        if (newPhone != null && ValidationUtil.isValidPhone(newPhone)) inv.setPhone(newPhone);
        System.out.println("[InvestorService] Updated investor: " + investorId);
    }

    /** Soft-deactivates an investor (does not delete data). */
    public void deactivateInvestor(String investorId) {
        getInvestorById(investorId).setActive(false);
        System.out.println("[InvestorService] Deactivated: " + investorId);
    }

    /** Returns all registered investors. */
    public List<Investor> getAllInvestors() {
        return new ArrayList<>(investorMap.values());
    }

    /** Returns only active investors. */
    public List<Investor> getActiveInvestors() {
        return investorMap.values().stream()
                .filter(Investor::isActive)
                .toList();
    }

    /**
     * Basic risk assessment based on investment horizon.
     * Sets a RiskProfile on the investor.
     */
    public RiskProfile assessRisk(String investorId, int horizonYears, double annualIncome) {
        Investor inv = getInvestorById(investorId);
        enums.RiskLevel level;
        int equity, debt, score;

        if (horizonYears >= 10 && annualIncome >= 1000000) {
            level  = enums.RiskLevel.HIGH;   equity = 80; debt = 20; score = 80;
        } else if (horizonYears >= 5) {
            level  = enums.RiskLevel.MEDIUM; equity = 60; debt = 40; score = 55;
        } else {
            level  = enums.RiskLevel.LOW;    equity = 30; debt = 70; score = 25;
        }

        RiskProfile profile = new RiskProfile(investorId + "_RP", level,
                equity, debt, DateUtil.today(), score);
        inv.setRiskProfile(profile);
        System.out.println("[InvestorService] Risk assessed for " + investorId + ": " + profile);
        return profile;
    }

    /** Count total investors. */
    public int getTotalCount() { return investorMap.size(); }
}
