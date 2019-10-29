package seedu.ichifund.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalFundBook.PERSON_ALICE;
import static seedu.ichifund.testutil.TypicalFundBook.PERSON_BOB;

import org.junit.jupiter.api.Test;

import seedu.ichifund.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(PERSON_ALICE.isSamePerson(PERSON_ALICE));

        // null -> returns false
        assertFalse(PERSON_ALICE.isSamePerson(null));

        // different phone and email -> returns false
        Person editedAlice = new PersonBuilder(PERSON_ALICE).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();
        assertFalse(PERSON_ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(PERSON_ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(PERSON_ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(PERSON_ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(PERSON_ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(PERSON_ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(PERSON_ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(PERSON_ALICE).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(PERSON_ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(PERSON_ALICE).build();
        assertTrue(PERSON_ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(PERSON_ALICE.equals(PERSON_ALICE));

        // null -> returns false
        assertFalse(PERSON_ALICE.equals(null));

        // different type -> returns false
        assertFalse(PERSON_ALICE.equals(5));

        // different person -> returns false
        assertFalse(PERSON_ALICE.equals(PERSON_BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(PERSON_ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(PERSON_ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(PERSON_ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(PERSON_ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(PERSON_ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(PERSON_ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(PERSON_ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(PERSON_ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(PERSON_ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(PERSON_ALICE.equals(editedAlice));
    }
}
