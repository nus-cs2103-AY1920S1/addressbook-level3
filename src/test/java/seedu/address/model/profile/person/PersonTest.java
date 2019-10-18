package seedu.address.model.profile.person;

import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_DENGUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.profile.TypicalProfiles.ALICE;
import static seedu.address.testutil.profile.TypicalProfiles.BOB;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.profile.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getMedicalHistories().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        Assertions.assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        Assertions.assertFalse(ALICE.isSamePerson(null));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        Assertions.assertFalse(ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE)
                .withMedicalHistories(VALID_HISTORY_DENGUE).build();
        Assertions.assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        Assertions.assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        Assertions.assertFalse(ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(ALICE.equals(5));

        // different person -> returns false
        Assertions.assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        Assertions.assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withMedicalHistories(VALID_HISTORY_DENGUE).build();
        Assertions.assertFalse(ALICE.equals(editedAlice));
    }
}
