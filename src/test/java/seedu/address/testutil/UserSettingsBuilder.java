package seedu.address.testutil;

import seedu.address.commons.core.UserSettings;
import seedu.address.model.usersettings.FineIncrement;
import seedu.address.model.usersettings.LoanPeriod;
import seedu.address.model.usersettings.MaxRenews;
import seedu.address.model.usersettings.RenewPeriod;

/**
 * A utility class to help with building UserSettings objects.
 */
public class UserSettingsBuilder {
    public static final int DEFAULT_LOAN_PERIOD = 14;
    public static final int DEFAULT_RENEW_PERIOD = 14;
    public static final int DEFAULT_FINE_INCREMENT = 10;
    public static final int DEFAULT_MAX_RENEWS = 1;

    private LoanPeriod loanPeriod;
    private RenewPeriod renewPeriod;
    private FineIncrement fineIncrement;
    private MaxRenews maxRenews;

    public UserSettingsBuilder() {
        loanPeriod = new LoanPeriod(DEFAULT_LOAN_PERIOD);
        renewPeriod = new RenewPeriod(DEFAULT_RENEW_PERIOD);
        fineIncrement = new FineIncrement(DEFAULT_FINE_INCREMENT);
        maxRenews = new MaxRenews(DEFAULT_MAX_RENEWS);
    }

    /**
     * Initializes the UserSettingsBuilder with the data of {@code userSettingsToCopy}.
     */
    public UserSettingsBuilder(UserSettings userSettingsToCopy) {
        loanPeriod = new LoanPeriod(userSettingsToCopy.getLoanPeriod());
        renewPeriod = new RenewPeriod(userSettingsToCopy.getRenewPeriod());
        fineIncrement = new FineIncrement(userSettingsToCopy.getFineIncrement());
        maxRenews = new MaxRenews(userSettingsToCopy.getMaxRenews());
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
     * Sets the {@cide maxRenews} of the {@code UserSettings} that we are building.
     */
    public UserSettingsBuilder withMaxRenews(String maxRenews) {
        this.maxRenews = new MaxRenews(maxRenews);
        return this;
    }

    /**
     * Returns a UserSettings object based on specified fields.
     * @return UserSettings object.
     */
    public UserSettings build() {
        return new UserSettings(loanPeriod.getLoanPeriod(), renewPeriod.getRenewPeriod(),
                fineIncrement.getFineIncrement(), maxRenews.getMaxRenews());
    }
}
