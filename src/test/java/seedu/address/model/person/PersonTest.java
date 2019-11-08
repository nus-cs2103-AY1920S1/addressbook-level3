package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.Amount;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {


    @BeforeEach
    public void setUp() {

    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, different balance -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withAmount("100").build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BENSON));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

    }

    @Test
    public void spend() {
        Person georgeCopy = new PersonBuilder(GEORGE).build();
        Amount amount = new Amount(100.00);
        georgeCopy.spend(amount);

        // 500 - 100 = 400 -> return true
        assertTrue(georgeCopy.getBalance().equals(new Amount(400.00)));

        Amount zeroAmount = Amount.zero();
        georgeCopy.spend(zeroAmount);
        // 400 - 0 = 400 -> return true
        assertTrue(georgeCopy.getBalance().equals(new Amount(400.00)));
    }

    @Test
    public void receive() {
        Person georgeCopy = new PersonBuilder(GEORGE).build();
        Amount amount = new Amount(100.00);
        georgeCopy.receive(amount);
        // 500 + 100 = 600 -> return true
        assertTrue(georgeCopy.getBalance().equals(new Amount(600.00)));

        Amount zeroAmount = Amount.zero();
        georgeCopy.receive(zeroAmount);
        // 600 + 0 = 600 -> return true
        assertTrue(georgeCopy.getBalance().equals(new Amount(600.00)));
    }

    @Test
    public void resetBalance() {
        Person georgeCopy = new PersonBuilder(GEORGE).build();
        georgeCopy.resetBalance();
        // 0 dollars -> return true
        assertTrue(georgeCopy.getBalance().equals(Amount.zero()));
    }

    @Test
    public void testToString() {
        // same values -> return true
        assertTrue(GEORGE.toString().equals("George Best Balance: 500.00"));

        // same object -> return true
        assertTrue(GEORGE.toString().equals(GEORGE.toString()));
    }

    @Test
    public void testHashCode() {
        Person georgeCopy = new PersonBuilder(GEORGE).build();

        // same values -> return true
        assertTrue(GEORGE.hashCode() == georgeCopy.hashCode());

        // same object -> return true
        assertTrue(GEORGE.hashCode() == GEORGE.hashCode());

        // different object -> return false
        assertFalse(GEORGE.hashCode() == ALICE.hashCode());
    }
}
