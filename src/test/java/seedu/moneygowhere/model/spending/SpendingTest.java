package seedu.moneygowhere.model.spending;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
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
        assertTrue(APPLE.isSameSpending(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSameSpending(null));

        // different date and remark -> returns false
        Spending editedAlice = new SpendingBuilder(APPLE).withDate(VALID_DATE_BOB).withRemark(VALID_REMARK_BOB).build();
        assertFalse(APPLE.isSameSpending(editedAlice));

        // different name -> returns false
        editedAlice = new SpendingBuilder(APPLE).withName(VALID_NAME_BOB).build();
        assertFalse(APPLE.isSameSpending(editedAlice));

        // same name, same date, different attributes -> returns true
        editedAlice = new SpendingBuilder(APPLE).withRemark(VALID_REMARK_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(APPLE.isSameSpending(editedAlice));

        // same name, same date, same remark, different attributes -> returns true
        editedAlice = new SpendingBuilder(APPLE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(APPLE.isSameSpending(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Spending aliceCopy = new SpendingBuilder(APPLE).build();
        assertTrue(APPLE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(APPLE.equals(APPLE));

        // null -> returns false
        assertFalse(APPLE.equals(null));

        // different type -> returns false
        assertFalse(APPLE.equals(5));

        // different Spending -> returns false
        assertFalse(APPLE.equals(BOB));

        // different name -> returns false
        Spending editedAlice = new SpendingBuilder(APPLE).withName(VALID_NAME_BOB).build();
        assertFalse(APPLE.equals(editedAlice));

        // different date -> returns false
        editedAlice = new SpendingBuilder(APPLE).withDate(VALID_DATE_BOB).build();
        assertFalse(APPLE.equals(editedAlice));

        // different remark -> returns false
        editedAlice = new SpendingBuilder(APPLE).withRemark(VALID_REMARK_BOB).build();
        assertFalse(APPLE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new SpendingBuilder(APPLE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(APPLE.equals(editedAlice));
    }
}
