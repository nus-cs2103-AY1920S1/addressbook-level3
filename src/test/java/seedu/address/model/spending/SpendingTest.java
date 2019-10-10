package seedu.address.model.spending;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSpendings.ALICE;
import static seedu.address.testutil.TypicalSpendings.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SpendingBuilder;

public class SpendingTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Spending spending = new SpendingBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> spending.getTags().remove(0));
    }

    @Test
    public void isSameSpending() {
        // same object -> returns true
        assertTrue(ALICE.isSameSpending(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameSpending(null));

        // different phone and email -> returns false
        Spending editedAlice = new SpendingBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameSpending(editedAlice));

        // different name -> returns false
        editedAlice = new SpendingBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameSpending(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new SpendingBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameSpending(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new SpendingBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameSpending(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new SpendingBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameSpending(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Spending aliceCopy = new SpendingBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different Spending -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Spending editedAlice = new SpendingBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new SpendingBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new SpendingBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new SpendingBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
