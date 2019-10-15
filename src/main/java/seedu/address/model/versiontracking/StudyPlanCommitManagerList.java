package seedu.address.model.versiontracking;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents a list of {@code StudyPlanCommitManager}.
 */
public class StudyPlanCommitManagerList {
    private List<StudyPlanCommitManager> studyPlanCommitManagers;

    public StudyPlanCommitManagerList() {
        studyPlanCommitManagers = new ArrayList<>();
    }

    public StudyPlanCommitManagerList(List<StudyPlanCommitManager> managers) {
        studyPlanCommitManagers = managers;
    }

    public List<StudyPlanCommitManager> getStudyPlanCommitManagers() {
        return studyPlanCommitManagers;
    }

    /**
     * Gets the StudyPlanCommitManager according to its index in studyPlanCommitManagers list.
     */
    public StudyPlanCommitManager get(int index) {
        return studyPlanCommitManagers.get(index);
    }

    /**
     * Gets the StudyPlanCommitManager according to the index of the study plan
     */
    public StudyPlanCommitManager getManagerByStudyPlan(StudyPlan studyPlan) {
        StudyPlanCommitManager managerToReturn = null;
        for (StudyPlanCommitManager manager : studyPlanCommitManagers) {
            if (manager.getStudyPlanIndex() == studyPlan.getIndex()) {
                managerToReturn = manager;
            }
        }
        if (managerToReturn == null) {
            // TODO: change the commit message.
            managerToReturn = new StudyPlanCommitManager(studyPlan, "empty commit");
        }

        return managerToReturn;
    }

    public void add(StudyPlanCommitManager manager) {
        studyPlanCommitManagers.add(manager);
    }

    public boolean managerAlreadyExists(StudyPlan studyPlan) {
        for (StudyPlanCommitManager manager : studyPlanCommitManagers) {
            if (manager.getStudyPlanIndex() == studyPlan.getIndex()) {
                return true;
            }
        }
        return false;
    }
}
