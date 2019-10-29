package seedu.address.testutil;

import seedu.address.model.module.ModuleCode;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.Commit;
import seedu.address.model.versiontracking.CommitList;
import seedu.address.model.versiontracking.StudyPlanCommitManager;
import seedu.address.model.versiontracking.VersionTrackingManager;

/**
 * A class to generate sample version tracking related classes for testing.
 */
public class TypicalVersionTrackingManager {

    // original study plans (to be changed before commit)
    public static final StudyPlan SP_1 = TypicalStudyPlans.SP_1;
    public static final StudyPlan SP_2 = TypicalStudyPlans.SP_2;
    public static final StudyPlan SP_3 = TypicalStudyPlans.SP_3;

    public static final String SAMPLE_COMMIT_MESSAGE_1 = "first commit";
    public static final String SAMPLE_COMMIT_MESSAGE_2 = "second commit";
    public static final String SAMPLE_COMMIT_MESSAGE_3 = "third commit";

    // committed study plans
    private StudyPlan clonedSp1 = SP_1.clone();
    private StudyPlan clonedSp2 = SP_2.clone();
    private StudyPlan clonedSp3 = SP_3.clone();

    public TypicalVersionTrackingManager() throws CloneNotSupportedException {
        /*
        // add a module
        SP_1.addModuleToSemester(new ModuleCode("MA1101R"), SemesterName.Y2S2);
        // add a tag
        SP_2.addTag(new UserTag("difficult module"), "CS3230");
        // change the title
        SP_3.setTitle(new Title("New Title"));
         */
    }

    /**
     * Returns a typical commit for testing.
     */
    public static Commit getTypicalCommit(int index) {
        switch (index) {
        case 1:
            return new Commit(SP_1, SAMPLE_COMMIT_MESSAGE_1);
        case 2:
            return new Commit(SP_2, SAMPLE_COMMIT_MESSAGE_2);
        case 3:
            return new Commit(SP_3, SAMPLE_COMMIT_MESSAGE_3);
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns a typical commit list of two commits for testing.
     *
     * @return
     */
    public static CommitList getTypicalCommitList() throws CloneNotSupportedException {
        // tests may fail if this method is changed.
        CommitList testCommitList = new CommitList();
        testCommitList.commitStudyPlan(SP_1, SAMPLE_COMMIT_MESSAGE_1);
        // add a module
        StudyPlan planAfterAddingModule = SP_1.clone();
        planAfterAddingModule.addModuleToSemester(new ModuleCode("CS1101S"), SemesterName.Y2S2);
        testCommitList.commitStudyPlan(planAfterAddingModule, SAMPLE_COMMIT_MESSAGE_2);
        return testCommitList;
    }

    /**
     * Returns a typical StudyPlanCommitManager containing two commits for testing.
     *
     * @return
     */
    public static StudyPlanCommitManager getTypicalStudyPlanCommitManager() {
        StudyPlanCommitManager testManager = new StudyPlanCommitManager(SP_1, SAMPLE_COMMIT_MESSAGE_1);
        testManager.commit(SP_1, SAMPLE_COMMIT_MESSAGE_2);
        return testManager;
    }

    /**
     * Returns a typical VersionTrackingManager.
     */
    public static VersionTrackingManager getTypicalVersionTrackingManager() {
        VersionTrackingManager testManager = new VersionTrackingManager();

        testManager.commitStudyPlan(SP_1, SAMPLE_COMMIT_MESSAGE_1);
        testManager.commitStudyPlan(SP_1, SAMPLE_COMMIT_MESSAGE_2);
        testManager.commitStudyPlan(SP_1, SAMPLE_COMMIT_MESSAGE_3);

        testManager.commitStudyPlan(SP_2, SAMPLE_COMMIT_MESSAGE_1);
        testManager.commitStudyPlan(SP_2, SAMPLE_COMMIT_MESSAGE_2);
        testManager.commitStudyPlan(SP_2, SAMPLE_COMMIT_MESSAGE_3);

        testManager.commitStudyPlan(SP_3, SAMPLE_COMMIT_MESSAGE_1);
        testManager.commitStudyPlan(SP_3, SAMPLE_COMMIT_MESSAGE_2);
        testManager.commitStudyPlan(SP_3, SAMPLE_COMMIT_MESSAGE_3);

        return testManager;
    }

}
