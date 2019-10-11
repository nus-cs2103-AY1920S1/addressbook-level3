package seedu.weme.model.meme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_CHARMANDER;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.DOGE;
import static seedu.weme.testutil.TypicalMemes.JOKER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.weme.model.meme.exceptions.DuplicateMemeException;
import seedu.weme.model.meme.exceptions.MemeNotFoundException;
import seedu.weme.testutil.MemeBuilder;

public class UniqueMemeListTest {

    private final UniqueMemeList uniqueMemeList = new UniqueMemeList();

    @Test
    public void contains_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.contains(null));
    }

    @Test
    public void contains_memeNotInList_returnsFalse() {
        assertFalse(uniqueMemeList.contains(DOGE));
    }

    @Test
    public void contains_memeInList_returnsTrue() {
        uniqueMemeList.add(DOGE);
        assertTrue(uniqueMemeList.contains(DOGE));
    }

    @Test
    public void contains_memeWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMemeList.add(DOGE);
        Meme editedAlice = new MemeBuilder(DOGE).withDescription(VALID_DESCRIPTION_JOKER).withTags(VALID_TAG_CHARMANDER)
                .build();
        assertTrue(uniqueMemeList.contains(editedAlice));
    }

    @Test
    public void add_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.add(null));
    }

    @Test
    public void add_duplicateMeme_throwsDuplicateMemeException() {
        uniqueMemeList.add(DOGE);
        assertThrows(DuplicateMemeException.class, () -> uniqueMemeList.add(DOGE));
    }

    @Test
    public void setMeme_nullTargetMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMeme(null, DOGE));
    }

    @Test
    public void setMeme_nullEditedMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMeme(DOGE, null));
    }

    @Test
    public void setMeme_targetMemeNotInList_throwsMemeNotFoundException() {
        assertThrows(MemeNotFoundException.class, () -> uniqueMemeList.setMeme(DOGE, DOGE));
    }

    @Test
    public void setMeme_editedMemeIsSameMeme_success() {
        uniqueMemeList.add(DOGE);
        uniqueMemeList.setMeme(DOGE, DOGE);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(DOGE);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMeme_editedMemeHasSameIdentity_success() {
        uniqueMemeList.add(DOGE);
        Meme editedAlice = new MemeBuilder(DOGE).withDescription(VALID_DESCRIPTION_JOKER).withTags(VALID_TAG_CHARMANDER)
                .build();
        uniqueMemeList.setMeme(DOGE, editedAlice);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(editedAlice);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMeme_editedMemeHasDifferentIdentity_success() {
        uniqueMemeList.add(DOGE);
        uniqueMemeList.setMeme(DOGE, JOKER);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(JOKER);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMeme_editedMemeHasNonUniqueIdentity_throwsDuplicateMemeException() {
        uniqueMemeList.add(DOGE);
        uniqueMemeList.add(JOKER);
        assertThrows(DuplicateMemeException.class, () -> uniqueMemeList.setMeme(DOGE, JOKER));
    }

    @Test
    public void remove_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.remove(null));
    }

    @Test
    public void remove_memeDoesNotExist_throwsMemeNotFoundException() {
        assertThrows(MemeNotFoundException.class, () -> uniqueMemeList.remove(DOGE));
    }

    @Test
    public void remove_existingMeme_removesMeme() {
        uniqueMemeList.add(DOGE);
        uniqueMemeList.remove(DOGE);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMemes_nullUniqueMemeList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMemes((UniqueMemeList) null));
    }

    @Test
    public void setMemes_uniqueMemeList_replacesOwnListWithProvidedUniqueMemeList() {
        uniqueMemeList.add(DOGE);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(JOKER);
        uniqueMemeList.setMemes(expectedUniqueMemeList);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMemes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMemes((List<Meme>) null));
    }

    @Test
    public void setMemes_list_replacesOwnListWithProvidedList() {
        uniqueMemeList.add(DOGE);
        List<Meme> memeList = Collections.singletonList(JOKER);
        uniqueMemeList.setMemes(memeList);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(JOKER);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMemes_listWithDuplicateMemes_throwsDuplicateMemeException() {
        List<Meme> listWithDuplicateMemes = Arrays.asList(DOGE, DOGE);
        assertThrows(DuplicateMemeException.class, () -> uniqueMemeList.setMemes(listWithDuplicateMemes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueMemeList.asUnmodifiableObservableList().remove(0));
    }
}
