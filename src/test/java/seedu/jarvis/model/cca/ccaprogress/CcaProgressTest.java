package seedu.jarvis.model.cca.ccaprogress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.jarvis.testutil.cca.TypicalCcaMilestones.RED_BLACK;
import static seedu.jarvis.testutil.cca.TypicalCcaMilestones.TIGER;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.cca.exceptions.CcaProgressNotIncrementedException;
import seedu.jarvis.model.cca.exceptions.MaxProgressNotSetException;

/**
 * Integration tests for {@code CcaProgress}.
 */
public class CcaProgressTest {

    private CcaProgress ccaProgress;

    @BeforeEach
    public void setUp() {
        ccaProgress = new CcaProgress();
    }

    @Test
    public void setMileStones_validInput_success() {
        CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();
        ccaProgress.setMilestones(ccaMilestoneList);
        assertEquals(ccaProgress.getCcaMilestoneList(), ccaMilestoneList);
        ccaMilestoneList.add(TIGER);
        ccaProgress.setMilestones(ccaMilestoneList);
        assertEquals(ccaProgress.getCcaMilestoneList(), ccaMilestoneList);
    }

    @Test
    public void getCcaProgressPercentage_validInput_success() {
        assertThrows(MaxProgressNotSetException.class, () -> ccaProgress.getCcaProgressPercentage());
        CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();
        ccaMilestoneList.add(TIGER);
        ccaMilestoneList.add(RED_BLACK);
        ccaProgress.setMilestones(ccaMilestoneList);
        assertEquals(ccaProgress.getCcaProgressPercentage(), 0);
        ccaProgress.increaseProgress();
        assertEquals(ccaProgress.getCcaProgressPercentage(), 0.5);
    }

    @Test
    public void getCurrentCcaMilestone_validInput_success() throws CcaProgressNotIncrementedException {
        CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();
        ccaMilestoneList.add(TIGER);
        ccaMilestoneList.add(RED_BLACK);
        ccaProgress.setMilestones(ccaMilestoneList);
        assertThrows(IndexOutOfBoundsException.class, () -> ccaProgress.getCurrentCcaMilestone());
        ccaProgress.increaseProgress();
        assertEquals(ccaProgress.getCurrentCcaMilestone(), TIGER);
    }
}
