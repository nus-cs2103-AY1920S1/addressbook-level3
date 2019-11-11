package seedu.address.model.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.employee.exceptions.DuplicateEmployeeException;
import seedu.address.model.employee.exceptions.EmployeeNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueEmployeeListTest {

    private final UniqueEmployeeList uniqueEmployeeList = new UniqueEmployeeList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEmployeeList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueEmployeeList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueEmployeeList.add(ALICE);
        assertTrue(uniqueEmployeeList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEmployeeList.add(ALICE);
        Employee editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueEmployeeList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEmployeeList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueEmployeeList.add(ALICE);
        assertThrows(DuplicateEmployeeException.class, () -> uniqueEmployeeList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEmployeeList.setEmployee(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEmployeeList.setEmployee(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(EmployeeNotFoundException.class, () -> uniqueEmployeeList.setEmployee(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueEmployeeList.add(ALICE);
        uniqueEmployeeList.setEmployee(ALICE, ALICE);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        expectedUniqueEmployeeList.add(ALICE);
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueEmployeeList.add(ALICE);
        Employee editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueEmployeeList.setEmployee(ALICE, editedAlice);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        expectedUniqueEmployeeList.add(editedAlice);
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueEmployeeList.add(ALICE);
        uniqueEmployeeList.setEmployee(ALICE, BOB);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        expectedUniqueEmployeeList.add(BOB);
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueEmployeeList.add(ALICE);
        uniqueEmployeeList.add(BOB);
        assertThrows(DuplicateEmployeeException.class, () -> uniqueEmployeeList.setEmployee(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEmployeeList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(EmployeeNotFoundException.class, () -> uniqueEmployeeList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueEmployeeList.add(ALICE);
        uniqueEmployeeList.remove(ALICE);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEmployeeList.setEmployees((UniqueEmployeeList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueEmployeeList.add(ALICE);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        expectedUniqueEmployeeList.add(BOB);
        uniqueEmployeeList.setEmployees(expectedUniqueEmployeeList);
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEmployeeList.setEmployees((List<Employee>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueEmployeeList.add(ALICE);
        List<Employee> employeeList = Collections.singletonList(BOB);
        uniqueEmployeeList.setEmployees(employeeList);
        UniqueEmployeeList expectedUniqueEmployeeList = new UniqueEmployeeList();
        expectedUniqueEmployeeList.add(BOB);
        assertEquals(expectedUniqueEmployeeList, uniqueEmployeeList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Employee> listWithDuplicateEmployees = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateEmployeeException.class, () ->
                uniqueEmployeeList.setEmployees(listWithDuplicateEmployees));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueEmployeeList.asUnmodifiableObservableList().remove(0));
    }
}
