package seedu.billboard.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalPersons.BILLS;
import static seedu.billboard.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.model.person.exceptions.DuplicatePersonException;
import seedu.billboard.model.person.exceptions.PersonNotFoundException;
import seedu.billboard.testutil.ExpenseBuilder;

public class UniqueExpenseListTest {

    private final RecordList recordList = new RecordList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(recordList.contains(BILLS));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        recordList.add(BILLS);
        assertTrue(recordList.contains(BILLS));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        recordList.add(BILLS);
        Expense editedAlice = new ExpenseBuilder(BILLS).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(recordList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        recordList.add(BILLS);
        assertThrows(DuplicatePersonException.class, () -> recordList.add(BILLS));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.setPerson(null, BILLS));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.setPerson(BILLS, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> recordList.setPerson(BILLS, BILLS));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        recordList.add(BILLS);
        recordList.setPerson(BILLS, BILLS);
        RecordList expectedRecordList = new RecordList();
        expectedRecordList.add(BILLS);
        assertEquals(expectedRecordList, recordList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        recordList.add(BILLS);
        Expense editedAlice = new ExpenseBuilder(BILLS).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        recordList.setPerson(BILLS, editedAlice);
        RecordList expectedRecordList = new RecordList();
        expectedRecordList.add(editedAlice);
        assertEquals(expectedRecordList, recordList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        recordList.add(BILLS);
        recordList.setPerson(BILLS, BOB);
        RecordList expectedRecordList = new RecordList();
        expectedRecordList.add(BOB);
        assertEquals(expectedRecordList, recordList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        recordList.add(BILLS);
        recordList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> recordList.setPerson(BILLS, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> recordList.remove(BILLS));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        recordList.add(BILLS);
        recordList.remove(BILLS);
        RecordList expectedRecordList = new RecordList();
        assertEquals(expectedRecordList, recordList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.setPersons((RecordList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        recordList.add(BILLS);
        RecordList expectedRecordList = new RecordList();
        expectedRecordList.add(BOB);
        recordList.setPersons(expectedRecordList);
        assertEquals(expectedRecordList, recordList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> recordList.setPersons((List<Expense>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        recordList.add(BILLS);
        List<Expense> expenseList = Collections.singletonList(BOB);
        recordList.setPersons(expenseList);
        RecordList expectedRecordList = new RecordList();
        expectedRecordList.add(BOB);
        assertEquals(expectedRecordList, recordList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Expense> listWithDuplicateExpenses = Arrays.asList(BILLS, BILLS);
        assertThrows(DuplicatePersonException.class, () -> recordList.setPersons(listWithDuplicateExpenses));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> recordList.asUnmodifiableObservableList().remove(0));
    }
}
