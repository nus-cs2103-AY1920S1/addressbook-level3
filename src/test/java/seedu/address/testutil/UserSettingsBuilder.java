package seedu.address.testutil;

import seedu.address.commons.core.UserSettings;
import seedu.address.model.usersettings.FineIncrement;
import seedu.address.model.usersettings.LoanPeriod;
import seedu.address.model.usersettings.RenewPeriod;

/**
 * A utility class to help with building UserSettings objects.
 */
public class UserSettingsBuilder {
    public static final int DEFAULT_LOAN_PERIOD = 14;
    public static final int DEFAULT_RENEW_PERIOD = 14;
    public static final int DEFAULT_FINE_INCREMENT = 10;

    private LoanPeriod loanPeriod;
    private RenewPeriod renewPeriod;
    private FineIncrement fineIncrement;

    public UserSettingsBuilder() {
        loanPeriod = new LoanPeriod(DEFAULT_LOAN_PERIOD);
        renewPeriod = new RenewPeriod(DEFAULT_RENEW_PERIOD);
        fineIncrement = new FineIncrement(DEFAULT_FINE_INCREMENT);
    }

    /**
     * Initializes the UserSettingsBuilder with the data of {@code userSettingsToCopy}.
     */
    public UserSettingsBuilder(UserSettings userSettingsToCopy) {
        loanPeriod = new LoanPeriod(userSettingsToCopy.getLoanPeriod());
        renewPeriod = new RenewPeriod(userSettingsToCopy.getRenewPeriod());
        fineIncrement = new FineIncrement((userSettingsToCopy.getFineIncrement()));
    }

    /**
     * Sets the {@code loanPeriod} of the {@code UserSettings} that we are building.
     */
    public UserSettingsBuilder withLoanPeriod(String loanPeriod) {
        this.loanPeriod = new LoanPeriod(loanPeriod);
        return this;
    }

    /**
     * Sets the {@code renewPeriod} of the {@code UserSettings} that we are building.
     */
    public UserSettingsBuilder withRenewPeriod(String renewPeriod) {
        this.renewPeriod = new RenewPeriod(renewPeriod);
        return this;
    }

    /**
     * Sets the {@code fineIncrement} of the {@code UserSettings} that we are building.
     */
    public UserSettingsBuilder withFineIncrement(String fineIncrement) {
        this.fineIncrement = new FineIncrement(fineIncrement);
        return this;
    }

    /**
     * Returns a UserSettings object based on specified fields.
     * @return UserSettings object.
     */
    public UserSettings build() {
        return new UserSettings(loanPeriod.getLoanPeriod(), renewPeriod.getRenewPeriod(),
                fineIncrement.getFineIncrement());
    }
}
