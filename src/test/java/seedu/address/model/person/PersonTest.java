package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.NEWTON;
import static seedu.address.testutil.TypicalPersons.NUS;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(NEWTON.isSamePerson(NEWTON));

        // null -> returns false
        assertFalse(NEWTON.isSamePerson(null));

        // different phone and email -> returns false
        Person editedAlice = new PersonBuilder(NEWTON).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(NEWTON.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(NEWTON).withQuestion(VALID_QUESTION_2).build();
        assertFalse(NEWTON.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(NEWTON).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(NEWTON.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(NEWTON).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(NEWTON.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(NEWTON).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(NEWTON.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(NEWTON).build();
        assertTrue(NEWTON.equals(aliceCopy));

        // same object -> returns true
        assertTrue(NEWTON.equals(NEWTON));

        // null -> returns false
        assertFalse(NEWTON.equals(null));

        // different type -> returns false
        assertFalse(NEWTON.equals(5));

        // different person -> returns false
        assertFalse(NEWTON.equals(NUS));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(NEWTON).withQuestion(VALID_QUESTION_2).build();
        assertFalse(NEWTON.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(NEWTON).withPhone(VALID_PHONE_BOB).build();
        assertFalse(NEWTON.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(NEWTON).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(NEWTON.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(NEWTON).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(NEWTON.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(NEWTON).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(NEWTON.equals(editedAlice));
    }
}
