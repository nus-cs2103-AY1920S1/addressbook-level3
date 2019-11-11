package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the User settings.
 */
public class UserSettings implements Serializable {

    public static final int DEFAULT_LOAN_PERIOD = 14;
    public static final int DEFAULT_RENEW_PERIOD = 14;
    public static final int DEFAULT_FINE_INCREMENT = 10; // Fine increment in cents.
    public static final int DEFAULT_MAX_RENEWS = 1;

    private final int loanPeriod;
    private final int renewPeriod;
    private final int fineIncrement; // Fine increment in cents.
    private final int maxRenews;

    /**
     * Instantiates a UserSettings that contains the default loanPeriod, renewPeriod and fineIncrement.
     */
    public UserSettings() {
        loanPeriod = DEFAULT_LOAN_PERIOD;
        renewPeriod = DEFAULT_RENEW_PERIOD;
        fineIncrement = DEFAULT_FINE_INCREMENT;
        maxRenews = DEFAULT_MAX_RENEWS;
    }

    /**
     * Instantiates a UserSettings that contains the specified loanPeriod, renewPeriod and fineIncrement.
     * @param loanPeriod Number of days that a book can be loaned.
     * @param renewPeriod Number of additional days given when loan is renewed.
     * @param fineIncrement Fine that is added each day for an overdue book.
     * @param maxRenews Number of times a book can be renewed.
     */
    public UserSettings(int loanPeriod, int renewPeriod, int fineIncrement, int maxRenews) {
        this.loanPeriod = loanPeriod;
        this.renewPeriod = renewPeriod;
        this.fineIncrement = fineIncrement;
        this.maxRenews = maxRenews;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public int getRenewPeriod() {
        return renewPeriod;
    }

    public int getFineIncrement() {
        return fineIncrement;
    }

    public int getMaxRenews() {
        return maxRenews;
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

        return getLoanPeriod() == o.getLoanPeriod()
                && getRenewPeriod() == o.getRenewPeriod()
                && getFineIncrement() == o.getFineIncrement()
                && getMaxRenews() == o.getMaxRenews();
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanPeriod, renewPeriod, fineIncrement, maxRenews);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Loan period : " + loanPeriod + " days\n");
        sb.append("Renew period : " + renewPeriod + " days\n");
        sb.append("Fine increment : " + fineIncrement + " cents per day\n");
        sb.append("Maximum renew count : " + maxRenews);
        return sb.toString();
    }


}
