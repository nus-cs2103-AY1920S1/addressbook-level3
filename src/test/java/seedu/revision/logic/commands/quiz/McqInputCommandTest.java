package seedu.revision.logic.commands.quiz;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.revision.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.revision.testutil.TypicalMcqs.G_MCQ_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.revision.logic.commands.Command;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.commands.main.CommandResultBuilder;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.Model;
import seedu.revision.model.ModelManager;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.testutil.McqBuilder;

public class McqInputCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private Mcq mcqTest = new McqBuilder().buildTest();

    @Test
    public void execute_mcqAnswerA_correct() throws ParseException {
        Command commandResult = new McqInputCommand("a", mcqTest);
        CommandResult expectedCommandResult = new CommandResultBuilder().withCorrect(true).build();

        try {
            assertCommandSuccess(commandResult, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(McqInputCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void execute_mcqAnswerB_wrong() throws ParseException {
        Command commandResult = new McqInputCommand("b", mcqTest);
        CommandResult expectedCommandResult = new CommandResultBuilder().withCorrect(false).build();

        try {
            assertCommandSuccess(commandResult, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(McqInputCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void execute_mcqAnswerC_wrong() throws ParseException {
        Command commandResult = new McqInputCommand("c", mcqTest);
        CommandResult expectedCommandResult = new CommandResultBuilder().withCorrect(false).build();

        try {
            assertCommandSuccess(commandResult, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(McqInputCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void execute_mcqAnswerD_wrong() throws ParseException {
        Command commandResult = new McqInputCommand("d", mcqTest);
        CommandResult expectedCommandResult = new CommandResultBuilder().withCorrect(false).build();

        try {
            assertCommandSuccess(commandResult, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(McqInputCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void execute_mcqAnswerN_wrong() throws ParseException {
        Command commandResult = new McqInputCommand("n", mcqTest);
        CommandResult expectedCommandResult = new CommandResultBuilder().withCorrect(false).build();

        try {
            assertCommandSuccess(commandResult, model,
                    expectedCommandResult, expectedModel);
        } catch (Exception e) {
            throw new ParseException(McqInputCommand.MESSAGE_USAGE);
        }
    }

    @Test
    public void equals() {
        // same values -> returns true
        Command commandCopy = new McqInputCommand("a", mcqTest);
        assertTrue(G_MCQ_COMMAND.equals(commandCopy));

        // same object -> returns true
        assertTrue(commandCopy.equals(commandCopy));

        assertFalse(commandCopy.equals(null));

        // different type -> returns false
        assertFalse(commandCopy.equals(10));
        assertFalse(commandCopy.equals("TestString"));
    }

}
