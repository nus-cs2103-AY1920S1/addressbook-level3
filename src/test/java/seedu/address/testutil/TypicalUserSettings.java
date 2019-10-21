package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_FINE_INCREMENT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_PERIOD_1;

import seedu.address.commons.core.UserSettings;

/**
 * A utility class containing a list of {@code UserSettings} objects to be used in tests.
 */
public class TypicalUserSettings {

    public static final UserSettings PARTIAL_USER_SETTINGS_1 = new UserSettingsBuilder()
            .withLoanPeriod(VALID_LOAN_PERIOD_1)
            .withFineIncrement(VALID_FINE_INCREMENT_1)
            .build();

    public static final UserSettings USER_SETTINGS_1 = new UserSettingsBuilder()
            .withLoanPeriod("7")
            .withRenewPeriod("7")
            .withFineIncrement("50")
            .build();

    public static final UserSettings USER_SETTINGS_2 = new UserSettingsBuilder()
            .withLoanPeriod("14")
            .withRenewPeriod("14")
            .withFineIncrement("50")
            .build();

    private TypicalUserSettings() {}


}
