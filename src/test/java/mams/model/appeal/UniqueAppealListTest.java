package mams.model.appeal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mams.model.appeal.exceptions.AppealNotFoundException;
import mams.model.appeal.exceptions.DuplicateAppealException;

import mams.testutil.Assert;
import mams.testutil.TypicalAppeals;

public class UniqueAppealListTest {

    private final UniqueAppealList uniqueAppealList = new UniqueAppealList();

    @Test
    public void contains_nullAppeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueAppealList.contains(null));
    }

    @Test
    public void contains_appealNotInList_returnsFalse() {
        assertFalse(uniqueAppealList.contains(TypicalAppeals.APPEAL1));
    }

    @Test
    public void contains_appealInList_returnsTrue() {
        uniqueAppealList.add(TypicalAppeals.APPEAL1);
        assertTrue(uniqueAppealList.contains(TypicalAppeals.APPEAL1));
    }

    @Test
    public void add_nullAppeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueAppealList.add(null));
    }

    @Test
    public void add_duplicateAppeal_throwsDuplicateAppealException() {
        uniqueAppealList.add(TypicalAppeals.APPEAL1);
        Assert.assertThrows(DuplicateAppealException.class, () -> uniqueAppealList.add(TypicalAppeals.APPEAL1));
    }

    @Test
    public void remove_nullAppeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueAppealList.remove(null));
    }

    @Test
    public void remove_studentDoesNotExist_throwsStudentNotFoundException() {
        Assert.assertThrows(AppealNotFoundException.class, () -> uniqueAppealList.remove(TypicalAppeals.APPEAL1));
    }

    @Test
    public void remove_existingAppeal_removesAppeal() {
        uniqueAppealList.add(TypicalAppeals.APPEAL1);
        uniqueAppealList.remove(TypicalAppeals.APPEAL1);
        UniqueAppealList expectedUniqueAppealList = new UniqueAppealList();
        assertEquals(expectedUniqueAppealList, uniqueAppealList);
    }

}
