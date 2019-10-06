package seedu.weme.model.meme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.ALICE;
import static seedu.weme.testutil.TypicalMemes.BOB;

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
        assertFalse(uniqueMemeList.contains(ALICE));
    }

    @Test
    public void contains_memeInList_returnsTrue() {
        uniqueMemeList.add(ALICE);
        assertTrue(uniqueMemeList.contains(ALICE));
    }

    @Test
    public void contains_memeWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMemeList.add(ALICE);
        Meme editedAlice = new MemeBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueMemeList.contains(editedAlice));
    }

    @Test
    public void add_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.add(null));
    }

    @Test
    public void add_duplicateMeme_throwsDuplicateMemeException() {
        uniqueMemeList.add(ALICE);
        assertThrows(DuplicateMemeException.class, () -> uniqueMemeList.add(ALICE));
    }

    @Test
    public void setMeme_nullTargetMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMeme(null, ALICE));
    }

    @Test
    public void setMeme_nullEditedMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMeme(ALICE, null));
    }

    @Test
    public void setMeme_targetMemeNotInList_throwsMemeNotFoundException() {
        assertThrows(MemeNotFoundException.class, () -> uniqueMemeList.setMeme(ALICE, ALICE));
    }

    @Test
    public void setMeme_editedMemeIsSameMeme_success() {
        uniqueMemeList.add(ALICE);
        uniqueMemeList.setMeme(ALICE, ALICE);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(ALICE);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMeme_editedMemeHasSameIdentity_success() {
        uniqueMemeList.add(ALICE);
        Meme editedAlice = new MemeBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueMemeList.setMeme(ALICE, editedAlice);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(editedAlice);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMeme_editedMemeHasDifferentIdentity_success() {
        uniqueMemeList.add(ALICE);
        uniqueMemeList.setMeme(ALICE, BOB);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(BOB);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMeme_editedMemeHasNonUniqueIdentity_throwsDuplicateMemeException() {
        uniqueMemeList.add(ALICE);
        uniqueMemeList.add(BOB);
        assertThrows(DuplicateMemeException.class, () -> uniqueMemeList.setMeme(ALICE, BOB));
    }

    @Test
    public void remove_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.remove(null));
    }

    @Test
    public void remove_memeDoesNotExist_throwsMemeNotFoundException() {
        assertThrows(MemeNotFoundException.class, () -> uniqueMemeList.remove(ALICE));
    }

    @Test
    public void remove_existingMeme_removesMeme() {
        uniqueMemeList.add(ALICE);
        uniqueMemeList.remove(ALICE);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMemes_nullUniqueMemeList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMemes((UniqueMemeList) null));
    }

    @Test
    public void setMemes_uniqueMemeList_replacesOwnListWithProvidedUniqueMemeList() {
        uniqueMemeList.add(ALICE);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(BOB);
        uniqueMemeList.setMemes(expectedUniqueMemeList);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMemes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMemes((List<Meme>) null));
    }

    @Test
    public void setMemes_list_replacesOwnListWithProvidedList() {
        uniqueMemeList.add(ALICE);
        List<Meme> memeList = Collections.singletonList(BOB);
        uniqueMemeList.setMemes(memeList);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(BOB);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setMemes_listWithDuplicateMemes_throwsDuplicateMemeException() {
        List<Meme> listWithDuplicateMemes = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateMemeException.class, () -> uniqueMemeList.setMemes(listWithDuplicateMemes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueMemeList.asUnmodifiableObservableList().remove(0));
    }
}
