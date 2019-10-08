package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEANING_BUTTERFREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORD_BUTTERFREE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CardBuilder;

public class CardTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Card card = new CardBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> card.getTags().remove(0));
    }

    @Test
    public void isSameName() {
        // same object -> returns true
        assertTrue(ABRA.isSameName(ABRA));

        // null -> returns false
        assertFalse(ABRA.isSameName(null));

        // different name -> returns false
        Card editedAbra = new CardBuilder(ABRA).withName(VALID_WORD_BUTTERFREE).build();
        assertFalse(ABRA.isSameName(editedAbra));

        // same name, different attributes -> returns true
        editedAbra = new CardBuilder(ABRA).withMeaning(VALID_MEANING_BUTTERFREE)
                .withTags(VALID_TAG_BUG).build();
        assertTrue(ABRA.isSameName(editedAbra));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Card abraCopy = new CardBuilder(ABRA).build();
        assertTrue(ABRA.equals(abraCopy));

        // same object -> returns true
        assertTrue(ABRA.equals(ABRA));

        // null -> returns false
        assertFalse(ABRA.equals(null));

        // different type -> returns false
        assertFalse(ABRA.equals(5));

        // different Card -> returns false
        assertFalse(ABRA.equals(BUTTERFREE));

        // different name -> returns false
        Card editedAbra = new CardBuilder(ABRA).withName(VALID_WORD_BUTTERFREE).build();
        assertFalse(ABRA.equals(editedAbra));

        // different description -> returns false
        editedAbra = new CardBuilder(ABRA).withMeaning(VALID_MEANING_BUTTERFREE).build();
        assertFalse(ABRA.equals(editedAbra));

        // different tags -> returns false
        editedAbra = new CardBuilder(ABRA).withTags(VALID_TAG_BUG).build();
        assertFalse(ABRA.equals(editedAbra));
    }
}
