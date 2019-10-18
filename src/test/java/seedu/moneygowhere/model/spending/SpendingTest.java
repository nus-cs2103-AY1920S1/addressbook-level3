package seedu.moneygowhere.model.spending;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.ALICE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BOB;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.testutil.SpendingBuilder;

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

        // different date and remark -> returns false
        Spending editedAlice = new SpendingBuilder(ALICE).withDate(VALID_DATE_BOB).withRemark(VALID_REMARK_BOB).build();
        assertFalse(ALICE.isSameSpending(editedAlice));

        // different name -> returns false
        editedAlice = new SpendingBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameSpending(editedAlice));

        // same name, same date, different attributes -> returns true
        editedAlice = new SpendingBuilder(ALICE).withRemark(VALID_REMARK_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameSpending(editedAlice));

        // same name, same remark, different attributes -> returns true
        editedAlice = new SpendingBuilder(ALICE).withDate(VALID_DATE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameSpending(editedAlice));

        // same name, same date, same remark, different attributes -> returns true
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

        // different date -> returns false
        editedAlice = new SpendingBuilder(ALICE).withDate(VALID_DATE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different remark -> returns false
        editedAlice = new SpendingBuilder(ALICE).withRemark(VALID_REMARK_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new SpendingBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
