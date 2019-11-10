package seedu.algobase.model;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.commands.problem.SortCommand;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.task.Task;

/**
 * Represents the in-memory model of the algobase data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AlgoBase algoBase;
    private final UserPrefs userPrefs;
    private final FilteredList<Problem> filteredProblems;
    private final SortedList<Problem> sortedProblems;
    private final FilteredList<Tag> filteredTags;
    private final FilteredList<Plan> filteredPlans;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<ProblemSearchRule> filteredFindRules;

    /**
     * Initializes a ModelManager with the given algoBase and userPrefs.
     */
    public ModelManager(ReadOnlyAlgoBase algoBase, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(algoBase, userPrefs);

        logger.fine("Initializing with algobase: " + algoBase + " and user prefs " + userPrefs);

        this.algoBase = new AlgoBase(algoBase);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredProblems = new FilteredList<>(this.algoBase.getProblemList());
        filteredTags = new FilteredList<>(this.algoBase.getTagList());
        sortedProblems = new SortedList<>(filteredProblems);
        sortedProblems.setComparator(SortCommand.PROBLEM_NAME_COMPARATOR);
        filteredPlans = new FilteredList<>(this.algoBase.getPlanList());
        filteredTasks = new FilteredList<>(this.algoBase.getCurrentTaskList());
        filteredFindRules = new FilteredList<>(this.algoBase.getFindRules());
    }

    public ModelManager() {
        this(new AlgoBase(), new UserPrefs());
    }

    //========== UserPrefs ==============================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAlgoBaseFilePath() {
        return userPrefs.getAlgoBaseFilePath();
    }

    @Override
    public void setAlgoBaseFilePath(Path algoBaseFilePath) {
        requireNonNull(algoBaseFilePath);
        userPrefs.setAlgoBaseFilePath(algoBaseFilePath);
    }

    //========== GUI State ==============================================================
    @Override
    public GuiState getGuiState() {
        return algoBase.getGuiState();
    }

    //========== AlgoBase ===============================================================

    @Override
    public void setAlgoBase(ReadOnlyAlgoBase algoBase) {
        this.algoBase.resetData(algoBase);
    }

    @Override
    public ReadOnlyAlgoBase getAlgoBase() {
        return algoBase;
    }

    //========== Problem ================================================================

    @Override
    public boolean hasProblem(Problem problem) {
        requireNonNull(problem);
        return algoBase.hasProblem(problem);
    }

    @Override
    public void deleteProblem(Problem target) {
        algoBase.removeProblem(target);
    }

    @Override
    public void addProblem(Problem problem) {
        algoBase.addProblem(problem);
        updateFilteredProblemList(PREDICATE_SHOW_ALL_PROBLEMS);
    }

    @Override
    public void setProblem(Problem target, Problem editedProblem) {
        requireAllNonNull(target, editedProblem);
        algoBase.setProblem(target, editedProblem);
    }

    @Override
    public ObservableList<Problem> getFilteredProblemList() {
        return sortedProblems;
    }

    @Override
    public void updateFilteredProblemList(Predicate<Problem> predicate) {
        requireNonNull(predicate);
        filteredProblems.setPredicate(predicate);
    }

    /**
     * Updates the Problem list according to the given {@code problemComparator}.
     *
     * @param problemComparator a comparator of problems
     * @throws NullPointerException if {@code problemComparator} is null;
     */
    @Override
    public void updateSortedProblemList(Comparator<Problem> problemComparator) {
        requireNonNull(problemComparator);
        sortedProblems.setComparator(problemComparator);
    }

    //=========== Tag ===================================================================

    @Override
    public boolean hasTag(Tag tag) {
        requireNonNull(tag);
        return algoBase.hasTag(tag);
    }

    @Override
    public void deleteTag(Tag target) {
        algoBase.removeTag(target);
    }

    @Override
    public void deleteTags(Tag target) {
        for (Problem problem : filteredProblems) {
            Set<Tag> targetTags = problem.getTags();
            if (targetTags.contains(target)) {
                problem.deleteTag(target);
            }
        }
        algoBase.refreshTagForProblemsWithTag();
    }

    @Override
    public void addTag(Tag tag) {
        algoBase.addTag(tag);
        updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
    }

    @Override
    public void addTags(Set<Tag> tags) {
        for (Tag tag : tags) {
            addTag(tag);
        }
    }

    @Override
    public void setTag(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);

        algoBase.setTag(target, editedTag);
    }

    @Override
    public void setTags(Tag target, Tag editedTag) {
        requireAllNonNull(target, editedTag);
        for (Problem problem : filteredProblems) {
            Set<Tag> targetTags = problem.getTags();
            if (targetTags.contains(target)) {
                problem.deleteTag(target);
                problem.addTag(editedTag);
            }
        }
        algoBase.resetTagForProblemsWithTag(target, editedTag);
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return filteredTags;
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

    //========== Plan ===================================================================

    @Override
    public boolean hasPlan(Plan plan) {
        requireNonNull(plan);
        return algoBase.hasPlan(plan);
    }

    @Override
    public void deletePlan(Plan target) {
        algoBase.removePlan(target);
    }

    @Override
    public void addPlan(Plan plan) {
        algoBase.addPlan(plan);
        updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);
    }

    @Override
    public void setPlan(Plan target, Plan editedPlan) {
        requireAllNonNull(target, editedPlan);

        algoBase.setPlan(target, editedPlan);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Plan} backed by the internal list of
     */
    @Override
    public ObservableList<Plan> getFilteredPlanList() {
        return filteredPlans;
    }

    @Override
    public void updateFilteredPlanList(Predicate<Plan> predicate) {
        requireNonNull(predicate);
        filteredPlans.setPredicate(predicate);
    }

    @Override
    public boolean checkIsProblemUsed(Problem problem) {
        return algoBase.checkIsProblemUsed(problem);
    }

    @Override
    public void removeProblemFromAllPlans(Problem problem) {
        algoBase.removeProblemFromAllPlans(problem);
    }

    @Override
    public void updateProblemInAllPlans(Problem oldProblem, Problem newProblem) {
        algoBase.updateProblemInAllPlans(oldProblem, newProblem);
    }

    //========== Task ===================================================================

    @Override
    public void updateTasks(Set<Task> taskSet, Plan plan) {
        this.algoBase.updateTasks(taskSet, plan);
    }

    @Override
    public void setCurrentPlan(Plan plan) {
        this.algoBase.setCurrentPlan(plan);
    }

    @Override
    public ObservableList<Task> getCurrentTaskList() {
        return filteredTasks;
    }

    @Override
    public StringProperty getCurrentPlan() {
        return this.algoBase.getCurrentPlan();
    }

    @Override
    public IntegerProperty getCurrentSolvedCount() {
        return this.algoBase.getCurrentSolvedCount();
    }

    @Override
    public IntegerProperty getCurrentUnsolvedCount() {
        return this.algoBase.getCurrentUnsolvedCount();
    }

    @Override
    public IntegerProperty getCurrentTaskCount() {
        return this.algoBase.getCurrentTaskCount();
    }

    //========== Find Rules =============================================================

    @Override
    public boolean hasFindRule(ProblemSearchRule rule) {
        requireNonNull(rule);
        return algoBase.hasFindRule(rule);
    }


    @Override
    public void addFindRule(ProblemSearchRule rule) {
        requireNonNull(rule);
        algoBase.addFindRule(rule);
    }

    @Override
    public void deleteFindRule(ProblemSearchRule rule) {
        requireNonNull(rule);
        algoBase.removeFindRule(rule);
    }

    @Override
    public void setFindRule(ProblemSearchRule target, ProblemSearchRule editedRule) {
        requireAllNonNull(target, editedRule);
        algoBase.setFindRule(target, editedRule);
    }

    @Override
    public ObservableList<ProblemSearchRule> getFilteredFindRuleList() {
        return filteredFindRules;
    }


    //========== Util ===================================================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return algoBase.equals(other.algoBase)
            && userPrefs.equals(other.userPrefs)
            && filteredProblems.equals(other.filteredProblems);
    }
}
