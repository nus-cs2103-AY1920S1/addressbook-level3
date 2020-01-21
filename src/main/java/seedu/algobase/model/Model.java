package seedu.algobase.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Plan> PREDICATE_SHOW_ALL_PLANS = unused -> true;
    Predicate<Problem> PREDICATE_SHOW_ALL_PROBLEMS = unused -> true;
    Predicate<Tag> PREDICATE_SHOW_ALL_TAGS = unused -> true;

    //=========== UserPref ==============================================================

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     * @param userPrefs the user preference used to replace existing data
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     * @param guiSettings the GUI settings used to replace existing data
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' algobase file path.
     */
    Path getAlgoBaseFilePath();

    /**
     * Sets the user prefs' algobase file path.
     * @param algoBaseFilePath the file path used to replace existing data
     */
    void setAlgoBaseFilePath(Path algoBaseFilePath);

    //=========== UI ====================================================================

    /**
     * Returns the state of the GUI.
     */
    GuiState getGuiState();

    //=========== AlgoBase ==============================================================

    /**
     * Replaces algobase data with the data in {@code algoBase}.
     * @param algoBase the data to be used to replace existing data
     */
    void setAlgoBase(ReadOnlyAlgoBase algoBase);

    /** Returns the AlgoBase */
    ReadOnlyAlgoBase getAlgoBase();

    //=========== Problem ===============================================================

    /**
     * Returns true if a Problem with the same identity as {@code Problem} exists in the algobase.
     * @param problem the problem to be checked
     */
    boolean hasProblem(Problem problem);

    /**
     * Deletes the given Problem.
     * The Problem must exist in the algobase.
     * @param problem the problem to be deleted
     */
    void deleteProblem(Problem problem);

    /**
     * Adds the given Problem.
     * {@code Problem} must not already exist in the algobase.
     * @param problem the problem to be added
     */
    void addProblem(Problem problem);

    /**
     * Replaces the given Problem {@code target} with {@code editedProblem}.
     * {@code target} must exist in the algobase.
     * The Problem identity of {@code editedProblem} must not be the same as another existing Problem in the algobase.
     * @param target the problem to be updated
     * @param editedProblem the updated problem
     */
    void setProblem(Problem target, Problem editedProblem);

    /** Returns an unmodifiable view of the filtered Problem list */
    ObservableList<Problem> getFilteredProblemList();

    /**
     * Updates the filter of the filtered Problem list to filter by the given {@code predicate}.
     * @param predicate the predicate used to filter the Problem List
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProblemList(Predicate<Problem> predicate);

    /**
     * Updates the Problem list according to the given {@code problemComparator}.
     * @param problemComparator the comparator to be used to sort the problem list
     * @throws NullPointerException if {@code problemComparator} is null;
     */
    void updateSortedProblemList(Comparator<Problem> problemComparator);

    //=========== Tag ===================================================================

    /**
     * Returns true if a Tag with the same identity as {@code Tag} exists in the algobase.
     * @param tag the tag to be checked
     */
    boolean hasTag(Tag tag);

    /**
     * Deletes the given Tag.
     * The Tag must exist in the algobase.
     * @param tag the tag to be deleted
     */
    void deleteTag(Tag tag);

    /**
     * Deletes the given Tag for all problems.
     * The Tag must exist in the algobase.
     * @param tag the tag to be deleted
     */
    void deleteTags(Tag tag);

    /**
     * Adds the given Tag.
     * {@code Tag} must not already exist in the algobase.
     * @param tag the tag to be added
     */
    void addTag(Tag tag);

    /**
     * Adds the given Tag list.
     * {@code Tag} must not already exist in the algobase.
     * @param tags the set of tags to be added
     */
    void addTags(Set<Tag> tags);

    /**
     * Replaces the given Tag {@code target} with {@code editedTag}.
     * {@code target} must exist in the algobase.
     * The Tag identity of {@code editedTag} must not be the same as another existing Tag in the algobase.
     * @param target the tag to be updated
     * @param editedTag the updated tag
     */
    void setTag(Tag target, Tag editedTag);

    /**
     * Replaces the given Tag {@code target} with {@code editedTag} for all problems in AlgoBase.
     * {@code target} must exist in the algobase.
     * The Tag identity of {@code editedTag} must not be the same as another existing Tag in the algobase.
     * @param target the tag to be updated
     * @param editedTag the updated tag
     */
    void setTags(Tag target, Tag editedTag);

    /** Returns an unmodifiable view of the filtered Tag list */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered Tag list to filter by the given {@code predicate}.
     * @param predicate the predicate used to filter the Plan List
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    //=========== Plan ==================================================================

    /**
     * Returns true if a Plan with the same identity as {@code Plan} exists in the algobase.
     * @param plan the plan to be checked
     */
    boolean hasPlan(Plan plan);

    /**
     * Deletes the given Plan.
     * The Plan must exist in the algobase.
     * @param plan the plan to be deleted
     */
    void deletePlan(Plan plan);

    /**
     * Adds the given Plan.
     * {@code Plan} must not already exist in the algobase.
     * @param plan the plan to be added
     */
    void addPlan(Plan plan);

    /**
     * Replaces the given Plan {@code target} with {@code editedPlan}.
     * {@code target} must exist in the algobase.
     * The Plan identity of {@code editedPlan} must not be the same as another existing Plan in the algobase.
     * @param target the plan to be updated
     * @param editedPlan the updated plan
     */
    void setPlan(Plan target, Plan editedPlan);

    /** Returns an unmodifiable view of the filtered Plan list */
    ObservableList<Plan> getFilteredPlanList();

    /**
     * Updates the filter of the filtered Plan list to filter by the given {@code predicate}.
     * @param predicate the predicate used to filter the Plan List
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPlanList(Predicate<Plan> predicate);

    /**
     * Checks if a problem is used in any plan.
     * @param problem the problem to be checked
     */
    boolean checkIsProblemUsed(Problem problem);

    /**
     * Removes the given problem from all plans.
     * @param problem the problem to be removed
     */
    void removeProblemFromAllPlans(Problem problem);

    /**
     * Updates the given problem in all plans.
     * @param oldProblem the existing problem to be updated
     * @param newProblem the new problem to be added
     */
    void updateProblemInAllPlans(Problem oldProblem, Problem newProblem);

    //=========== Task ==================================================================

    /**
     * Updates the task set in the given Plan.
     * @param taskSet the task set to be updated
     * @param plan the plan to be updated in
     */
    void updateTasks(Set<Task> taskSet, Plan plan);

    /**
     * Sets the given {@code Plan} as the current plan in main display.
     * @param plan the plan to be set as current plan
     */
    void setCurrentPlan(Plan plan);

    /**
     * Returns an unmodifiable view of the filtered Plan list.
     */
    ObservableList<Task> getCurrentTaskList();

    /**
     * Returns the current {@code Plan}.
     */
    StringProperty getCurrentPlan();

    /**
     * Returns the number of done tasks in current plan.
     */
    IntegerProperty getCurrentDoneCount();

    /**
     * Returns the number of undone tasks in current plan.
     */
    IntegerProperty getCurrentUndoneCount();

    /**
     * Returns the total number of tasks in current plan.
     */
    IntegerProperty getCurrentTaskCount();

    //========== Find Rules =============================================================

    /**
     * Returns true if the given {@code ProblemSearchRule} has the same identity as one that exists in the AlgoBase.
     * @param rule the rule to be matched
     */
    boolean hasFindRule(ProblemSearchRule rule);

    /**
     * Adds the given rule into AlgoBase.
     * @param rule the rule to be added
     */
    void addFindRule(ProblemSearchRule rule);

    /**
     * Deletes the given rule into AlgoBase.
     * The given {@code ProblemSearchRule} must exist in the AlgoBase.
     * @param rule the rule to be deleted
     */
    void deleteFindRule(ProblemSearchRule rule);

    /**
     * Replaces the given {@code ProblemSearchRule} with {@code editedRule}.
     * {@code target} must exist in the algobase.
     * The identity of {@code editedRule} must not be the same as another existing {@code ProblemSearchRule}
     * in the algobase.
     * @param target the rule to be edited
     * @param editedRule the edited rule
     */
    void setFindRule(ProblemSearchRule target, ProblemSearchRule editedRule);

    /**
     * Returns an unmodifiable view of the filtered list of AlgoBase's find rules.
     */
    ObservableList<ProblemSearchRule> getFilteredFindRuleList();

}
