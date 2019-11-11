package seedu.tarence.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.TypicalPersons.getTypicalApplication;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void isMatchingCommandWord_validCommandWord_returnsTrue() {
        String userCommand = "clear";
        assertTrue(ClearCommand.isMatchingCommandWord(userCommand));
    }

    @Test
    public void isMatchingCommandWord_invalidCommandWord_returnsFalse() {
        String userCommand = "invalidCommand";
        assertFalse(ClearCommand.isMatchingCommandWord(userCommand));
    }

    @Test
    public void execute_valid() throws CommandException {
        ClearCommand clearCommand = new ClearCommand();
        Model model = new ModelManager(getTypicalApplication(), new UserPrefs());
        assertEquals(new CommandResult(ClearCommand.MESSAGE_SUCCESS), clearCommand.execute(model));
    }

}
