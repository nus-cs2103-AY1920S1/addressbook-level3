package seedu.address.model.budget;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jfree.data.xy.XYSeries;

import seedu.address.model.claim.Claim;
import seedu.address.model.claim.Status;
import seedu.address.model.commonvariables.Date;

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
    private XYSeries claimSeries = new XYSeries("Claim");
    private int currentMonthNumber = currentDate.date.getMonthValue();
    private int currentYearNumber = currentDate.date.getYear();

    ClaimPlotter(List<Claim> claimList) {
        this.claimList = claimList;
    }

    /**
     * Plots points of claim values
     */

    XYSeries plotClaims() {
        findClaimValueAtStartOfMonth();
        claimSeries.add(1, startingExpenses);
        double currentExpenses = startingExpenses;
        List<Claim> approvedClaimsInCurrentMonthList = findApprovedClaimsInCurrentMonth();
        for (int day = 2; day <= 30; day++) {
            for (Claim claim : approvedClaimsInCurrentMonthList) {
                if (claim.getDate().date.getDayOfMonth() == day) {
                    currentExpenses += Double.parseDouble(claim.getAmount().value);
                    currentExpenses = Math.round(currentExpenses * 100) / 100.0;
                    assert currentExpenses >= 0 : "A negative claim value managed to get into the claim list";
                }
            }
            claimSeries.add(day, currentExpenses);
        }
        return claimSeries;
    }

    /**
     * Finds the claim value at the start of the month
     */

    private void findClaimValueAtStartOfMonth() {
        assert currentMonthNumber <= 12: "There is an error with LocalDate Month";
        assert currentYearNumber > 0: "There is an error with LocalDate Year";
        LocalDate firstDayOfMonth = LocalDate.of(currentYearNumber, currentMonthNumber, 2);
        for (Claim claim : claimList) {
            if (claim.getStatus() == Status.APPROVED) {
                if (claim.getDate().date.isBefore(firstDayOfMonth)) {
                    startingExpenses += Double.parseDouble(claim.getAmount().value);
                    startingExpenses = Math.round(startingExpenses * 100) / 100.0;
                    assert startingExpenses >= 0 : "A negative claim value managed to get into the claim list";
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
                    && (currentMonthNumber == claim.getDate().date.getMonthValue()
                    && currentYearNumber == claim.getDate().date.getYear())) {
                assert currentMonthNumber <= 12: "There is an error with LocalDate Month";
                assert currentYearNumber > 0: "There is an error with LocalDate Year";
                updatedClaimList.add(claim);
            }
        }
        return updatedClaimList;
    }
}
