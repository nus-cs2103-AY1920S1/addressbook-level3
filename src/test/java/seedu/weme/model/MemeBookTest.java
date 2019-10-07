package seedu.weme.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.ALICE;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.exceptions.DuplicateMemeException;
import seedu.weme.testutil.MemeBuilder;

public class MemeBookTest {

    private final MemeBook memeBook = new MemeBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), memeBook.getMemeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> memeBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMemeBook_replacesData() {
        MemeBook newData = getTypicalMemeBook();
        memeBook.resetData(newData);
        assertEquals(newData, memeBook);
    }

    @Test
    public void resetData_withDuplicateMemes_throwsDuplicateMemeException() {
        // Two memes with the same identity fields
        Meme editedAlice = new MemeBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Meme> newMemes = Arrays.asList(ALICE, editedAlice);
        MemeBookStub newData = new MemeBookStub(newMemes);

        assertThrows(DuplicateMemeException.class, () -> memeBook.resetData(newData));
    }

    @Test
    public void hasMeme_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> memeBook.hasMeme(null));
    }

    @Test
    public void hasMeme_memeNotInMemeBook_returnsFalse() {
        assertFalse(memeBook.hasMeme(ALICE));
    }

    @Test
    public void hasMeme_memeInMemeBook_returnsTrue() {
        memeBook.addMeme(ALICE);
        assertTrue(memeBook.hasMeme(ALICE));
    }

    @Test
    public void hasMeme_memeWithSameIdentityFieldsInMemeBook_returnsTrue() {
        memeBook.addMeme(ALICE);
        Meme editedAlice = new MemeBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(memeBook.hasMeme(editedAlice));
    }

    @Test
    public void getMemeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> memeBook.getMemeList().remove(0));
    }

    /**
     * A stub ReadOnlyMemeBook whose memes list can violate interface constraints.
     */
    private static class MemeBookStub implements ReadOnlyMemeBook {
        private final ObservableList<Meme> memes = FXCollections.observableArrayList();

        MemeBookStub(Collection<Meme> memes) {
            this.memes.setAll(memes);
        }

        @Override
        public ObservableList<Meme> getMemeList() {
            return memes;
        }
    }

}
