package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTutorAid.getTypicalTutorAid;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) for {@code UnknownCommand}.
 */
public class UnknownCommandTest {

    private static final String FULL_COMMAND = "add n/caesar c/CS2030";
    private static final String COMMAND_WORD = "add";

    private Model model = new ModelManager(getTypicalTutorAid(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalTutorAid(), new UserPrefs());


    @Test
    public void execute_fullLengthCommand_failure() {
        model.saveCommand("Previous Valid Command");
        UnknownCommand unknownCommand = new UnknownCommand(FULL_COMMAND);
        assertTrue(FULL_COMMAND.split(" ").length > 1);
        String expectedMessage = String.format(UnknownCommand.UNKNOWN_COMMAND_USAGE
                        + model.getSavedCommand() + UnknownCommand.CANCEL_USAGE, FULL_COMMAND);
        assertCommandFailure(unknownCommand, model, expectedMessage);
    }

    @Test
    public void execute_commandWord_success() {
        UnknownCommand unknownCommand = new UnknownCommand(COMMAND_WORD);
        String expectedMessage = String.format(UnknownCommand.SHOWING_UNKNOWN_MESSAGE
                + UnknownCommand.CANCEL_USAGE, COMMAND_WORD);
        assertCommandSuccess(unknownCommand, model, expectedMessage, expectedModel);
    }

}
