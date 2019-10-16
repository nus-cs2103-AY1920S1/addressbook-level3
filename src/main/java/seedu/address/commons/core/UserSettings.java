package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Objects;

import seedu.address.model.usersettings.FineIncrement;
import seedu.address.model.usersettings.LoanPeriod;
import seedu.address.model.usersettings.RenewPeriod;

/**
 * A Serializable class that contains the User settings.
 */
public class UserSettings implements Serializable {

    public static final int DEFAULT_LOAN_PERIOD = 14;
    private static final int DEFAULT_RENEW_PERIOD = 14;
    private static final int DEFAULT_FINE_INCREMENT = 10; // Fine increment in cents.

    private final int loanPeriod;
    private final int renewPeriod;
    private final int fineIncrement; //Fine increment in cents.

    /**
     * Instantiates a UserSettings that contains the default loanPeriod, renewPeriod and fineIncrement.
     */
    public UserSettings() {
        loanPeriod = DEFAULT_LOAN_PERIOD;
        renewPeriod = DEFAULT_RENEW_PERIOD;
        fineIncrement = DEFAULT_FINE_INCREMENT;
    }

    /**
     * Instantiates a UserSettings that contains the specified loanPeriod, renewPeriod and fineIncrement.
     * @param loanPeriod Number of days that a book can be loaned.
     * @param renewPeriod Number of additional days given when loan is renewed.
     * @param fineIncrement Fine that is added each day for an overdue book.
     */
    public UserSettings(int loanPeriod, int renewPeriod, int fineIncrement) {
        this.loanPeriod = loanPeriod;
        this.renewPeriod = renewPeriod;
        this.fineIncrement = fineIncrement;
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
                && getFineIncrement() == o.getFineIncrement();
    }

    @Override
    public int hashCode() {
        return Objects.hash(loanPeriod, renewPeriod, fineIncrement);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Loan period : " + loanPeriod + "\n");
        sb.append("Renew period : " + renewPeriod + "\n");
        sb.append("Fine percentage increment : " + fineIncrement);
        return sb.toString();
    }


}
