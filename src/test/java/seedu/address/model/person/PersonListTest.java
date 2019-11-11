package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.CARL;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.USER;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;
import static seedu.address.testutil.scheduleutil.TypicalEvents.EVENT_NAME1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.NoPersonFieldsEditedException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.personutil.PersonBuilder;
import seedu.address.testutil.scheduleutil.TypicalEvents;

class PersonListTest {

    private PersonList personList;
    private Person zack;

    @BeforeEach
    void init() throws DuplicatePersonException {
        personList = new PersonList(new User(USER));
        personList.addPerson(ALICE);
        personList.addPerson(BENSON);
        zack = new PersonBuilder(ZACK).build();
    }

    @Test
    void editUser() {
        User user;
        try {
            user = personList.editUser(ZACK);
            assertTrue(user.isSamePerson(zack));
        } catch (NoPersonFieldsEditedException | DuplicatePersonException e) {
            e.printStackTrace();
        }
    }

    @Test
    void editUser_changeNameOnly() {
        User user;
        PersonDescriptor nameChangeOnly = new PersonDescriptor();
        nameChangeOnly.setName(ALICE.getName());
        try {
            user = personList.editUser(nameChangeOnly);
            assertFalse(user.isSamePerson(zack));
        } catch (NoPersonFieldsEditedException | DuplicatePersonException e) {
            e.printStackTrace();
        }
    }

    @Test
    void editUser_noFieldsEdited() {
        assertThrows(NoPersonFieldsEditedException.class, () -> personList.editUser(new PersonDescriptor()));
    }

    @Test
    void addPerson() throws DuplicatePersonException {
        PersonBuilder personBuilder = new PersonBuilder(CARL);
        Person person = personList.addPerson(CARL);
        assertTrue(person.isSamePerson(personBuilder.build()));
    }

    @Test
    void deletePerson() throws PersonNotFoundException {
        Person person = personList.findPerson(ALICE.getName());
        assertDoesNotThrow(() -> personList.deletePerson(person.getPersonId()));
        assertThrows(PersonNotFoundException.class, () -> personList.deletePerson(person.getPersonId()));
    }

    @Test
    void editPerson() throws PersonNotFoundException, NoPersonFieldsEditedException, DuplicatePersonException {

        PersonDescriptor personDescriptor = CARL;
        Person editedPerson = personList.editPerson(ALICE.getName(), personDescriptor);
        Person toCompare = new PersonBuilder(personDescriptor).build();

        assertTrue(editedPerson.isSamePerson(toCompare));
    }

    @Test
    void editPerson_duplicatePerson() {
        assertThrows(DuplicatePersonException.class, () -> personList.editPerson(ALICE.getName(), ALICE));
    }

    @Test
    void findPerson() throws PersonNotFoundException {
        Person person = personList.findPerson(ALICE.getName());
        Person alice = new PersonBuilder(ALICE).build();
        assertTrue(alice.isSamePerson(person));
    }

    @Test
    void testFindPerson() throws PersonNotFoundException {
        Person person = personList.findPerson(BENSON.getName());
        Person benson = new PersonBuilder(BENSON).build();
        assertTrue(benson.isSamePerson(person));
    }


    @Test
    void addEvent_success() {
        assertDoesNotThrow(() -> personList.addEvent(ALICE.getName(), TypicalEvents.generateTypicalEvent1()));
    }

    @Test
    void addEvent_personNotFound() {
        assertThrows(PersonNotFoundException.class, () ->
                personList.addEvent(CARL.getName(), TypicalEvents.generateTypicalEvent1()));
    }

    @Test
    void addEvent_eventClash() {
        assertDoesNotThrow(() ->
                personList.addEvent(ALICE.getName(), TypicalEvents.generateTypicalEvent1()));

        assertThrows(EventClashException.class, () ->
                personList.addEvent(ALICE.getName(), TypicalEvents.generateTypicalEvent1()));
    }

    @Test
    void deleteEvent_success() {
        assertDoesNotThrow(() -> personList.addEvent(ALICE.getName(), TypicalEvents.generateTypicalEvent1()));
        assertDoesNotThrow(() -> personList.deleteEvent(ALICE.getName(), EVENT_NAME1));
    }
}
