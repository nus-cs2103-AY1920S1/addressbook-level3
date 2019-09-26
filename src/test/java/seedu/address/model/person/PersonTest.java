package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonDescriptorBuilder;
import seedu.address.testutil.PersonEditor;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // different phone and email -> returns true
        Person editedAlice = new PersonEditor(ALICE).edit(
                new PersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build());
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name -> returns true
        editedAlice = new PersonEditor(ALICE).edit(
                new PersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonEditor(ALICE).edit(
                new PersonDescriptorBuilder().withEmail(VALID_EMAIL_BOB)
                        .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build());
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonEditor(ALICE).edit(
                new PersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                        .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build());
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonEditor(ALICE).edit(
                new PersonDescriptorBuilder().withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build());
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        assertTrue(ALICE.equals(ALICE));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different phone and email -> returns true
        Person editedAlice = new PersonEditor(ALICE).edit(
                new PersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build());
        assertTrue(ALICE.equals(editedAlice));

        PersonDescriptor personDescriptor = new PersonDescriptorBuilder(ALICE).build();
        Person otherPerson = new PersonBuilder(personDescriptor).build();
        assertTrue(ALICE.equals(otherPerson));

        otherPerson.setName(CARL.getName());
        assertFalse(ALICE.equals(otherPerson));

    }
}
