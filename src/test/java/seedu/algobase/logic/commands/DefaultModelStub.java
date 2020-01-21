package seedu.algobase.logic.commands;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.model.Model;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.ReadOnlyUserPrefs;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.task.Task;

/**
 * A default model stub that have all of the methods failing.
 */
public class DefaultModelStub implements Model {

    //=========== UserPref ==============================================================

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
    public void setAlgoBaseFilePath(Path algobaseFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    //=========== UI ====================================================================

    @Override
    public GuiState getGuiState() {
        throw new AssertionError("This method should not be called.");
    }

    //=========== AlgoBase ==============================================================

    @Override
    public void setAlgoBase(ReadOnlyAlgoBase newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAlgoBase getAlgoBase() {
        throw new AssertionError("This method should not be called.");
    }

    //=========== Problem ===============================================================

    @Override
    public void addProblem(Problem problem) {
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

    //=========== Plan ==================================================================

    @Override
    public boolean hasPlan(Plan plan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePlan(Plan target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPlan(Plan plan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPlan(Plan target, Plan editedPlan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Plan> getFilteredPlanList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPlanList(Predicate<Plan> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean checkIsProblemUsed(Problem problem) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeProblemFromAllPlans(Problem problem) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateProblemInAllPlans(Problem oldProblem, Problem newProblem) {
        throw new AssertionError("This method should not be called.");
    }

    //=========== Tag ===================================================================

    @Override
    public boolean hasTag(Tag tag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTag(Tag target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTags(Tag target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTag(Tag tag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTags(Set<Tag> tags) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTag(Tag target, Tag editedTag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTags(Tag target, Tag editedTag) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    //=========== Task ==================================================================

    @Override
    public void updateTasks(Set<Task> taskSet, Plan plan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCurrentPlan(Plan plan) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getCurrentTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public StringProperty getCurrentPlan() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public IntegerProperty getCurrentDoneCount() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public IntegerProperty getCurrentUndoneCount() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public IntegerProperty getCurrentTaskCount() {
        throw new AssertionError("This method should not be called.");
    }

    //=========== Find Rule =============================================================

    @Override
    public boolean hasFindRule(ProblemSearchRule rule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addFindRule(ProblemSearchRule rule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteFindRule(ProblemSearchRule rule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setFindRule(ProblemSearchRule target, ProblemSearchRule editedRule) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<ProblemSearchRule> getFilteredFindRuleList() {
        throw new AssertionError("This method should not be called.");
    }

}
