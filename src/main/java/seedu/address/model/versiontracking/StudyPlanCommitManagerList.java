package seedu.address.model.versiontracking;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.exception.StudyPlanCommitManagerNotFoundException;

/**
 * Represents a list of {@code StudyPlanCommitManager}.
 */
public class StudyPlanCommitManagerList {
    private static final String EMPTY_COMMIT = "empty commit";

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
     * Deletes the StudyPlanCommitManager according to the index given.
     */
    public void delete(int index) throws StudyPlanCommitManagerNotFoundException {
        StudyPlanCommitManager managerToRemove = null;
        for (StudyPlanCommitManager manager : studyPlanCommitManagers) {
            if (manager.getStudyPlanIndex() == index) {
                managerToRemove = manager;
            }
        }
        if (managerToRemove != null) {
            studyPlanCommitManagers.remove(managerToRemove);
        }
    }

    /**
     * Gets the StudyPlanCommitManager according to the index of the study plan.
     */
    public StudyPlanCommitManager getManagerByStudyPlan(StudyPlan studyPlan) {
        int studyPlanIndex = studyPlan.getIndex();

        StudyPlanCommitManager managerToReturn = getManagerByStudyPlanIndex(studyPlanIndex);

        if (managerToReturn == null) {
            managerToReturn = new StudyPlanCommitManager(studyPlan, EMPTY_COMMIT);
        }

        return managerToReturn;
    }

    /**
     * Gets the StudyPlanCommitManager according to the given index. The result can be null.
     */
    public StudyPlanCommitManager getManagerByStudyPlanIndex(int index) {
        StudyPlanCommitManager managerToReturn = null;
        for (StudyPlanCommitManager manager : studyPlanCommitManagers) {
            if (manager.getStudyPlanIndex() == index) {
                managerToReturn = manager;
            }
        }
        return managerToReturn;
    }

    public void add(StudyPlanCommitManager manager) {
        studyPlanCommitManagers.add(manager);
    }

    /**
     * Method to check whether or not the manager already exists
     */
    public boolean managerAlreadyExists(StudyPlan studyPlan) {
        for (StudyPlanCommitManager manager : studyPlanCommitManagers) {
            if (manager.getStudyPlanIndex() == studyPlan.getIndex()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { // short circuit if identical objects
            return true;
        }

        if (!(obj instanceof StudyPlanCommitManagerList)) {
            return false; // handle null pointers
        }

        // check manager list
        try {
            for (int i = 0; i < studyPlanCommitManagers.size(); i++) {
                StudyPlanCommitManager manager1 = studyPlanCommitManagers.get(i);
                StudyPlanCommitManager manager2 = ((StudyPlanCommitManagerList) obj).studyPlanCommitManagers.get(i);
                if (!manager1.equals(manager2)) {
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }
}
