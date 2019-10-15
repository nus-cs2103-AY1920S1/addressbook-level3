package seedu.algobase.model;

import java.nio.file.Path;

import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Problem> PREDICATE_SHOW_ALL_PROBLEMS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Tag> PREDICATE_SHOW_ALL_TAGS = unused -> true;
    Predicate<Plan> PREDICATE_SHOW_ALL_PLANS = unused -> true;

    //=========== UserPref ==============================================================
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

    //=========== GUI state =============================================================

    /**
     * Returns the state of the GUI.
     */
    GuiState getGuiState();

    //=========== AlgoBase ==============================================================

    /**
     * Replaces algobase data with the data in {@code algoBase}.
     */
    void setAlgoBase(ReadOnlyAlgoBase algoBase);

    /** Returns the AlgoBase */
    ReadOnlyAlgoBase getAlgoBase();
    /////

    //=========== Problem ===============================================================

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
    /////

    /**
     * Returns true if a Tag with the same identity as {@code Tag} exists in the algobase.
     */
    boolean hasTag(Tag tag);

    /**
     * Deletes the given Tag.
     * The Tag must exist in the algobase.
     */
    void deleteTag(Tag target);

    /**
     * Deletes the given Tag for all problems.
     * The Tag must exist in the algobase.
     */
    void deleteTags(Tag target);

    /**
     * Adds the given Tag.
     * {@code Tag} must not already exist in the algobase.
     */
    void addTag(Tag tag);

    /**
     * Adds the given Tag list.
     * {@code Tag} must not already exist in the algobase.
     */
    void addTags(Set<Tag> tags);

    /**
     * Replaces the given Tag {@code target} with {@code editedTag}.
     * {@code target} must exist in the algobase.
     * The Tag identity of {@code editedTag} must not be the same as another existing Tag in the algobase.
     */
    void setTag(Tag target, Tag editedTag);

    /**
     * Replaces the given Tag {@code target} with {@code editedTag} for all problems in AlgoBase.
     * {@code target} must exist in the algobase.
     * The Tag identity of {@code editedTag} must not be the same as another existing Tag in the algobase.
     */
    void setTags(Tag target, Tag editedTag);

    /** Returns an unmodifiable view of the filtered Tag list */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered Tag list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    /**
     * Updates the Problem list according to the given {@code problemComparator}.
     * @param problemComparator
     * @throws NullPointerException if {@code problemComparator} is null;
     */
    void updateSortedProblemList(Comparator<Problem> problemComparator);

    //=========== Plan ==================================================================

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
