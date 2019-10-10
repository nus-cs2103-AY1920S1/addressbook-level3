package seedu.billboard.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.billboard.testutil.Assert.assertThrows;
import static seedu.billboard.testutil.TypicalPersons.BILLS;
import static seedu.billboard.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.billboard.testutil.ExpenseBuilder;

public class ExpenseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Expense expense = new ExpenseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> expense.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(BILLS.isSameRecord(BILLS));

        // null -> returns false
        assertFalse(BILLS.isSameRecord(null));

        // different phone and email -> returns false
        Expense editedAlice = new ExpenseBuilder(BILLS).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(BILLS.isSameRecord(editedAlice));

        // different name -> returns false
        editedAlice = new ExpenseBuilder(BILLS).withName(VALID_NAME_BOB).build();
        assertFalse(BILLS.isSameRecord(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ExpenseBuilder(BILLS).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(BILLS.isSameRecord(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ExpenseBuilder(BILLS).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(BILLS.isSameRecord(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ExpenseBuilder(BILLS).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(BILLS.isSameRecord(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expense aliceCopy = new ExpenseBuilder(BILLS).build();
        assertTrue(BILLS.equals(aliceCopy));

        // same object -> returns true
        assertTrue(BILLS.equals(BILLS));

        // null -> returns false
        assertFalse(BILLS.equals(null));

        // different type -> returns false
        assertFalse(BILLS.equals(5));

        // different expense -> returns false
        assertFalse(BILLS.equals(BOB));

        // different name -> returns false
        Expense editedAlice = new ExpenseBuilder(BILLS).withName(VALID_NAME_BOB).build();
        assertFalse(BILLS.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ExpenseBuilder(BILLS).withPhone(VALID_PHONE_BOB).build();
        assertFalse(BILLS.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ExpenseBuilder(BILLS).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(BILLS.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ExpenseBuilder(BILLS).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(BILLS.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ExpenseBuilder(BILLS).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(BILLS.equals(editedAlice));
    }
}
