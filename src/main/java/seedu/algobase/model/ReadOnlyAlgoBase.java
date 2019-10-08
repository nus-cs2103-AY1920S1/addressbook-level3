package seedu.algobase.model;

import javafx.collections.ObservableList;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;

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
     * Returns an unmodifiable view of the plans list.
     */
    ObservableList<Plan> getPlanList();

}
