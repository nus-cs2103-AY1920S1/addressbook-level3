package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPerformance.getTypicalPerformance;
import static seedu.address.testutil.TypicalPersons.getTypicalAthletick;

import org.junit.jupiter.api.Test;

import seedu.address.model.Athletick;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrainingManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.history.HistoryManager;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        CommandResult expectedCommandResult = new CommandResult(ClearCommand.MESSAGE_SUCCESS, false, false,
                true);

        assertCommandSuccess(new ClearCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAthletick(), getTypicalPerformance(), new TrainingManager(),
                new UserPrefs(), new HistoryManager());
        Model expectedModel = new ModelManager(getTypicalAthletick(), getTypicalPerformance(), new TrainingManager(),
                new UserPrefs(), new HistoryManager());

        expectedModel.setAthletick(new Athletick());

        CommandResult expectedCommandResult = new CommandResult(ClearCommand.MESSAGE_SUCCESS,
                false, false, true);

        assertCommandSuccess(new ClearCommand(), model, expectedCommandResult, expectedModel);
    }

}
