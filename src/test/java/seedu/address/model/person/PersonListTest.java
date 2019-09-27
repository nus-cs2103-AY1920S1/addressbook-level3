package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.CARL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.personutil.PersonBuilder;

class PersonListTest {

    private PersonList personList;

    @BeforeEach
    void init() {
        personList = new PersonList();
        personList.addPerson(ALICE);
        personList.addPerson(BENSON);
    }

    @Test
    void addPerson() {
        PersonBuilder personBuilder = new PersonBuilder(CARL);
        Person person = personList.addPerson(CARL);
        assertTrue(person.isSamePerson(personBuilder.build()));
    }

    @Test
    void deletePerson() {
        Person person = personList.findPerson(ALICE.getName());
        assertTrue(personList.deletePerson(person.getPersonId()));
        assertFalse(personList.deletePerson(person.getPersonId()));
    }

    @Test
    void editPerson() {
        Person person = personList.findPerson(ALICE.getName());
        PersonDescriptor personDescriptor = CARL;

        Person editedPerson = personList.editPerson(person.getName(), personDescriptor);
        Person toCompare = new PersonBuilder(personDescriptor).build();

        assertTrue(editedPerson.isSamePerson(toCompare));

    }

    @Test
    void findPerson() {
        Person person = personList.findPerson(ALICE.getName());
        Person alice = new PersonBuilder(ALICE).build();
        assertTrue(alice.isSamePerson(person));
    }

    @Test
    void testFindPerson() {
        Person person = personList.findPerson(BENSON.getName());
        Person benson = new PersonBuilder(BENSON).build();
        assertTrue(benson.isSamePerson(person));
    }
}
