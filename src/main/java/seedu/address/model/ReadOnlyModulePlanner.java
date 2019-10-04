package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.studyplan.StudyPlan;

/**
 * Unmodifiable view of an module planner
 */
public interface ReadOnlyModulePlanner {

    /**
     * Returns an unmodifiable view of the study plans list.
     * This list will not contain any duplicate study plans.
     */
    ObservableList<StudyPlan> getStudyPlanList();

}
