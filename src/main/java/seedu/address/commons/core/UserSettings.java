package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the User settings.
 * Guarantees: immutable.
 */
public class UserSettings implements Serializable {

    private static final int DEFAULT_LOAN_PERIOD = 14;
    private static final int DEFAULT_RENEW_PERIOD = 14;
    private static final double DEFAULT_FINE_PERCENTAGE_INCREMENT = 10;

    private final int loanPeriod;
    private final int renewPeriod;
    private final double finePercentageIncrement;

    /**
     * Instantiates a UserSettings that contains the default loanPeriod, renewPeriod and finePercentageIncrement.
     */
    public UserSettings() {
        loanPeriod = DEFAULT_LOAN_PERIOD;
        renewPeriod = DEFAULT_RENEW_PERIOD;
        finePercentageIncrement = DEFAULT_FINE_PERCENTAGE_INCREMENT;
    }

    /**
     * Instantiates a UserSettings that contains the specified loanPeriod, renewPeriod and finePercentageIncrement.
     * @param loanPeriod Number of days that a book can be loaned.
     * @param renewPeriod Number of additional days given when loan is renewed.
     * @param finePercentageIncrement Percentage of the cost of the book that is added for each day of overdue.
     */
    public UserSettings(int loanPeriod, int renewPeriod, double finePercentageIncrement) {
        this.loanPeriod = loanPeriod;
        this.renewPeriod = renewPeriod;
        this.finePercentageIncrement = finePercentageIncrement;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public int getRenewPeriod() {
        return renewPeriod;
    }

    public double getFinePercentageIncrement() {
        return finePercentageIncrement;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserSettings)) { //this handles null as well.
            return false;
        }

        UserSettings o = (UserSettings) other;

        return loanPeriod == o.loanPeriod
                && renewPeriod == o.renewPeriod
                && finePercentageIncrement == o.finePercentageIncrement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanPeriod, renewPeriod, finePercentageIncrement);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Loan period : " + loanPeriod + "\n");
        sb.append("Renew period : " + renewPeriod + "\n");
        sb.append("Fine percentage increment : " + finePercentageIncrement);
        return sb.toString();
    }


}
