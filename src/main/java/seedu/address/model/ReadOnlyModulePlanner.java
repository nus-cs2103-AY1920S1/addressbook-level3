package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.VersionTrackingManager;

/**
 * Unmodifiable view of an module planner
 */
public interface ReadOnlyModulePlanner {

    /**
     * Returns an unmodifiable view of the study plans list.
     * This list will not contain any duplicate study plans.
     */
    ObservableList<StudyPlan> getStudyPlanList();

    /**
     * Returns the active study plan of this module planner.
     */
    StudyPlan getActiveStudyPlan();

    /**
     * Returns the version tracking manager of this module planner.
     */
    VersionTrackingManager getVersionTrackingManager();
}
