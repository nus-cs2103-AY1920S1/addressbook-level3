package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.model.transaction.Amount;
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

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
        Amount amount = new Amount(100.00);
        ALICE.spend(amount);

        // 200 - 100 = 100 -> return true
        assertTrue(ALICE.getBalance().equals(new Amount(100.00)));

        Amount zeroAmount = Amount.zero();
        ALICE.spend(zeroAmount);
        // 100 - 0 = 100 -> return true
        assertTrue(ALICE.getBalance().equals(new Amount(100.00)));
    }

    @Test
    public void receive() {
        Amount amount = new Amount(100.00);
        ALICE.receive(amount);
        // 200 + 100 = 300 -> return true
        assertTrue(ALICE.getBalance().equals(new Amount(300.00)));

        Amount zeroAmount = Amount.zero();
        ALICE.receive(zeroAmount);
        // 100 + 0 = 100 -> return true
        assertTrue(ALICE.getBalance().equals(new Amount(300.00)));
    }

    @Test
    public void resetBalance() {
        ALICE.resetBalance();
        // 0 dollars -> return true
        assertTrue(ALICE.getBalance().equals(Amount.zero()));
    }

    @Test
    public void testToString() {
        // same values -> return true
        assertTrue(ALICE.toString().equals("Alice Pauline Balance: 200.00"));

        // same object -> return true
        assertTrue(ALICE.toString().equals(ALICE.toString()));
    }
}
