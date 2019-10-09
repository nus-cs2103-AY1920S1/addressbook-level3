package seedu.algobase.logic.commands;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.ObservableList;

import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.model.Model;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.ReadOnlyUserPrefs;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;

/**
 * A default model stub that have all of the methods failing.
 */

public class DefaultModelStub implements Model {

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
    public ObservableIntegerValue getDisplayTabPaneIndex() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Plan> getFilteredPlanList() {
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
