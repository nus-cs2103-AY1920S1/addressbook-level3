package seedu.address.model.versiontracking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.TypicalVersionTrackingManager.SP_1;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalVersionTrackingManager;

public class StudyPlanCommitManagerListTest {
    @Test
    public void get() {
        StudyPlanCommitManagerList managerList = new StudyPlanCommitManagerList();
        StudyPlanCommitManager managerToPut = TypicalVersionTrackingManager.getTypicalStudyPlanCommitManager();
        managerList.add(managerToPut);
        StudyPlanCommitManager managerToGet = managerList.get(0);
        assertEquals(managerToGet, managerToPut);
    }

    @Test
    public void delete() {
        StudyPlanCommitManagerList managerList = new StudyPlanCommitManagerList();
        StudyPlanCommitManager manager = TypicalVersionTrackingManager.getTypicalStudyPlanCommitManager();
        managerList.add(manager);
        managerList.delete(manager.getStudyPlanIndex());
        // the manager does not exist in the manager list anymore
        assertFalse(managerList.managerAlreadyExists(SP_1));
    }

}
