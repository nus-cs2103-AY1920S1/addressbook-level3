package seedu.ichifund.model.repeater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalFundBook.REPEATER_PHONE_BILLS;
import static seedu.ichifund.testutil.TypicalFundBook.REPEATER_SALARY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ichifund.model.repeater.exceptions.DuplicateRepeaterException;
import seedu.ichifund.model.repeater.exceptions.RepeaterNotFoundException;
import seedu.ichifund.testutil.RepeaterBuilder;

public class UniqueRepeaterListTest {

    private final UniqueRepeaterList uniqueRepeaterList = new UniqueRepeaterList();

    @Test
    public void contains_nullRepeater_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRepeaterList.contains(null));
    }

    @Test
    public void contains_repeaterNotInList_returnsFalse() {
        assertFalse(uniqueRepeaterList.contains(REPEATER_PHONE_BILLS));
    }

    @Test
    public void contains_repeaterInList_returnsTrue() {
        uniqueRepeaterList.add(REPEATER_PHONE_BILLS);
        assertTrue(uniqueRepeaterList.contains(REPEATER_PHONE_BILLS));
    }

    @Test
    public void add_nullRepeater_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRepeaterList.add(null));
    }

    @Test
    public void add_duplicateRepeater_throwsDuplicateRepeaterException() {
        uniqueRepeaterList.add(REPEATER_PHONE_BILLS);
        assertThrows(DuplicateRepeaterException.class, () -> uniqueRepeaterList.add(REPEATER_PHONE_BILLS));
    }

    @Test
    public void setRepeater_nullTargetRepeater_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRepeaterList.setRepeater(null, REPEATER_PHONE_BILLS));
    }

    @Test
    public void setRepeater_nullEditedRepeater_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRepeaterList.setRepeater(REPEATER_PHONE_BILLS, null));
    }

    @Test
    public void setRepeater_targetRepeaterNotInList_throwsRepeaterNotFoundException() {
        assertThrows(RepeaterNotFoundException.class, () -> uniqueRepeaterList
                .setRepeater(REPEATER_PHONE_BILLS, REPEATER_PHONE_BILLS));
    }

    @Test
    public void setRepeater_editedRepeaterIsSameRepeater_success() {
        uniqueRepeaterList.add(REPEATER_PHONE_BILLS);
        uniqueRepeaterList.setRepeater(REPEATER_PHONE_BILLS, REPEATER_PHONE_BILLS);
        UniqueRepeaterList expectedUniqueRepeaterList = new UniqueRepeaterList();
        expectedUniqueRepeaterList.add(REPEATER_PHONE_BILLS);
        assertEquals(expectedUniqueRepeaterList, uniqueRepeaterList);
    }

    @Test
    public void setRepeater_editedRepeaterHasSameIdentity_success() {
        uniqueRepeaterList.add(REPEATER_PHONE_BILLS);
        Repeater editedAnime = new RepeaterBuilder(REPEATER_PHONE_BILLS)
                .withAmount(REPEATER_SALARY.getAmount().toString()).build();
        uniqueRepeaterList.setRepeater(REPEATER_PHONE_BILLS, editedAnime);
        UniqueRepeaterList expectedUniqueRepeaterList = new UniqueRepeaterList();
        expectedUniqueRepeaterList.add(editedAnime);
        assertEquals(expectedUniqueRepeaterList, uniqueRepeaterList);
    }

    @Test
    public void setRepeater_editedRepeaterHasDifferentIdentity_success() {
        uniqueRepeaterList.add(REPEATER_PHONE_BILLS);
        uniqueRepeaterList.setRepeater(REPEATER_PHONE_BILLS, REPEATER_SALARY);
        UniqueRepeaterList expectedUniqueRepeaterList = new UniqueRepeaterList();
        expectedUniqueRepeaterList.add(REPEATER_SALARY);
        assertEquals(expectedUniqueRepeaterList, uniqueRepeaterList);
    }

    @Test
    public void setRepeater_editedRepeaterHasNonUniqueIdentity_throwsDuplicateRepeaterException() {
        uniqueRepeaterList.add(REPEATER_PHONE_BILLS);
        uniqueRepeaterList.add(REPEATER_SALARY);
        assertThrows(DuplicateRepeaterException.class, () -> uniqueRepeaterList
                .setRepeater(REPEATER_PHONE_BILLS, REPEATER_SALARY));
    }

    @Test
    public void remove_nullRepeater_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRepeaterList.remove(null));
    }

    @Test
    public void remove_repeaterDoesNotExist_throwsRepeaterNotFoundException() {
        assertThrows(RepeaterNotFoundException.class, () -> uniqueRepeaterList.remove(REPEATER_PHONE_BILLS));
    }

    @Test
    public void remove_existingRepeater_removesRepeater() {
        uniqueRepeaterList.add(REPEATER_PHONE_BILLS);
        uniqueRepeaterList.remove(REPEATER_PHONE_BILLS);
        UniqueRepeaterList expectedUniqueRepeaterList = new UniqueRepeaterList();
        assertEquals(expectedUniqueRepeaterList, uniqueRepeaterList);
    }

    @Test
    public void setRepeaters_nullUniqueRepeaterList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRepeaterList.setRepeaters((UniqueRepeaterList) null));
    }

    @Test
    public void setRepeaters_uniqueRepeaterList_replacesOwnListWithProvidedUniqueRepeaterList() {
        uniqueRepeaterList.add(REPEATER_PHONE_BILLS);
        UniqueRepeaterList expectedUniqueRepeaterList = new UniqueRepeaterList();
        expectedUniqueRepeaterList.add(REPEATER_SALARY);
        uniqueRepeaterList.setRepeaters(expectedUniqueRepeaterList);
        assertEquals(expectedUniqueRepeaterList, uniqueRepeaterList);
    }

    @Test
    public void setRepeaters_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRepeaterList.setRepeaters((List<Repeater>) null));
    }

    @Test
    public void setRepeaters_list_replacesOwnListWithProvidedList() {
        uniqueRepeaterList.add(REPEATER_PHONE_BILLS);
        List<Repeater> repeaterList = Collections.singletonList(REPEATER_SALARY);
        uniqueRepeaterList.setRepeaters(repeaterList);
        UniqueRepeaterList expectedUniqueRepeaterList = new UniqueRepeaterList();
        expectedUniqueRepeaterList.add(REPEATER_SALARY);
        assertEquals(expectedUniqueRepeaterList, uniqueRepeaterList);
    }

    @Test
    public void setRepeaters_listWithDuplicateRepeaters_throwsDuplicateRepeaterException() {
        List<Repeater> listWithDuplicateRepeaters = Arrays.asList(REPEATER_PHONE_BILLS, REPEATER_PHONE_BILLS);
        assertThrows(DuplicateRepeaterException.class, () -> uniqueRepeaterList
                .setRepeaters(listWithDuplicateRepeaters));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueRepeaterList.asUnmodifiableObservableList().remove(0));
    }
}
