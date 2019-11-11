package seedu.revision.logic.commands.quiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.revision.logic.commands.CommandTestUtil.COMMAND_RESULT_BUILDER_CORRECT_HELP_FALSE_EXIT_FALSE;
import static seedu.revision.logic.commands.CommandTestUtil.COMMAND_RESULT_BUILDER_WRONG_HELP_FALSE_EXIT_FALSE;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.testutil.TypicalSaqs.SAQ_A;
import static seedu.revision.testutil.TypicalSaqs.SAQ_B;
import static seedu.revision.testutil.TypicalSaqs.SAQ_B_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;


public class SaqInputCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();


    @Test
    public void execute_saqMatchingAnswer_correct() throws ParseException {
        Command commandResult = new SaqInputCommand(
                "Unit Testing", SAQ_A);
        CommandResult expectedCommandResult = new CommandResult(COMMAND_RESULT_BUILDER_CORRECT_HELP_FALSE_EXIT_FALSE);

        try {
            assertCommandSuccess(commandResult, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(TfInputCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void execute_saqRandomAnswer_wrong() throws ParseException {
        Command commandResult = new SaqInputCommand("I don't know", SAQ_A);
        CommandResult expectedCommandResult = new CommandResult(COMMAND_RESULT_BUILDER_WRONG_HELP_FALSE_EXIT_FALSE);
        try {
            assertCommandSuccess(commandResult, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(TfInputCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void equals() {
        // same values -> returns true
        Command commandCopy = new SaqInputCommand("UML Diagram", SAQ_B);
        assertEquals(commandCopy, SAQ_B_COMMAND);

        // same object -> returns true
        assertEquals(commandCopy, commandCopy);

        assertFalse(commandCopy.equals(null));

        // different type -> returns false
        assertFalse(commandCopy.equals(10));
        assertFalse(commandCopy.equals("TestString"));
    }
}
