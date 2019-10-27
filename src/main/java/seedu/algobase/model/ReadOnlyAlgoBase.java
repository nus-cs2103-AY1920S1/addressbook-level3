package seedu.algobase.model;

import javafx.collections.ObservableList;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
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
     * Returns a view of the GuiState.
     */
    GuiState getGuiState();
}
