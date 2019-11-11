package seedu.address.model.borrower;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalBorrowers.ALICE;
import static seedu.address.testutil.TypicalBorrowers.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.BorrowerBuilder;


public class BorrowerTest {
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameBorrower(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameBorrower(null));

        // different phone and email -> returns true
        Borrower editedAlice = new BorrowerBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSameBorrower(editedAlice));

        // different name -> returns true
        editedAlice = new BorrowerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSameBorrower(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new BorrowerBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .build();
        assertTrue(ALICE.isSameBorrower(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new BorrowerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.isSameBorrower(editedAlice));

        // different borrowerId, -> returns false
        editedAlice = new BorrowerBuilder(ALICE).withBorrowerId(VALID_ID_BOB).build();
        assertFalse(ALICE.isSameBorrower(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Borrower aliceCopy = new BorrowerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Borrower editedAlice = new BorrowerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new BorrowerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new BorrowerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different BorrowerId -> returns false
        editedAlice = new BorrowerBuilder(ALICE).withBorrowerId(VALID_ID_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toFullString_borrower() {
        Borrower alice = new BorrowerBuilder(ALICE).build();
        String stringRep = alice.getName()
                + ", Phone: " + alice.getPhone()
                + ", Email: " + alice.getEmail()
                + ", Borrower ID: " + alice.getBorrowerId();

        String temp = alice.toFullString();
        assertTrue(temp.equals(stringRep));
    }

    @Test
    public void hashCode_sameBorrowerSameHashCode_assertTrue() {
        Borrower borrower1 = new BorrowerBuilder(ALICE).build();
        Borrower borrower2 = new BorrowerBuilder(ALICE).build();
        assertEquals(borrower1.hashCode(), borrower2.hashCode());
    }
}
