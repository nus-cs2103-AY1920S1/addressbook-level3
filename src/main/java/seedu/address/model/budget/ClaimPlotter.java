package seedu.address.model.budget;

import org.jfree.data.xy.XYSeries;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.Status;
import seedu.address.model.commonvariables.Date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *  Represents the claim portion of the Budget graph
 */

public class ClaimPlotter {
    private List<Claim> claimList;
    private double startingExpenses = 0;
    private final LocalDate currentLocalDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private String currentDateString = currentLocalDate.format(formatter);
    private Date currentDate = new Date(currentDateString);
    private XYSeries claimSeries;
    private int currentMonthNumber = currentDate.date.getMonthValue();
    private int currentYearNumber = currentDate.date.getYear();

    ClaimPlotter(List<Claim> claimList, XYSeries claimSeries) {
        this.claimList = claimList;
        this.claimSeries = claimSeries;
    }

    /**
     * Plots points of claim values
     */

    XYSeries plotClaims() {
        findClaimValueAtStartOfMonth();
        claimSeries.add(1, startingExpenses);
        double currentExpenses = startingExpenses;
        List<Claim> approvedClaimsInCurrentMonthList = findApprovedClaimsInCurrentMonth();
        for (Claim claim : approvedClaimsInCurrentMonthList) {
            for (int day = 2; day <= 31; day++){
                if (claim.getDate().date.getDayOfMonth() == day){
                    currentExpenses += Double.parseDouble(claim.getAmount().value);
                }
                claimSeries.add(day, currentExpenses);
            }
        }
        return claimSeries;
    }

    /**
     * Finds the claim value at the start of the month
     */

    private void findClaimValueAtStartOfMonth() {
        LocalDate firstDayOfMonth = LocalDate.of(currentYearNumber, currentMonthNumber, 2);
        for (Claim claim : claimList) {
            if (claim.getStatus() == Status.APPROVED) {
                if (claim.getDate().date.isBefore(firstDayOfMonth)) {
                    startingExpenses += Double.parseDouble(claim.getAmount().value);
                }
            }
        }
    }

    /**
     * Returns an updated list of claims that are approved and in the current month
     */

    private List<Claim> findApprovedClaimsInCurrentMonth() {
        List<Claim> updatedClaimList = new ArrayList<>();
        for (Claim claim : claimList) {
            if ((claim.getStatus() == Status.APPROVED)
                    && (currentMonthNumber == claim.getDate().date.getMonthValue())) {
                updatedClaimList.add(claim);
            }
        }
        return updatedClaimList;
    }
}
