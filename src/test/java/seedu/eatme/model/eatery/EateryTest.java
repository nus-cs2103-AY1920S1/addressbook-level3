package seedu.eatme.model.eatery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_NAME_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_CHEAP;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_NICE;
import static seedu.eatme.testutil.Assert.assertThrows;
import static seedu.eatme.testutil.TypicalEateries.KENTUCKY;
import static seedu.eatme.testutil.TypicalEateries.MCDONALD;

import org.junit.jupiter.api.Test;

import seedu.eatme.testutil.EateryBuilder;

public class EateryTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Eatery eatery = new EateryBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> eatery.getTags().remove(0));
    }

    @Test
    public void isSameEatery() {
        // same object -> returns true
        assertTrue(MCDONALD.isSameEatery(MCDONALD));
        assertTrue(MCDONALD.isSameEatery(new EateryBuilder(MCDONALD).build()));

        // null -> returns false
        assertFalse(MCDONALD.isSameEatery(null));

        Eatery editedAlice = new EateryBuilder(MCDONALD).build();

        // different name -> returns false
        editedAlice = new EateryBuilder(MCDONALD).withName(VALID_NAME_NO_PREFIX_KFC).build();
        assertFalse(MCDONALD.isSameEatery(editedAlice));

        // same name, different attributes -> returns false
        editedAlice = new EateryBuilder(MCDONALD).withAddress(VALID_ADDRESS_NO_PREFIX_KFC)
                .withTags(VALID_TAG_NO_PREFIX_CHEAP).build();
        assertFalse(MCDONALD.isSameEatery(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Eatery aliceCopy = new EateryBuilder(MCDONALD).build();
        assertTrue(MCDONALD.equals(aliceCopy));

        // same object -> returns true
        assertTrue(MCDONALD.equals(MCDONALD));

        // null -> returns false
        assertFalse(MCDONALD.equals(null));

        // different type -> returns false
        assertFalse(MCDONALD.equals(5));

        // different eatery -> returns false
        assertFalse(MCDONALD.equals(KENTUCKY));

        // different name -> returns false
        Eatery editedMac = new EateryBuilder(MCDONALD).withName(VALID_NAME_NO_PREFIX_KFC).build();
        assertFalse(MCDONALD.equals(editedMac));

        // different address -> returns false
        editedMac = new EateryBuilder(MCDONALD).withAddress(VALID_ADDRESS_NO_PREFIX_KFC).build();
        assertFalse(MCDONALD.equals(editedMac));

        // different tags -> returns false
        editedMac = new EateryBuilder(MCDONALD).withTags(VALID_TAG_NO_PREFIX_NICE).build();
        assertFalse(MCDONALD.equals(editedMac));
    }
}
