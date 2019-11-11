package seedu.billboard.model.recurrence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalRecurrences.RECUR_BILLS;
import static seedu.billboard.testutil.TypicalRecurrences.RECUR_TAXES;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.model.recurrence.exceptions.DuplicateRecurrenceException;
import seedu.billboard.model.recurrence.exceptions.RecurrenceNotFoundException;
import seedu.billboard.testutil.RecurrenceBuilder;

public class RecurrenceListTest {

    private final RecurrenceList recurrenceList = new RecurrenceList();

    @Test
    public void contains_nullRecurrence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recurrenceList.contains(null));
    }

    @Test
    public void contains_recurrenceNotInList_returnsFalse() {
        assertFalse(recurrenceList.contains(RECUR_BILLS));
    }

    @Test
    public void contains_recurrenceInList_returnsTrue() {
        recurrenceList.add(RECUR_BILLS);
        assertTrue(recurrenceList.contains(RECUR_BILLS));
    }

    @Test
    public void add_nullRecurrence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recurrenceList.add(null));
    }

    @Test
    public void add_duplicateRecurrence_throwsDuplicateRecurrenceException() {
        recurrenceList.add(RECUR_BILLS);
        assertThrows(DuplicateRecurrenceException.class, () -> recurrenceList.add(RECUR_BILLS));
    }

    @Test
    public void setRecurrence_nullTargetRecurrence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recurrenceList.setRecurrence(null, RECUR_BILLS));
    }

    @Test
    public void setRecurrence_nullEditedRecurrence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recurrenceList.setRecurrence(RECUR_BILLS, null));
    }

    @Test
    public void setRecurrence_targetRecurrenceNotInList_throwsRecurrenceNotFoundException() {
        assertThrows(RecurrenceNotFoundException.class, () -> recurrenceList.setRecurrence(RECUR_BILLS, RECUR_BILLS));
    }

    @Test
    public void setRecurrence_editedRecurrenceIsSameRecurrence_success() {
        recurrenceList.add(RECUR_BILLS);
        recurrenceList.setRecurrence(RECUR_BILLS, RECUR_BILLS);
        RecurrenceList expectedRecurrenceList = new RecurrenceList();
        expectedRecurrenceList.add(RECUR_BILLS);
        assertEquals(expectedRecurrenceList, recurrenceList);
    }

    @Test
    public void setRecurrence_editedRecurrenceHasSameIdentity_success() {
        recurrenceList.add(RECUR_BILLS);
        Recurrence editedAlice = new RecurrenceBuilder(RECUR_BILLS).withAmount(VALID_AMOUNT_TAXES)
                .withTag(VALID_TAG_DINNER).build();
        recurrenceList.setRecurrence(RECUR_BILLS, editedAlice);
        RecurrenceList expectedRecurrenceList = new RecurrenceList();
        expectedRecurrenceList.add(editedAlice);
        assertEquals(expectedRecurrenceList, recurrenceList);
    }

    @Test
    public void setRecurrence_editedRecurrenceHasDifferentIdentity_success() {
        recurrenceList.add(RECUR_BILLS);
        recurrenceList.setRecurrence(RECUR_BILLS, RECUR_TAXES);
        RecurrenceList expectedRecurrenceList = new RecurrenceList();
        expectedRecurrenceList.add(RECUR_TAXES);
        assertEquals(expectedRecurrenceList, recurrenceList);
    }

    @Test
    public void setRecurrence_editedRecurrenceHasNonUniqueIdentity_throwsDuplicateRecurrenceException() {
        recurrenceList.add(RECUR_BILLS);
        recurrenceList.add(RECUR_TAXES);
        assertThrows(DuplicateRecurrenceException.class, () -> recurrenceList.setRecurrence(RECUR_BILLS, RECUR_TAXES));
    }

    @Test
    public void remove_nullRecurrence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recurrenceList.remove(null));
    }

    @Test
    public void remove_recurrenceDoesNotExist_throwsRecurrenceNotFoundException() {
        assertThrows(RecurrenceNotFoundException.class, () -> recurrenceList.remove(RECUR_BILLS));
    }

    @Test
    public void remove_existingRecurrence_removesRecurrence() {
        recurrenceList.add(RECUR_BILLS);
        recurrenceList.remove(RECUR_BILLS);
        RecurrenceList expectedRecurrenceList = new RecurrenceList();
        assertEquals(expectedRecurrenceList, recurrenceList);
    }

    @Test
    public void setRecurrences_nullUniqueRecurrenceList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recurrenceList.setRecurrences((RecurrenceList) null));
    }

    @Test
    public void setRecurrences_uniqueRecurrenceList_replacesOwnListWithProvidedUniqueRecurrenceList() {
        recurrenceList.add(RECUR_BILLS);
        RecurrenceList expectedRecurrenceList = new RecurrenceList();
        expectedRecurrenceList.add(RECUR_TAXES);
        recurrenceList.setRecurrences(expectedRecurrenceList);
        assertEquals(expectedRecurrenceList, recurrenceList);
    }

    @Test
    public void setRecurrences_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recurrenceList.setRecurrences((List<Recurrence>) null));
    }

    @Test
    public void setRecurrences_list_replacesOwnListWithProvidedList() {
        recurrenceList.add(RECUR_BILLS);
        List<Recurrence> recurrenceList = Collections.singletonList(RECUR_TAXES);
        this.recurrenceList.setRecurrences(recurrenceList);
        RecurrenceList expectedRecurrenceList = new RecurrenceList();
        expectedRecurrenceList.add(RECUR_TAXES);
        assertEquals(expectedRecurrenceList, this.recurrenceList);
    }

    @Test
    public void setRecurrences_listWithDuplicateRecurrences_throwsDuplicateRecurrenceException() {
        List<Recurrence> listWithDuplicateRecurrences = Arrays.asList(RECUR_BILLS, RECUR_BILLS);
        assertThrows(DuplicateRecurrenceException.class, () ->
                recurrenceList.setRecurrences(listWithDuplicateRecurrences));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> recurrenceList.asUnmodifiableList().remove(0));
    }
}
