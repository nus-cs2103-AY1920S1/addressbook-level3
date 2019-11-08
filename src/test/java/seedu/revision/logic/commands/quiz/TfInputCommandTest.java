package seedu.revision.logic.commands.quiz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.testutil.TypicalTrueFalse.A_ANSWERABLE;
import static seedu.revision.testutil.TypicalTrueFalse.A_TF_COMMAND;


import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;

public class TfInputCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();


    @Test
    public void execute_tfAnswerFalseCaseInsensitive_correct() throws ParseException {
        Command commandResultNormal = new TfInputCommand("False", A_ANSWERABLE);
        Command commandResultUpperCase = new TfInputCommand("FALSE", A_ANSWERABLE);
        Command commandResultLowerCase = new TfInputCommand("false", A_ANSWERABLE);
        Command commandResultRandomCase = new TfInputCommand("FAlSe", A_ANSWERABLE);

        CommandResult expectedCommandResult = new CommandResult().withFeedBack("correct")
                .withHelp(false).withExit(false).build();
        try {
            assertCommandSuccess(commandResultNormal, model,
                    expectedCommandResult, expectedModel);
            assertCommandSuccess(commandResultUpperCase, model,
                    expectedCommandResult, expectedModel);
            assertCommandSuccess(commandResultLowerCase, model,
                    expectedCommandResult, expectedModel);
            assertCommandSuccess(commandResultRandomCase, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(TfInputCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void execute_tfAnswerTrue_wrong() throws ParseException {
        Command commandResult = new TfInputCommand("True", A_ANSWERABLE);
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
        Command commandCopy = new TfInputCommand("False", A_ANSWERABLE);
        assertTrue(A_TF_COMMAND.equals(commandCopy));

        // same object -> returns true
        assertTrue(commandCopy.equals(commandCopy));

        assertFalse(commandCopy.equals(null));

        // different type -> returns false
        assertFalse(commandCopy.equals(10));
        assertFalse(commandCopy.equals("TestString"));
    }
}
