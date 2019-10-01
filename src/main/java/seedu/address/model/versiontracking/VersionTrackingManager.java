package seedu.address.model.versiontracking;

import java.util.ArrayList;

import seedu.address.model.studyplan.StudyPlan;

public class VersionTrackingManager {
    private static ArrayList<StudyPlanCommitManager> studyPlanCommitManagers = new ArrayList<>();

    public void initializeStudyPlan(StudyPlan studyPlan) {
        StudyPlanCommitManager manager = new StudyPlanCommitManager(studyPlan);
        studyPlanCommitManagers.add(manager);
    }
}
