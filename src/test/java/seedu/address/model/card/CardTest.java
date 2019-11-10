package seedu.address.model.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEANING_BUTTERFREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BUG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORD_BUTTERFREE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.CardBuilder;

public class CardTest {


    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Card card = new CardBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> card.getTags().remove(0));
    }

    @Test
    public void isSameMeaning() {
        // same object -> returns true
        assertTrue(ABRA.isSameMeaning(ABRA));

        // null -> returns false
        assertFalse(ABRA.isSameMeaning(null));

        // different word -> returns true
        Card editedAbra = new CardBuilder(ABRA).withWord(VALID_WORD_BUTTERFREE).build();
        assertTrue(ABRA.isSameMeaning(editedAbra));

        // same word, different meanings -> returns false
        editedAbra = new CardBuilder(ABRA).withMeaning(VALID_MEANING_BUTTERFREE)
                .withTags(VALID_TAG_BUG).build();
        assertFalse(ABRA.isSameMeaning(editedAbra));
    }

    @Test
    public void getHint() {
        String wordStr = "Pikachu";
        String meaningStr = "PIKA PIKA";
        Card card = new Card("dummyID", new Word(wordStr), new Meaning(meaningStr), new HashSet<>());
        FormattedHint formattedHint = card.getFormattedHint();
        for (int i = 0; i < wordStr.length(); ++i) {
            formattedHint = card.getFormattedHint();
        }
        /** After all hint characters are supplied, the formatted hint should be same as original word. */
        assertTrue(formattedHint.toString().equals(wordStr));
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
        Card editedAbra = new CardBuilder(ABRA).withWord(VALID_WORD_BUTTERFREE).build();
        assertFalse(ABRA.equals(editedAbra));

        // different description -> returns false
        editedAbra = new CardBuilder(ABRA).withMeaning(VALID_MEANING_BUTTERFREE).build();
        assertFalse(ABRA.equals(editedAbra));

        // different tags -> returns false
        editedAbra = new CardBuilder(ABRA).withTags(VALID_TAG_BUG).build();
        assertFalse(ABRA.equals(editedAbra));
    }

    @Test
    public void testClone() {
        Card abraCopy = new CardBuilder(ABRA).build();
        assertFalse(abraCopy == abraCopy.clone());
        assertTrue(abraCopy.equals(abraCopy.clone()));
    }

    @Test
    public void getHintFormatSize() {
        String wordStr = "Pikachu";
        String meaningStr = "PIKA PIKA";
        Card card = new Card("dummyID", new Word(wordStr), new Meaning(meaningStr), new HashSet<>());
        assertEquals(card.getHintFormatSize(), wordStr.length());

        String wordWithSpaces = "0            0";
        card = new Card("dummyID", new Word(wordWithSpaces), new Meaning("0 0"), new HashSet<>());
        assertEquals(card.getHintFormatSize(), wordWithSpaces.length());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Word dummyWord = new Word("dummy");
        Meaning dummyMeaning = new Meaning("dummy");
        String idStr = "dummy";
        Set<Tag> dummyHashSet = new HashSet<>();

        assertThrows(NullPointerException.class, () ->
                new Card(null, dummyWord, dummyMeaning, dummyHashSet)
        );

        assertThrows(NullPointerException.class, () ->
                new Card(idStr, null, dummyMeaning, dummyHashSet)
        );

        assertThrows(NullPointerException.class, () ->
                new Card(idStr, dummyWord, null, dummyHashSet)
        );

        assertThrows(NullPointerException.class, () ->
                new Card(idStr, dummyWord, dummyMeaning, null)
        );
    }
}
