package seedu.address.model.versiontracking;

import seedu.address.model.studyplan.StudyPlan;

/**
 * Manages version tracking of the application.
 */
public class VersionTrackingManager {
    private StudyPlanCommitManagerList studyPlanCommitManagers;

    public VersionTrackingManager() {
        studyPlanCommitManagers = new StudyPlanCommitManagerList();
    }

    /**
     * This constructor is used for {@code JsonAdaptedVersionTrackingManager} to construct a
     * {@code VersionTrackingManager} object from the given details.
     */
    public VersionTrackingManager(StudyPlanCommitManagerList managers) {
        studyPlanCommitManagers = managers;
    }

    public StudyPlanCommitManager getStudyPlanCommitManagerByIndex(int index) {
        return studyPlanCommitManagers.get(index);
    }

    public StudyPlanCommitManagerList getStudyPlanCommitManagerList() {
        return studyPlanCommitManagers;
    }

    /**
     * Initializes the commit manager for a study plan, so that the user can start making commits.
     * @param studyPlan
     */
    public void initializeStudyPlan(StudyPlan studyPlan) {
        StudyPlanCommitManager manager = new StudyPlanCommitManager(studyPlan);
        studyPlanCommitManagers.add(manager);
    }
}
