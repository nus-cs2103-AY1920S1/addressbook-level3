package seedu.address.model.versiontracking;

import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.exception.StudyPlanCommitManagerNotFoundException;

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

    public StudyPlanCommitManager getStudyPlanCommitManagerByStudyPlan(StudyPlan studyPlan) {
        return studyPlanCommitManagers.getManagerByStudyPlan(studyPlan);
    }

    public StudyPlanCommitManagerList getStudyPlanCommitManagerList() {
        return studyPlanCommitManagers;
    }

    /**
     * Creates the commit manager for a study plan if it does not exist, so that the user can start making commits.
     *
     * @param studyPlan
     */
    public StudyPlanCommitManager commitStudyPlan(StudyPlan studyPlan, String commitMessage) {
        boolean managerAlreadyExists = getStudyPlanCommitManagerList().managerAlreadyExists(studyPlan);
        if (managerAlreadyExists) {
            StudyPlanCommitManager manager = studyPlanCommitManagers.getManagerByStudyPlan(studyPlan);
            manager.commit(studyPlan, commitMessage);
            return manager;
        } else {
            StudyPlanCommitManager manager = new StudyPlanCommitManager(studyPlan, commitMessage);
            studyPlanCommitManagers.add(manager);
            return manager;
        }
    }

    /**
     * Deletes a StudyPlanCommitManager with the given index, matching the study plan to be deleted.
     */
    public void deleteStudyPlanCommitManagerByIndex(int index) throws StudyPlanCommitManagerNotFoundException {
        studyPlanCommitManagers.delete(index);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || ((obj instanceof VersionTrackingManager)
                && studyPlanCommitManagers.equals(((VersionTrackingManager) obj).studyPlanCommitManagers));
    }
}
