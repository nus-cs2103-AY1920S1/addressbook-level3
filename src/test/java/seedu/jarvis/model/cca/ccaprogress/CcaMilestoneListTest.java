package seedu.jarvis.model.cca.ccaprogress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;
import static seedu.jarvis.testutil.cca.TypicalCcaMilestones.RED_BLACK;
import static seedu.jarvis.testutil.cca.TypicalCcaMilestones.TIGER;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.cca.exceptions.CcaMilestoneNotFoundException;
import seedu.jarvis.model.cca.exceptions.DuplicateCcaMilestoneException;

public class CcaMilestoneListTest {

    private final CcaMilestoneList ccaMilestoneList = new CcaMilestoneList();

    @Test
    public void contains_nullCcaMilestone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaMilestoneList.contains(null));
    }

    @Test
    public void contains_ccaMilestoneNotInList_returnsFalse() {
        assertFalse(ccaMilestoneList.contains(TIGER));
    }


    @Test
    public void contains_ccaMilestoneInList_returnsTrue() {
        ccaMilestoneList.add(TIGER);
        assertTrue(ccaMilestoneList.contains(TIGER));
    }

    @Test
    public void add_nullCcaMilestone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaMilestoneList.add(null));
    }

    @Test
    public void add_duplicateCcaMilestone_throwsDuplicateCcaMilestoneException() {
        ccaMilestoneList.add(TIGER);
        assertThrows(DuplicateCcaMilestoneException.class, () -> ccaMilestoneList.add(TIGER));
    }

    @Test
    public void setCcaMilestone_nullTargetCcaMilestone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaMilestoneList.setCcaMilestone(null, TIGER));
    }

    @Test
    public void setCcaMilestone_nullEditedCcaMilestone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaMilestoneList.setCcaMilestone(TIGER, null));
    }

    @Test
    public void setCcaMilestone_targetCcaMilestoneNotInList_throwsCcaMilestoneNotFoundException() {
        assertThrows(CcaMilestoneNotFoundException.class, () -> ccaMilestoneList.setCcaMilestone(TIGER, TIGER));
    }

    @Test
    public void setCcaMilestone_editedCcaMilestoneIsSameCcaMilestone_success() {
        ccaMilestoneList.add(TIGER);
        ccaMilestoneList.setCcaMilestone(TIGER, TIGER);
        CcaMilestoneList expectedCcaMilestoneList = new CcaMilestoneList();
        expectedCcaMilestoneList.add(TIGER);
        assertEquals(expectedCcaMilestoneList, ccaMilestoneList);
    }

    @Test
    public void setCcaMilestone_editedCcaMilestoneHasNonUniqueIdentity_throwsDuplicateCcaMilestoneException() {
        ccaMilestoneList.add(TIGER);
        ccaMilestoneList.add(RED_BLACK);
        assertThrows(DuplicateCcaMilestoneException.class, () -> ccaMilestoneList.setCcaMilestone(TIGER, RED_BLACK));
    }

    @Test
    public void remove_nullCcaMilestone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ccaMilestoneList.remove(null));
    }

    @Test
    public void remove_ccaMilestoneDoesNotExist_throwsCcaMilestoneNotFoundException() {
        assertThrows(CcaMilestoneNotFoundException.class, () -> ccaMilestoneList.remove(TIGER));
    }

    @Test
    public void remove_existingCcaMilestone_removesCcaMilestone() {
        ccaMilestoneList.add(TIGER);
        ccaMilestoneList.remove(TIGER);
        CcaMilestoneList expectedCcaMilestoneList = new CcaMilestoneList();
        assertEquals(expectedCcaMilestoneList, ccaMilestoneList);
    }
}
