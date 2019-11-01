package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static seedu.address.logic.commands.CommandTestUtil.DESC_USER_SETTINGS_1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_USER_SETTINGS_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FINE_INCREMENT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOAN_PERIOD_1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.UserSettings;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.SetUserSettingsDescriptorBuilder;
import seedu.address.testutil.TypicalUserSettings;
import seedu.address.testutil.UserSettingsBuilder;

public class SetCommandTest {
    private Model model =
            new ModelManager(new Catalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        UserSettings editedUserSettings = new UserSettingsBuilder().build();
        SetCommand.SetUserSettingsDescriptor descriptor = new SetUserSettingsDescriptorBuilder(editedUserSettings)
                .build();

        SetCommand setCommand = new SetCommand(descriptor);

        String expectedMessage = String.format(SetCommand.MESSAGE_SET_USER_SETTINGS_SUCCESS, editedUserSettings);

        Model expectedModel = new ModelManager(
                new Catalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());

        UserSettings userSettings = new UserSettings(UserSettingsBuilder.DEFAULT_LOAN_PERIOD,
                UserSettingsBuilder.DEFAULT_RENEW_PERIOD,
                UserSettingsBuilder.DEFAULT_FINE_INCREMENT,
                UserSettingsBuilder.DEFAULT_MAX_RENEWS);

        expectedModel.setUserSettings(userSettings);

        assertCommandSuccess(setCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_someFieldsSpecified_success() {
        SetCommand.SetUserSettingsDescriptor descriptor = new SetUserSettingsDescriptorBuilder()
                .withLoanPeriod(VALID_LOAN_PERIOD_1)
                .withFineIncrement(VALID_FINE_INCREMENT_1)
                .build();

        SetCommand setCommand = new SetCommand(descriptor);

        Model expectedModel = new ModelManager(
                new Catalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());

        expectedModel.setUserSettings(TypicalUserSettings.PARTIAL_USER_SETTINGS_1);

        String expectedMessage = String.format(SetCommand.MESSAGE_SET_USER_SETTINGS_SUCCESS,
                TypicalUserSettings.PARTIAL_USER_SETTINGS_1);

        assertCommandSuccess(setCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        SetCommand setCommand = new SetCommand(new SetCommand.SetUserSettingsDescriptor());

        Model expectedModel = new ModelManager(
                new Catalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());

        String expectedMessage = String.format(SetCommand.MESSAGE_SET_USER_SETTINGS_SUCCESS, new UserSettings());

        assertCommandSuccess(setCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final SetCommand standardCommand = new SetCommand(DESC_USER_SETTINGS_1);

        // same values -> returns true
        SetCommand.SetUserSettingsDescriptor copyDescriptor =
                new SetCommand.SetUserSettingsDescriptor(DESC_USER_SETTINGS_1);
        SetCommand commandWithSameValues = new SetCommand(copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new SetCommand(DESC_USER_SETTINGS_2));
    }
}
