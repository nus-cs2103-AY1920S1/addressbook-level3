package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Schedule;
import seedu.address.testutil.personutil.PersonBuilder;
import seedu.address.testutil.personutil.PersonDescriptorBuilder;
import seedu.address.testutil.personutil.PersonEditor;
import seedu.address.testutil.scheduleutil.TypicalEvents;
import seedu.address.testutil.scheduleutil.TypicalSchedule;

public class PersonTest {

    private Person alice;
    private Person benson;

    @BeforeEach
    void init() {
        alice = new PersonBuilder(ALICE).build();
        benson = new PersonBuilder(BENSON).build();
    }


    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void equals_same_person() {
        // same object -> returns true
        assertTrue(alice.equals(alice));

        // null -> returns false
        assertFalse(alice.equals(null));

        // different person -> return false
        assertFalse(alice.equals(benson));
    }

    @Test
    public void equals_edited_person() {
        // edited same person -> returns true
        Person editedAlice = new PersonEditor(alice).edit(
                new PersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build());
        assertTrue(alice.equals(editedAlice));

        // different name -> returns true
        editedAlice = new PersonEditor(alice).edit(
                new PersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
        assertTrue(alice.equals(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonEditor(alice).edit(
                new PersonDescriptorBuilder().withEmail(VALID_EMAIL_BOB)
                        .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build());
        assertTrue(alice.equals(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonEditor(alice).edit(
                new PersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                        .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build());
        assertTrue(alice.equals(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonEditor(alice).edit(
                new PersonDescriptorBuilder().withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build());
        assertTrue(alice.equals(editedAlice));
    }

    @Test
    public void isSamePerson_same_object() {
        // same object -> returns true
        assertTrue(alice.isSamePerson(alice));

        // different person -> returns false
        assertFalse(alice.isSamePerson(benson));
    }

    @Test
    public void isSamePerson_same_values() {
        // same values -> returns true
        Person otherPerson = new PersonBuilder(ALICE).build();
        assertTrue(alice.isSamePerson(otherPerson));

        // different values -> return false
        otherPerson.setName(benson.getName());
        assertFalse(alice.isSamePerson(otherPerson));
    }

    @Test
    public void isSamePerson_edited_person() {
        // different phone and email but same object -> return true
        Person editedAlice = new PersonEditor(new PersonBuilder(ALICE).build()).edit(
                new PersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build());
        assertFalse(alice.isSamePerson(editedAlice));
    }

    @Test
    void addEvent() {
        alice.addEvent(TypicalEvents.generateTypicalEvent1());
        Schedule schedule = alice.getSchedule();
        assertNotNull(schedule);
        Event event = schedule.getEvents().get(0);
        assertNotNull(event);
        assertTrue(event.equals(TypicalEvents.generateTypicalEvent1()));
    }

    @Test
    void getPersonId() {
        assertNotNull(alice.getPersonId());
    }

    @Test
    void getName() {
        assertNotNull(alice.getName());
        assertTrue(ALICE.getName().equals(alice.getName()));
    }

    @Test
    void setName() {
        alice.setName(BENSON.getName());
        assertFalse(alice.getName().equals(ALICE.getName()));
        assertTrue(alice.getName().equals(BENSON.getName()));
    }

    @Test
    void getPhone() {
        assertNotNull(alice.getPhone());
        assertTrue(alice.getPhone().equals(ALICE.getPhone()));
    }

    @Test
    void setPhone() {
        alice.setPhone(BENSON.getPhone());
        assertFalse(alice.getPhone().equals(ALICE.getPhone()));
        assertTrue(alice.getPhone().equals(BENSON.getPhone()));
    }

    @Test
    void getEmail() {
        assertNotNull(alice.getEmail());
        assertTrue(alice.getEmail().equals(ALICE.getEmail()));
    }

    @Test
    void setEmail() {
        alice.setEmail(BENSON.getEmail());
        assertFalse(alice.getEmail().equals(ALICE.getEmail()));
        assertTrue(alice.getEmail().equals(BENSON.getEmail()));
    }

    @Test
    void getAddress() {
        assertNotNull(alice.getAddress());
        assertTrue(alice.getAddress().equals(ALICE.getAddress()));
    }

    @Test
    void setAddress() {
        alice.setAddress(BENSON.getAddress());
        assertFalse(alice.getAddress().equals(ALICE.getAddress()));
        assertTrue(alice.getAddress().equals(BENSON.getAddress()));
    }

    @Test
    void getRemark() {
        assertNotNull(alice.getRemark());
        assertTrue(alice.getRemark().equals(ALICE.getRemark()));
    }

    @Test
    void setRemark() {
        alice.setRemark(BENSON.getRemark());
        assertFalse(alice.getRemark().equals(ALICE.getRemark()));
        assertTrue(alice.getRemark().equals(BENSON.getRemark()));
    }

    @Test
    void setSchedule() {
        Schedule schedule = TypicalSchedule.generateTypicalSchedule(alice.getPersonId());
        alice.setSchedule(schedule);
        assertTrue(alice.getSchedule().getPersonId().equals(alice.getPersonId()));
        assertNotNull(alice.getSchedule());
    }

    @Test
    void getSchedule() {
        Schedule schedule = TypicalSchedule.generateEmptySchedule(benson.getPersonId());
        benson.setSchedule(schedule);
        assertTrue(benson.getSchedule().getPersonId().equals(benson.getPersonId()));
        assertNotNull(benson.getSchedule());
    }
}
