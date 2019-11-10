package seedu.algobase.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.task.Task;

/**
 * Unmodifiable view of an algobase
 */
public interface ReadOnlyAlgoBase {

    /**
     * Returns an unmodifiable view of the problems list.
     * This list will not contain any duplicate problems.
     */
    ObservableList<Problem> getProblemList();

    /**
     * Returns the {@code Problem} with the same id in the algobase.
     */
    Problem findProblemById(Id problemId) throws IllegalValueException;

    /**
     * Checks whether a problem is used in any plan.
     */
    boolean checkIsProblemUsed(Problem problem);

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
     * Returns the {@code Tag} with the same id in the algobase.
     */
    Tag findTagById(Id tagId) throws IllegalValueException;

    /**
     * Returns an unmodifiable view of the plans list.
     */
    ObservableList<Plan> getPlanList();

    /**
     * Returns the {@code Plan} with the same id in the algobase.
     */
    Plan findPlanById(Id planId) throws IllegalValueException;

    /**
     * Returns an unmodifiable view of the current tasks list.
     */
    ObservableList<Task> getCurrentTaskList();

    /**
     * Returns current plan name.
     */
    StringProperty getCurrentPlan();

    /**
     * Sets the given plan as the current plan in main display
     * @param plan the plan to be set as current plan
     */
    void setCurrentPlan(Plan plan);

    /**
     * Sets current plan name.
     */
    void setCurrentPlan(int index);

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

    /**
     * Returns an unmodifiable view of the find rule list.
     */
    ObservableList<ProblemSearchRule> getFindRules();


    /**
     * Returns a view of the GuiState.
     */
    GuiState getGuiState();

}
