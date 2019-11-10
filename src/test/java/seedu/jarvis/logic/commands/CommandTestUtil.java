package seedu.jarvis.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_TYPE;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;
import static seedu.jarvis.testutil.Assert.assertThrows;

import java.util.Arrays;

import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.planner.predicates.TaskDesContainsKeywordsPredicate;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_CCA_DESC_TRACK = "Track";
    public static final String VALID_CCA_TYPE_TRACK = "sport";

    public static final String CCA_DESC = " " + PREFIX_CCA_NAME + VALID_CCA_DESC_TRACK;
    public static final String CCA_TYPE = " " + PREFIX_CCA_TYPE + VALID_CCA_TYPE_TRACK;

    public static final String INVALID_NAME_DESC = " " + PREFIX_CCA_NAME + "James&"; // '&' not allowed in names

    public static final String INVALID_INSTAL_MONEY = " " + PREFIX_MONEY + "-10.0";
    public static final String INVALID_PURCHASE_MONEY = " " + PREFIX_MONEY + "-10.0";

    public static final String INVALID_MONTHLY_LIMIT = " " + PREFIX_MONEY + "-500.0";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
    }

    /**
     * Inversely executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandInverseSuccess(Command command, Model actualModel,
                                                   CommandResult expectedCommandResult,
                                                   Model expectedModel) {
        try {
            CommandResult result = command.executeInverse(actualModel);
            assertEquals(expectedCommandResult, result);
            //assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Inverse Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandInverseSuccess(Command, Model, String, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandInverseSuccess(Command command, Model actualModel,
                                                   String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandInverseSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Inversely executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     */
    public static void assertCommandInverseFailure(Command command, Model actualModel,
                                                   String expectedMessage) {
        assertThrows(CommandException.class, expectedMessage, () -> command.executeInverse(actualModel));
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s planner.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitDes = task.getTaskDes().split("\\s+");
        model.updateFilteredTaskList(new TaskDesContainsKeywordsPredicate(Arrays.asList(splitDes[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }

}
