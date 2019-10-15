package mams.model.appeal;

import mams.model.appeal.exceptions.AppealNotFoundException;
import mams.model.appeal.exceptions.DuplicateAppealException;
import mams.testutil.Assert;
import mams.testutil.TypicalAppeals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UniqueAppealListTest {

    private final UniqueAppealList uniqueAppealList = new UniqueAppealList();

    @Test
    public void contains_nullAppeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueAppealList.contains(null));
    }

    @Test
    public void contains_appealNotInList_returnsFalse() {
        assertFalse(uniqueAppealList.contains(TypicalAppeals.appeal1));
    }

    @Test
    public void contains_appealInList_returnsTrue() {
        uniqueAppealList.add(TypicalAppeals.appeal1);
        assertTrue(uniqueAppealList.contains(TypicalAppeals.appeal1));
    }

    @Test
    public void add_nullAppeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueAppealList.add(null));
    }

    @Test
    public void add_duplicateAppeal_throwsDuplicateAppealException() {
        uniqueAppealList.add(TypicalAppeals.appeal1);
        Assert.assertThrows(DuplicateAppealException.class, () -> uniqueAppealList.add(TypicalAppeals.appeal1));
    }

    @Test
    public void remove_nullAppeal_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueAppealList.remove(null));
    }

    @Test
    public void remove_studentDoesNotExist_throwsStudentNotFoundException() {
        Assert.assertThrows(AppealNotFoundException.class, () -> uniqueAppealList.remove(TypicalAppeals.appeal1));
    }

    @Test
    public void remove_existingAppeal_removesAppeal() {
        uniqueAppealList.add(TypicalAppeals.appeal1);
        uniqueAppealList.remove(TypicalAppeals.appeal1);
        UniqueAppealList expectedUniqueAppealList = new UniqueAppealList();
        assertEquals(expectedUniqueAppealList, uniqueAppealList);
    }

}
