package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYMENT_STATUS_XENIA;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalWorkers.XENIA;
import static seedu.address.testutil.TypicalWorkers.ZACH;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.exceptions.DuplicateEntityException;
import seedu.address.model.entity.exceptions.EntityNotFoundException;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.WorkerBuilder;

public class UniqueEntityListsTest {

    private final UniqueEntityLists uniqueEntityLists = new UniqueEntityLists();

    @Test
    public void contains_nullEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.contains(null));
    }

    @Test
    public void contains_entityNotInList_returnsFalse() {
        assertFalse(uniqueEntityLists.contains(ZACH));
    }

    @Test
    public void contains_entityInList_returnsTrue() {
        uniqueEntityLists.add(ZACH);
        assertTrue(uniqueEntityLists.contains(ZACH));
    }

    @Test
    public void contains_entityWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEntityLists.add(ZACH);
        Worker editedZach = new WorkerBuilder(ZACH).withEmploymentStatus(VALID_EMPLOYMENT_STATUS_XENIA).build();
        assertTrue(uniqueEntityLists.contains(editedZach));
    }

    @Test
    public void add_nullEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.add(null));
    }

    @Test
    public void add_duplicateEntity_throwsDuplicatePersonException() {
        uniqueEntityLists.add(ZACH);
        assertThrows(DuplicateEntityException.class, () -> uniqueEntityLists.add(ZACH));
    }

    @Test
    public void setEntity_nullTargetEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.setEntity(null, ZACH));
    }

    @Test
    public void setEntity_nullEditedEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.setEntity(ZACH, null));
    }

    @Test
    public void setEntity_targetEntityNotInList_throwsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> uniqueEntityLists.setEntity(ZACH, ZACH));
    }

    @Test
    public void setEntity_editedEntityIsSameEntity_success() {
        uniqueEntityLists.add(ZACH);
        uniqueEntityLists.setEntity(ZACH, ZACH);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        expectedUniqueEntityList.add(ZACH);
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setEntity_editedPersonHasSameIdentity_success() {
        uniqueEntityLists.add(ZACH);
        Worker editedZach = new WorkerBuilder(ZACH).withEmploymentStatus(VALID_EMPLOYMENT_STATUS_XENIA).build();
        uniqueEntityLists.setEntity(ZACH, editedZach);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        expectedUniqueEntityList.add(editedZach);
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setEntity_editedEntityHasDifferentIdentity_success() {
        uniqueEntityLists.add(ZACH);
        uniqueEntityLists.setEntity(ZACH, XENIA);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        expectedUniqueEntityList.add(XENIA);
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setEntity_editedEntityHasNonUniqueIdentity_throwsDuplicateEntityException() {
        uniqueEntityLists.add(ZACH);
        uniqueEntityLists.add(XENIA);
        assertThrows(DuplicateEntityException.class, () -> uniqueEntityLists.setEntity(ZACH, XENIA));
    }

    @Test
    public void remove_nullEntity_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.remove(null));
    }

    @Test
    public void remove_entityDoesNotExist_throwsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> uniqueEntityLists.remove(ZACH));
    }

    @Test
    public void remove_existingEntity_removesEntity() {
        uniqueEntityLists.add(ZACH);
        uniqueEntityLists.remove(ZACH);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.setPersons((UniqueEntityLists) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueEntityLists.add(ALICE);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        expectedUniqueEntityList.add(BOB);
        uniqueEntityLists.setPersons(expectedUniqueEntityList);
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEntityLists.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueEntityLists.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniqueEntityLists.setPersons(personList);
        UniqueEntityLists expectedUniqueEntityList = new UniqueEntityLists();
        expectedUniqueEntityList.add(BOB);
        assertEquals(expectedUniqueEntityList, uniqueEntityLists);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueEntityLists.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableListWorker_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueEntityLists.asUnmodifiableObservableListWorker().remove(0));
    }

    @Test
    public void asUnmodifiableObservableListBody_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueEntityLists.asUnmodifiableObservableListBody().remove(0));
    }

    @Test
    public void asUnmodifiableObservableListFridge_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueEntityLists.asUnmodifiableObservableListFridge().remove(0));
    }
}
