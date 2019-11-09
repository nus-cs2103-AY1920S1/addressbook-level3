package seedu.revision.logic.commands.quiz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.testutil.TypicalSaq.SAQ_A;
import static seedu.revision.testutil.TypicalSaq.SAQ_A_COMMAND;

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
        CommandResult expectedCommandResult = new CommandResult().withFeedBack("correct")
                .withHelp(false).withExit(false).build();

        try {
            assertCommandSuccess(commandResult, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(TfInputCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void execute_saqRandomAnswer_wrong() throws ParseException {
        Command commandResult = new TfInputCommand("I don't know", SAQ_A);
        CommandResult expectedCommandResult = new CommandResult().withFeedBack("wrong")
                .withHelp(false).withExit(false).build();

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
        Command commandCopy = new SaqInputCommand("Unit Testing", SAQ_A);
        assertEquals(SAQ_A_COMMAND, commandCopy);

        // same object -> returns true
        assertEquals(commandCopy, commandCopy);

        assertFalse(commandCopy.equals(null));

        // different type -> returns false
        assertFalse(commandCopy.equals(10));
        assertFalse(commandCopy.equals("TestString"));
    }
}
