package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonDescriptorBuilder;

class PersonListTest {

    private PersonList personList;

    @BeforeEach
    void init() {
        personList = new PersonList();
        personList.addPerson(new PersonDescriptorBuilder(ALICE).build());
        personList.addPerson(new PersonDescriptorBuilder(BENSON).build());
    }

    @Test
    void addPerson() {
        PersonDescriptor personDescriptor = new PersonDescriptorBuilder(CARL).build();
        PersonBuilder personBuilder = new PersonBuilder(personDescriptor);
        Person person = personList.addPerson(personDescriptor);
        assertTrue(person.equals(personBuilder.build()));
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
        PersonDescriptor personDescriptor = new PersonDescriptorBuilder(CARL).build();

        Person editedPerson = personList.editPerson(person.getName(), personDescriptor);
        Person toCompare = new PersonBuilder(personDescriptor).build();

        assertTrue(editedPerson.equals(toCompare));

    }

    @Test
    void findPerson() {
        Person person = personList.findPerson(ALICE.getName());
        assertTrue(ALICE.equals(person));
    }

    @Test
    void testFindPerson() {
        Person person = personList.findPerson(BENSON.getName());
        assertTrue(BENSON.equals(person));
    }
}
