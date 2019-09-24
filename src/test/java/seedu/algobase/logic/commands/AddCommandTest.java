package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.ReadOnlyUserPrefs;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.testutil.ProblemBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_problemAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingProblemAdded modelStub = new ModelStubAcceptingProblemAdded();
        Problem validProblem = new ProblemBuilder().build();

        CommandResult commandResult = new AddCommand(validProblem).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validProblem), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProblem), modelStub.problemsAdded);
    }

    @Test
    public void execute_duplicateProblem_throwsCommandException() {
        Problem validProblem = new ProblemBuilder().build();
        AddCommand addCommand = new AddCommand(validProblem);
        ModelStub modelStub = new ModelStubWithProblem(validProblem);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Problem alice = new ProblemBuilder().withName("Alice").build();
        Problem bob = new ProblemBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different Problem -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public void setAlgoBaseFilePath(Path algoBaseFilePath) {
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
        public void deleteProblem(Problem target) {
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
    }

    /**
     * A Model stub that contains a single Problem.
     */
    private class ModelStubWithProblem extends ModelStub {
        private final Problem problem;

        ModelStubWithProblem(Problem problem) {
            requireNonNull(problem);
            this.problem = problem;
        }

        @Override
        public boolean hasProblem(Problem problem) {
            requireNonNull(problem);
            return this.problem.isSameProblem(problem);
        }
    }

    /**
     * A Model stub that always accept the Problem being added.
     */
    private class ModelStubAcceptingProblemAdded extends ModelStub {
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
