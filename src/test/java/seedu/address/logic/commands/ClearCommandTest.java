package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.FinSec;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private Model model = new ModelManager(new FinSec(), new UserPrefs());

    @Test
    public void execute_clear_success() {
        CommandResult commandResult = new ClearCommand().execute(model);
        assertEquals("clear"
                + ": Clears FinSec of all data. Warning! It is not reversible!"
                + " Would you still like to delete? \n"
                + "If yes, enter \"Y\" \n"
                + "Alternatively, enter \"N\"` to continue. ", commandResult.getFeedbackToUser());
    }
}
