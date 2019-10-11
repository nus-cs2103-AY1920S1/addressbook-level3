package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.JOHN;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DonorBuilder;
import seedu.address.testutil.PersonBuilder;

public class DonorTest {

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(JOHN.isSamePerson(JOHN));

        // null -> returns false
        assertFalse(JOHN.isSamePerson(null));

        Donor editedJohn = new DonorBuilder(JOHN).build();

        // different nric -> returns false
        editedJohn = new DonorBuilder(JOHN).withNric(VALID_NRIC_BOB).build();
        assertFalse(JOHN.isSamePerson(editedJohn));

        // different phone -> returns true
        editedJohn = new DonorBuilder(JOHN).withPhone(VALID_PHONE_BOB).build();
        assertTrue(JOHN.isSamePerson(editedJohn));

        // different name -> returns true
        editedJohn = new DonorBuilder(JOHN).withName(VALID_NAME_BOB).build();
        assertTrue(JOHN.isSamePerson(editedJohn));

        // different nric, different other attributes -> returns false
        editedJohn = new DonorBuilder(JOHN).withNric(VALID_NRIC_BOB).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        assertFalse(JOHN.isSamePerson(editedJohn));

        // same nric, different other attributes -> returns true
        editedJohn = new DonorBuilder(JOHN).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        assertTrue(JOHN.isSamePerson(editedJohn));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Donor johnCopy = new DonorBuilder(JOHN).build();
        assertTrue(JOHN.equals(johnCopy));

        // same object -> returns true
        assertTrue(JOHN.equals(JOHN));

        // null -> returns false
        assertFalse(JOHN.equals(null));

        // different type -> returns false
        assertFalse(JOHN.equals(5));

        // different person -> returns false
        assertFalse(JOHN.equals(BOB));

        // different name -> returns false
        Donor editedJohn = new DonorBuilder(JOHN).withName(VALID_NAME_BOB).build();
        assertFalse(JOHN.equals(editedJohn));

        // different phone -> returns false
        editedJohn = new DonorBuilder(JOHN).withPhone(VALID_PHONE_BOB).build();
        assertFalse(JOHN.equals(editedJohn));

        // different nric -> returns false
        editedJohn = new DonorBuilder(JOHN).withNric(VALID_NRIC_BOB).build();
        assertFalse(JOHN.equals(editedJohn));

        // different person type -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withType("doctor").build();
        assertFalse(editedAlice.equals(editedJohn));
    }

    @Test
    public void toStringTest() {
        Donor john = new DonorBuilder(JOHN).build();
        assertEquals(john.toString().trim() , "John Person Type: donor Nric: T1312123P Phone: 81230942 Age: 60");
    }

    @Test
    public void hashCodeTest() {
        Donor john = new DonorBuilder(JOHN).build();

        assertEquals(john.hashCode(), new DonorBuilder(JOHN).build().hashCode());
        assertNotEquals(john.hashCode(),
                new DonorBuilder(JOHN).withPhone(VALID_PHONE_BOB).build().hashCode());
    }
}
