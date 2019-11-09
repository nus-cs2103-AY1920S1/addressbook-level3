package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AutoCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEarnings.getTypicalTutorAid;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.earnings.Earnings;

public class AutoCommandTest {


    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());

    @Test
    public void execute_auto_success() {
        Earnings earningsToAutomate = model.getFilteredEarningsList().get(INDEX_FIRST.getZeroBased());
        AutoCommand auto = new AutoCommand();
        String expectedCommandResult = String.format(MESSAGE_SUCCESS, earningsToAutomate);

        Model expectedModel = new ModelManager(model.getTutorAid(), new UserPrefs());
        showNoEarnings(expectedModel);

        assertCommandSuccess(auto, model, expectedCommandResult, expectedModel);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEarnings(Model model) {
        model.updateFilteredEarningsList(p -> false);

        assertTrue(model.getFilteredEarningsList().isEmpty());
    }
}
