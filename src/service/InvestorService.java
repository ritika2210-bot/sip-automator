package service;

import data.DataStore;
import model.Investor;
import model.RiskProfile;

import java.util.List;

/**
 * InvestorService — CRUD and business operations on Investor records.
 */
public class InvestorService {

    private DataStore ds;

    public InvestorService(DataStore ds) {
        this.ds = ds;
    }

    // ── Register a new investor ──────────────────────────────────────
    public Investor registerInvestor(Investor investor) {
        // Validate basic fields
        if (investor.getName() == null || investor.getName().isBlank())
            throw new IllegalArgumentException("Investor name cannot be empty.");
        if (investor.getEmail() == null || !investor.getEmail().contains("@"))
            throw new IllegalArgumentException("Invalid email address.");
        if (investor.getPhone() == null || investor.getPhone().length() != 10)
            throw new IllegalArgumentException("Phone must be 10 digits.");
        if (investor.getBankAccount() == null)
            throw new IllegalArgumentException("Bank account is required.");

        // Check duplicate email
        for (Investor inv : ds.getInvestors()) {
            if (inv.getEmail().equalsIgnoreCase(investor.getEmail()))
                throw new IllegalArgumentException("Email already registered: " + investor.getEmail());
        }

        ds.addInvestor(investor);
        System.out.println("✅ Investor registered: " + investor);
        return investor;
    }

    // ── Fetch investor by ID ─────────────────────────────────────────
    public Investor getInvestorById(String investorId) {
        Investor inv = ds.findInvestorById(investorId);
        if (inv == null) throw new IllegalArgumentException("Investor not found: " + investorId);
        return inv;
    }

    // ── List all active investors ────────────────────────────────────
    public List<Investor> getAllActiveInvestors() {
        return ds.getInvestors().stream()
                 .filter(Investor::isActive)
                 .toList();
    }

    // ── Update email ─────────────────────────────────────────────────
    public void updateEmail(String investorId, String newEmail) {
        Investor inv = getInvestorById(investorId);
        if (!newEmail.contains("@")) throw new IllegalArgumentException("Invalid email.");
        inv.setEmail(newEmail);
        System.out.println("✅ Email updated for " + investorId + " → " + newEmail);
    }

    // ── Update phone ─────────────────────────────────────────────────
    public void updatePhone(String investorId, String newPhone) {
        Investor inv = getInvestorById(investorId);
        if (newPhone.length() != 10) throw new IllegalArgumentException("Phone must be 10 digits.");
        inv.setPhone(newPhone);
        System.out.println("✅ Phone updated for " + investorId + " → " + newPhone);
    }

    // ── Assign / update risk profile ─────────────────────────────────
    public void assignRiskProfile(String investorId, RiskProfile rp) {
        Investor inv = getInvestorById(investorId);
        inv.setRiskProfile(rp);
        System.out.println("✅ Risk profile assigned to " + investorId + ": " + rp);
    }

    // ── Deactivate investor ──────────────────────────────────────────
    public void deactivateInvestor(String investorId) {
        Investor inv = getInvestorById(investorId);
        inv.setActive(false);
        System.out.println("⚠️  Investor deactivated: " + investorId);
    }

    // ── Print investor summary ───────────────────────────────────────
    public void printInvestorSummary(String investorId) {
        Investor inv = getInvestorById(investorId);
        System.out.println("═══════════════════════════════════════");
        System.out.println("  INVESTOR PROFILE");
        System.out.println("═══════════════════════════════════════");
        System.out.println("  ID       : " + inv.getInvestorId());
        System.out.println("  Name     : " + inv.getName());
        System.out.println("  Email    : " + inv.getEmail());
        System.out.println("  Phone    : " + inv.getPhone());
        System.out.println("  Bank     : " + inv.getBankAccount());
        System.out.println("  Nominee  : " + inv.getNominee());
        System.out.println("  Risk     : " + (inv.getRiskProfile() != null ? inv.getRiskProfile() : "Not assessed"));
        System.out.println("  Joined   : " + inv.getRegistrationDate());
        System.out.println("  Active   : " + (inv.isActive() ? "Yes" : "No"));
        System.out.println("═══════════════════════════════════════");
    }
}
