package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.algobase.logic.commands.problem.SortCommand.MESSAGE_SUCCESS;
import static seedu.algobase.testutil.TypicalAlgoBase.getTypicalAlgoBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.logic.commands.problem.SortCommand;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.problem.Problem;

class SortCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code filteredProblemList} matches the original list sorted with {@code comparator}
     * in content and order.
     */
    private void assertSuccessfullySorted(SortCommand command, Comparator<Problem> comparator) throws CommandException {
        String expectedMessage = MESSAGE_SUCCESS;
        CommandResult actualResult = command.execute(model, commandHistory);
        assertEquals(new CommandResult(expectedMessage), actualResult);
        List<Problem> expectedList = new ArrayList<>(expectedModel.getFilteredProblemList());
        expectedList.sort(comparator);
        ObservableList<Problem> actualList = model.getFilteredProblemList();
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
    }

    @Test
    public void constructor_nullMethod_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null, SortCommand.SortingOrder.ascend));
    }

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(SortCommand.SortingMethod.byName, null));
    }

    @Test
    public void execute_byNameAscend_success() throws CommandException {
        SortCommand command = new SortCommand(SortCommand.SortingMethod.byName, SortCommand.SortingOrder.ascend);
        assertSuccessfullySorted(command, SortCommand.PROBLEM_NAME_COMPARATOR);
    }

    @Test
    public void execute_byAuthorAscend_success() throws CommandException {
        SortCommand command = new SortCommand(SortCommand.SortingMethod.byAuthor, SortCommand.SortingOrder.ascend);
        assertSuccessfullySorted(command, SortCommand.PROBLEM_AUTHOR_COMPARATOR);
    }

    @Test
    public void execute_byWebLinkAscend_success() throws CommandException {
        SortCommand command = new SortCommand(SortCommand.SortingMethod.byWebLink, SortCommand.SortingOrder.ascend);
        assertSuccessfullySorted(command, SortCommand.PROBLEM_WEB_LINK_COMPARATOR);
    }

    @Test
    public void execute_byDifficultyAscend_success() throws CommandException {
        SortCommand command = new SortCommand(SortCommand.SortingMethod.byDifficulty, SortCommand.SortingOrder.ascend);
        assertSuccessfullySorted(command, SortCommand.PROBLEM_DIFFICULTY_COMPARATOR);
    }

    @Test
    public void execute_bySourceAscend_success() throws CommandException {
        SortCommand command = new SortCommand(SortCommand.SortingMethod.bySource, SortCommand.SortingOrder.ascend);
        assertSuccessfullySorted(command, SortCommand.PROBLEM_SOURCE_COMPARATOR);
    }

    @Test
    public void execute_byNameDescend_success() throws CommandException {
        SortCommand command = new SortCommand(SortCommand.SortingMethod.byName, SortCommand.SortingOrder.descend);
        assertSuccessfullySorted(command, SortCommand.PROBLEM_NAME_COMPARATOR.reversed());
    }

    @Test
    public void execute_byAuthorDescend_success() throws CommandException {
        SortCommand command = new SortCommand(SortCommand.SortingMethod.byAuthor, SortCommand.SortingOrder.descend);
        assertSuccessfullySorted(command, SortCommand.PROBLEM_AUTHOR_COMPARATOR.reversed());
    }

    @Test
    public void execute_byWebLinkDescend_success() throws CommandException {
        SortCommand command = new SortCommand(SortCommand.SortingMethod.byWebLink, SortCommand.SortingOrder.descend);
        assertSuccessfullySorted(command, SortCommand.PROBLEM_WEB_LINK_COMPARATOR.reversed());
    }

    @Test
    public void execute_byDifficultyDescend_success() throws CommandException {
        SortCommand command = new SortCommand(SortCommand.SortingMethod.byDifficulty, SortCommand.SortingOrder.descend);
        assertSuccessfullySorted(command, SortCommand.PROBLEM_DIFFICULTY_COMPARATOR.reversed());
    }

    @Test
    public void execute_bySourceDescend_success() throws CommandException {
        SortCommand command = new SortCommand(SortCommand.SortingMethod.bySource, SortCommand.SortingOrder.descend);
        assertSuccessfullySorted(command, SortCommand.PROBLEM_SOURCE_COMPARATOR.reversed());
    }
}
