package seedu.algobase.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Problem> PREDICATE_SHOW_ALL_PROBLEMS = unused -> true;

    //=========== UserPref =============================================================
    Predicate<Plan> PREDICATE_SHOW_ALL_PLANS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' algobase file path.
     */
    Path getAlgoBaseFilePath();

    /**
     * Sets the user prefs' algobase file path.
     */
    void setAlgoBaseFilePath(Path algoBaseFilePath);

    //=========== AlgoBase =============================================================

    /**
     * Replaces algobase data with the data in {@code algoBase}.
     */
    void setAlgoBase(ReadOnlyAlgoBase algoBase);

    /** Returns the AlgoBase */
    ReadOnlyAlgoBase getAlgoBase();

    //=========== Problem List =============================================================

    /**
     * Returns true if a Problem with the same identity as {@code Problem} exists in the algobase.
     */
    boolean hasProblem(Problem problem);

    /**
     * Deletes the given Problem.
     * The Problem must exist in the algobase.
     */
    void deleteProblem(Problem target);

    /**
     * Adds the given Problem.
     * {@code Problem} must not already exist in the algobase.
     */
    void addProblem(Problem problem);

    /**
     * Replaces the given Problem {@code target} with {@code editedProblem}.
     * {@code target} must exist in the algobase.
     * The Problem identity of {@code editedProblem} must not be the same as another existing Problem in the algobase.
     */
    void setProblem(Problem target, Problem editedProblem);

    /** Returns an unmodifiable view of the filtered Problem list */
    ObservableList<Problem> getFilteredProblemList();

    /**
     * Updates the filter of the filtered Problem list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProblemList(Predicate<Problem> predicate);

    /**
     * Updates the Problem list according to the given {@code problemComparator}.
     * @param problemComparator
     * @throws NullPointerException if {@code problemComparator} is null;
     */
    void updateSortedProblemList(Comparator<Problem> problemComparator);

    //=========== Plan List =============================================================

    /**
     * Returns true if a Plan with the same identity as {@code Plan} exists in the algobase.
     */
    boolean hasPlan(Plan plan);

    /**
     * Deletes the given Plan.
     * The Plan must exist in the algobase.
     */
    void deletePlan(Plan target);

    /**
     * Adds the given Plan.
     * {@code Plan} must not already exist in the algobase.
     */
    void addPlan(Plan plan);

    /**
     * Replaces the given Plan {@code target} with {@code editedPlan}.
     * {@code target} must exist in the algobase.
     * The Plan identity of {@code editedPlan} must not be the same as another existing Plan in the algobase.
     */
    void setPlan(Plan target, Plan editedPlan);

    /** Returns an unmodifiable view of the filtered Plan list */
    ObservableList<Plan> getFilteredPlanList();

    /**
     * Updates the filter of the filtered Plan list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPlanList(Predicate<Plan> predicate);

}
