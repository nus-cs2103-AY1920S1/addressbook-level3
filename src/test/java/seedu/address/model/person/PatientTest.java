package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.IRENE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PersonBuilder;

public class PatientTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(IRENE.isSamePerson(IRENE));

        // null -> returns false
        assertFalse(IRENE.isSamePerson(null));

        Patient editedIrene = new PatientBuilder(IRENE).build();

        // different nric -> returns false
        editedIrene = new PatientBuilder(IRENE).withNric(VALID_NRIC_BOB).build();
        assertFalse(IRENE.isSamePerson(editedIrene));

        // different phone -> returns true
        editedIrene = new PatientBuilder(IRENE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(IRENE.isSamePerson(editedIrene));

        // different name -> returns true
        editedIrene = new PatientBuilder(IRENE).withName(VALID_NAME_BOB).build();
        assertTrue(IRENE.isSamePerson(editedIrene));

        // different nric, same attributes -> returns false
        editedIrene = new PatientBuilder(IRENE).withNric(VALID_NRIC_BOB).build();
        assertFalse(IRENE.isSamePerson(editedIrene));

        // same nric, different attributes -> returns false
        editedIrene = new PatientBuilder(IRENE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        assertTrue(IRENE.isSamePerson(editedIrene));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Patient ireneCopy = new PatientBuilder(IRENE).build();
        assertTrue(IRENE.equals(ireneCopy));

        // same object -> returns true
        assertTrue(IRENE.equals(IRENE));

        // null -> returns false
        assertFalse(IRENE.equals(null));

        // different type -> returns false
        assertFalse(IRENE.equals(5));

        // different person -> returns false
        assertFalse(IRENE.equals(BOB));

        // different name -> returns false
        Patient editedIrene = new PatientBuilder(IRENE).withName(VALID_NAME_BOB).build();
        assertFalse(IRENE.equals(editedIrene));

        // different phone -> returns false
        editedIrene = new PatientBuilder(IRENE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(IRENE.equals(editedIrene));

        // different nric -> returns false
        editedIrene = new PatientBuilder(IRENE).withNric(VALID_NRIC_BOB).build();
        assertFalse(IRENE.equals(editedIrene));

        // different person type -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withType("doctor").build();
        assertFalse(editedAlice.equals(editedIrene));
    }
}
