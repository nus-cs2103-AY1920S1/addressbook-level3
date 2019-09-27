package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.STORE_AND_FORWARD;
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
        assertTrue(STORE_AND_FORWARD.isSamePerson(STORE_AND_FORWARD));

        // null -> returns false
        assertFalse(STORE_AND_FORWARD.isSamePerson(null));

        // different answer and email -> returns false
        Person editedAlice = new PersonBuilder(STORE_AND_FORWARD).withAnswer(VALID_ANSWER_2).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(STORE_AND_FORWARD.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withQuestion(VALID_QUESTION_2).build();
        assertFalse(STORE_AND_FORWARD.isSamePerson(editedAlice));

        // same name, same answer, different attributes -> returns true
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(STORE_AND_FORWARD.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withAnswer(VALID_ANSWER_2).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(STORE_AND_FORWARD.isSamePerson(editedAlice));

        // same name, same answer, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(STORE_AND_FORWARD.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(STORE_AND_FORWARD).build();
        assertTrue(STORE_AND_FORWARD.equals(aliceCopy));

        // same object -> returns true
        assertTrue(STORE_AND_FORWARD.equals(STORE_AND_FORWARD));

        // null -> returns false
        assertFalse(STORE_AND_FORWARD.equals(null));

        // different type -> returns false
        assertFalse(STORE_AND_FORWARD.equals(5));

        // different person -> returns false
        assertFalse(STORE_AND_FORWARD.equals(NUS));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(STORE_AND_FORWARD).withQuestion(VALID_QUESTION_2).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));

        // different answer -> returns false
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withAnswer(VALID_ANSWER_2).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(STORE_AND_FORWARD).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(STORE_AND_FORWARD.equals(editedAlice));
    }
}
