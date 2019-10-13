package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static organice.logic.commands.CommandTestUtil.VALID_TYPE_BOB;
import static organice.testutil.TypicalPersons.ALICE;
import static organice.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import organice.testutil.DoctorBuilder;

public class DoctorTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        Doctor editedAlice;

        // different nric -> returns false
        editedAlice = new DoctorBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different phone -> returns true
        editedAlice = new DoctorBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name -> returns true
        editedAlice = new DoctorBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different nric, same attributes -> returns false
        editedAlice = new DoctorBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same nric, different attributes -> returns true
        editedAlice = new DoctorBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Doctor aliceCopy = new DoctorBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different doctor -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Doctor editedAlice = new DoctorBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new DoctorBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different nric -> returns false
        editedAlice = new DoctorBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different person type -> returns false
        editedAlice = (Doctor) new DoctorBuilder(ALICE).withType(VALID_TYPE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringTest() {
        Doctor alice = new DoctorBuilder(ALICE).build();
        assertEquals(alice.toString().trim() , "Alice Pauline Person Type: doctor Nric: S1532142A Phone: 94351253");
    }

    @Test
    public void hashCodeTest() {
        Doctor alice = new DoctorBuilder(ALICE).build();

        assertEquals(alice.hashCode(), new DoctorBuilder(ALICE).build().hashCode());
        assertNotEquals(alice.hashCode(),
                new DoctorBuilder(ALICE).withPhone(VALID_PHONE_BOB).build().hashCode());
    }
}
