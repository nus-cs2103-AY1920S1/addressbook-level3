package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.expense.exceptions.DuplicateExpenseException;
import seedu.address.model.expense.exceptions.ExpenseNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniquePersonListTest {

    private final UniqueExpenseList uniqueExpenseList = new UniqueExpenseList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueExpenseList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueExpenseList.add(ALICE);
        assertTrue(uniqueExpenseList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueExpenseList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueExpenseList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueExpenseList.add(ALICE);
        assertThrows(DuplicateExpenseException.class, () -> uniqueExpenseList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(ExpenseNotFoundException.class, () -> uniqueExpenseList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueExpenseList.add(ALICE);
        uniqueExpenseList.setPerson(ALICE, ALICE);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(ALICE);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueExpenseList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueExpenseList.setPerson(ALICE, editedAlice);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(editedAlice);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueExpenseList.add(ALICE);
        uniqueExpenseList.setPerson(ALICE, BOB);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(BOB);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueExpenseList.add(ALICE);
        uniqueExpenseList.add(BOB);
        assertThrows(DuplicateExpenseException.class, () -> uniqueExpenseList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(ExpenseNotFoundException.class, () -> uniqueExpenseList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueExpenseList.add(ALICE);
        uniqueExpenseList.remove(ALICE);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.setPersons((UniqueExpenseList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueExpenseList.add(ALICE);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(BOB);
        uniqueExpenseList.setPersons(expectedUniqueExpenseList);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExpenseList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueExpenseList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniqueExpenseList.setPersons(personList);
        UniqueExpenseList expectedUniqueExpenseList = new UniqueExpenseList();
        expectedUniqueExpenseList.add(BOB);
        assertEquals(expectedUniqueExpenseList, uniqueExpenseList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateExpenseException.class, () -> uniqueExpenseList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueExpenseList.asUnmodifiableObservableList().remove(0));
    }
}
