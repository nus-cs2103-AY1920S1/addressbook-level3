package seedu.address.model.versiontracking;

import java.util.ArrayList;
import java.util.List;

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

    public StudyPlanCommitManager get(int index) {
        return studyPlanCommitManagers.get(index);
    }

    public void add(StudyPlanCommitManager manager) {
        studyPlanCommitManagers.add(manager);
    }
}
