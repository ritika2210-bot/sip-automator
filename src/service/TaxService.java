package service;

import model.CapitalGain;
import model.TaxReport;
import util.DateUtil;
import util.IDGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Calculates and tracks capital gains and taxes for investors.
 * Uses TreeSet inside TaxReport for date-sorted gain records.
 *
 * Tax rates (India 2024-25):
 *   Equity STCG = 20%
 *   Equity LTCG = 12.5% above ₹1.25L exemption
 */
public class TaxService {

    // investorId → list of gains
    private final Map<String, java.util.ArrayList<CapitalGain>> gainsByInvestor = new HashMap<>();

    /** Records a capital gain for an investor. */
    public void recordGain(String investorId, CapitalGain gain) {
        gainsByInvestor.computeIfAbsent(investorId, k -> new java.util.ArrayList<>()).add(gain);
        System.out.println("[TaxService] Recorded gain: " + gain);
    }

    /**
     * Generates a full annual tax report for an investor.
     * Filters gains belonging to the specified financial year.
     */
    public TaxReport generateTaxReport(String investorId, String financialYear) {
        TaxReport report = new TaxReport(IDGenerator.generateReportId("TAX"),
                investorId, financialYear);

        java.util.ArrayList<CapitalGain> gains =
                gainsByInvestor.getOrDefault(investorId, new java.util.ArrayList<>());

        for (CapitalGain gain : gains) {
            if (DateUtil.getFinancialYear(gain.getGainDate()).equals(financialYear)) {
                report.addGain(gain);
            }
        }
        System.out.println("[TaxService] Tax report generated: " + report);
        return report;
    }

    /** Returns the LTCG exemption limit (₹1.25 lakh for equity). */
    public double getExemptionLimit() { return 125000.0; }
}
