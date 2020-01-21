package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_SOURCE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_WEBLINK;
import static seedu.algobase.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ID_QUICK_SORT = "id-1";
    public static final String VALID_AUTHOR_QUICK_SORT = "Steven Halim";
    public static final String VALID_DESCRIPTION_QUICK_SORT = "Sort an array quickly, in O(1) time =)";
    public static final String VALID_DIFFICULTY_QUICK_SORT = "1.5";
    public static final String VALID_NAME_QUICK_SORT = "Quick sort";
    public static final String VALID_REMARK_QUICK_SORT = "Steven says this is easy.";
    public static final String VALID_SOURCE_QUICK_SORT = "Kattis";
    public static final String VALID_WEBLINK_QUICK_SORT = "https://open.kattis.com/0";

    public static final String AUTHOR_DESC_QUICK_SORT = " " + PREFIX_AUTHOR + VALID_AUTHOR_QUICK_SORT;
    public static final String DESCRIPTION_DESC_QUICK_SORT = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_QUICK_SORT;
    public static final String DIFFICULTY_DESC_QUICK_SORT = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_QUICK_SORT;
    public static final String NAME_DESC_QUICK_SORT = " " + PREFIX_NAME + VALID_NAME_QUICK_SORT;
    public static final String REMARK_DESC_QUICK_SORT = " " + PREFIX_REMARK + VALID_REMARK_QUICK_SORT;
    public static final String SOURCE_DESC_QUICK_SORT = " " + PREFIX_SOURCE + VALID_SOURCE_QUICK_SORT;
    public static final String WEBLINK_DESC_QUICK_SORT = " " + PREFIX_WEBLINK + VALID_WEBLINK_QUICK_SORT;

    public static final String VALID_ID_TWO_SUM = "id-2";
    public static final String VALID_AUTHOR_TWO_SUM = "Alice Halim";
    public static final String VALID_DESCRIPTION_TWO_SUM = "B";
    public static final String VALID_DIFFICULTY_TWO_SUM = "2.0";
    public static final String VALID_NAME_TWO_SUM = "Two sum";
    public static final String VALID_REMARK_TWO_SUM = "B";
    public static final String VALID_SOURCE_TWO_SUM = "B";
    public static final String VALID_WEBLINK_TWO_SUM = "https://open.kattis.com/1";

    public static final String VALID_ID_FACTORIAL = "id-3";
    public static final String VALID_AUTHOR_FACTORIAL = "Clice Halim";
    public static final String VALID_DESCRIPTION_FACTORIAL = "C";
    public static final String VALID_DIFFICULTY_FACTORIAL = "2.5";
    public static final String VALID_NAME_FACTORIAL = "Factorial";
    public static final String VALID_REMARK_FACTORIAL = "C";
    public static final String VALID_SOURCE_FACTORIAL = "C";
    public static final String VALID_WEBLINK_FACTORIAL = "https://open.kattis.com/2";

    public static final String VALID_TAG_DIFFICULT = "difficult";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandHistory, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistroy,
                                            String expectedMessage, Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, actualCommandHistroy, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the algobase, filtered problem list and selected problem in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistroy,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AlgoBase expectedAlgoBase = new AlgoBase(actualModel.getAlgoBase());
        List<Problem> expectedFilteredList = new ArrayList<>(actualModel.getFilteredProblemList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, actualCommandHistroy));
        assertEquals(expectedAlgoBase, actualModel.getAlgoBase());
        assertEquals(expectedFilteredList, actualModel.getFilteredProblemList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s algobase.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredProblemList().size());

        Problem problem = model.getFilteredProblemList().get(targetIndex.getZeroBased());
        final String[] splitName = problem.getName().fullName.split("\\s+");
        model.updateFilteredProblemList(new NameContainsKeywordsPredicate(Arrays.asList(new Keyword(splitName[0]))));

        assertEquals(1, model.getFilteredProblemList().size());
    }

}
