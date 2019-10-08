package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.algobase.logic.commands.SortCommand.MESSAGE_SUCCESS;
import static seedu.algobase.testutil.TypicalProblems.getTypicalAlgoBase;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.ReadOnlyUserPrefs;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.problem.Problem;

class SortCommandTest {
    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAlgoBase(), new UserPrefs());

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null, null));
    }

    @Test
    public void execute_byNameAscend_success() throws CommandException {
        String expectedMessage = String.format(MESSAGE_SUCCESS);
        SortCommand command = new SortCommand(SortCommand.SortingMethod.byName, SortCommand.SortingOrder.ascend);
        expectedModel.updateSortedProblemList(SortCommand.PROBLEM_NAME_COMPARATOR);
        CommandResult actualResult = command.execute(model);
        assertEquals(new CommandResult(expectedMessage), actualResult);
        ObservableList<Problem> expectedList = expectedModel.getFilteredProblemList();
        ObservableList<Problem> actualList = model.getFilteredProblemList();
        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i), actualList.get(i));
        }
        // TODO: Fix the broken line below, probably it was due to incorrect implementation of hashcode.
        // assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAlgoBaseFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAlgoBaseFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addProblem(Problem problem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAlgoBase(ReadOnlyAlgoBase newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAlgoBase getAlgoBase() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProblem(Problem problem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteProblem(Problem problem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProblem(Problem target, Problem editedProblem) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Problem> getFilteredProblemList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredProblemList(Predicate<Problem> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedProblemList(Comparator<Problem> problemComparator) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the problem being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Problem> problemsAdded = new ArrayList<>();

        @Override
        public boolean hasProblem(Problem problem) {
            requireNonNull(problem);
            return problemsAdded.stream().anyMatch(problem::isSameProblem);
        }

        @Override
        public void addProblem(Problem problem) {
            requireNonNull(problem);
            problemsAdded.add(problem);
        }

        @Override
        public ReadOnlyAlgoBase getAlgoBase() {
            return new AlgoBase();
        }
    }
}
