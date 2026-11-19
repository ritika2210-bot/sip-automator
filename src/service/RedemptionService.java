package service;

import enums.GainType;
import enums.TaxCategory;
import exception.InsufficientUnitsException;
import model.*;
import util.DateUtil;
import util.IDGenerator;

import java.util.LinkedList;
import java.util.List;

/**
 * Handles selling (redeeming) of fund units and triggers tax calculation.
 */
public class RedemptionService {

    private final LinkedList<Redemption> redemptionHistory = new LinkedList<>();
    private final TaxService             taxService;
    private final FundService            fundService;

    public RedemptionService(TaxService taxService, FundService fundService) {
        this.taxService  = taxService;
        this.fundService = fundService;
    }

    /**
     * Redeems (sells) a specified number of units from an investor's portfolio.
     * @throws InsufficientUnitsException if investor holds fewer units than requested.
     */
    public Redemption redeemUnits(Investor investor, String fundId, double units) {
        Portfolio portfolio  = investor.getPortfolio();
        double    heldUnits  = portfolio.getUnitsForFund(fundId);

        if (units > heldUnits) throw new InsufficientUnitsException(units, heldUnits);

        double nav             = fundService.getLatestNAV(fundId);
        String today           = DateUtil.today();
        String redemptionId    = IDGenerator.generateRedemptionId();

        Redemption redemption = new Redemption(redemptionId, investor.getInvestorId(),
                fundId, units, nav, today);

        // Calculate capital gain (uses FIFO — based on first buy transaction)
        Transaction firstBuy = portfolio.getTransactions().stream()
                .filter(t -> t.getFundId().equals(fundId))
                .findFirst().orElse(null);

        if (firstBuy != null) {
            long holdingDays = DateUtil.daysBetween(firstBuy.getDate(), today);
            GainType gainType = holdingDays >= 365 ? GainType.LTCG : GainType.STCG;
            CapitalGain gain = new CapitalGain(
                    IDGenerator.generateReportId("G"),
                    firstBuy.getTransactionId(), redemptionId,
                    firstBuy.getNavAtTransaction(), nav, units,
                    gainType, (int) holdingDays, today
            );
            redemption.setCapitalGain(gain);
            taxService.recordGain(investor.getInvestorId(), gain);
        }

        // Update portfolio
        double investedAmt = portfolio.getInvestedAmountForFund(fundId);
        double proportionalInvested = heldUnits > 0 ? (units / heldUnits) * investedAmt : 0;
        portfolio.removeUnits(fundId, units, proportionalInvested);
        portfolio.addRedemption(redemption);
        redemptionHistory.addLast(redemption);

        System.out.printf("[RedemptionService] Redeemed %.4f units of %s @ ₹%.2f = ₹%.2f%n",
                units, fundId, nav, redemption.getRedemptionAmount());
        return redemption;
    }

    /** Redeems all units of a fund for an investor. */
    public Redemption redeemAll(Investor investor, String fundId) {
        double allUnits = investor.getPortfolio().getUnitsForFund(fundId);
        return redeemUnits(investor, fundId, allUnits);
    }

    public List<Redemption> getRedemptionHistory(String investorId) {
        return redemptionHistory.stream()
                .filter(r -> r.getInvestorId().equals(investorId))
                .toList();
    }

    public double calculateRedemptionAmount(double units, double nav) {
        return Math.round(units * nav * 100.0) / 100.0;
    }
}
