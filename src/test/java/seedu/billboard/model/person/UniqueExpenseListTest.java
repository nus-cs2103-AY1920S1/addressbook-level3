package seedu.billboard.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalPersons.BILLS;
import static seedu.billboard.testutil.TypicalPersons.TAXES;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.model.person.exceptions.DuplicatePersonException;
import seedu.billboard.model.person.exceptions.PersonNotFoundException;
import seedu.billboard.testutil.ExpenseBuilder;

public class UniqueExpenseListTest {

    private final ExpenseList expenseList = new ExpenseList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(expenseList.contains(BILLS));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        expenseList.add(BILLS);
        assertTrue(expenseList.contains(BILLS));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        expenseList.add(BILLS);
        Expense editedAlice = new ExpenseBuilder(BILLS).withAmount(VALID_AMOUNT_TAXES).withTags(VALID_TAG_DINNER)
                .build();
        assertTrue(expenseList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        expenseList.add(BILLS);
        assertThrows(DuplicatePersonException.class, () -> expenseList.add(BILLS));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.setPerson(null, BILLS));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.setPerson(BILLS, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> expenseList.setPerson(BILLS, BILLS));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        expenseList.add(BILLS);
        expenseList.setPerson(BILLS, BILLS);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(BILLS);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        expenseList.add(BILLS);
        Expense editedAlice = new ExpenseBuilder(BILLS).withAmount(VALID_AMOUNT_TAXES).withTags(VALID_TAG_DINNER)
                .build();
        expenseList.setPerson(BILLS, editedAlice);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(editedAlice);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        expenseList.add(BILLS);
        expenseList.setPerson(BILLS, TAXES);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(TAXES);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        expenseList.add(BILLS);
        expenseList.add(TAXES);
        assertThrows(DuplicatePersonException.class, () -> expenseList.setPerson(BILLS, TAXES));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> expenseList.remove(BILLS));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        expenseList.add(BILLS);
        expenseList.remove(BILLS);
        ExpenseList expectedExpenseList = new ExpenseList();
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.setPersons((ExpenseList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        expenseList.add(BILLS);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(TAXES);
        expenseList.setPersons(expectedExpenseList);
        assertEquals(expectedExpenseList, expenseList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenseList.setPersons((List<Expense>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        expenseList.add(BILLS);
        List<Expense> expenseList = Collections.singletonList(TAXES);
        this.expenseList.setPersons(expenseList);
        ExpenseList expectedExpenseList = new ExpenseList();
        expectedExpenseList.add(TAXES);
        assertEquals(expectedExpenseList, this.expenseList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Expense> listWithDuplicateExpenses = Arrays.asList(BILLS, BILLS);
        assertThrows(DuplicatePersonException.class, () -> expenseList.setPersons(listWithDuplicateExpenses));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> expenseList.asUnmodifiableObservableList().remove(0));
    }
}
