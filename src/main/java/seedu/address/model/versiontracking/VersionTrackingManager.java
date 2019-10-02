package seedu.address.model.versiontracking;

import java.util.ArrayList;

import seedu.address.model.studyplan.StudyPlan;

/**
 * Manages version tracking of the application.
 */
public class VersionTrackingManager {
    private static ArrayList<StudyPlanCommitManager> studyPlanCommitManagers = new ArrayList<>();

    /**
     * Initializes the commit manager for a study plan, so that the user can start making commits.
     * @param studyPlan
     */
    public void initializeStudyPlan(StudyPlan studyPlan) {
        StudyPlanCommitManager manager = new StudyPlanCommitManager(studyPlan);
        studyPlanCommitManagers.add(manager);
    }
}
