package seedu.weme.model.meme;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_CHARMANDER;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.DOGE;
import static seedu.weme.testutil.TypicalMemes.JOKER;

import org.junit.jupiter.api.Test;

import seedu.weme.testutil.MemeBuilder;

public class MemeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Meme meme = new MemeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> meme.getTags().remove(0));
    }

    @Test
    public void isSameUrl() {
        // same object -> returns true
        assertTrue(DOGE.isSameMeme(DOGE));

        // null -> returns false
        assertFalse(DOGE.isSameMeme(null));

        // same name, different attributes -> returns true
        Meme editedAlice = new MemeBuilder(DOGE).withDescription(VALID_DESCRIPTION_JOKER)
                .withTags(VALID_TAG_CHARMANDER).build();
        assertTrue(DOGE.isSameMeme(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Meme aliceCopy = new MemeBuilder(DOGE).build();
        assertTrue(DOGE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(DOGE.equals(DOGE));

        // null -> returns false
        assertFalse(DOGE.equals(null));

        // different type -> returns false
        assertFalse(DOGE.equals(5));

        // different meme -> returns false
        assertFalse(DOGE.equals(JOKER));

        // different url -> returns false
        Meme editedAlice = new MemeBuilder(DOGE).withFilePath(VALID_FILEPATH_JOKER).build();
        assertFalse(DOGE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new MemeBuilder(DOGE).withTags(VALID_TAG_CHARMANDER).build();
        assertFalse(DOGE.equals(editedAlice));
    }
}
