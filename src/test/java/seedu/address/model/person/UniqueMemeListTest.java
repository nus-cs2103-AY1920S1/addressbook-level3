package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateMemeException;
import seedu.address.model.person.exceptions.MemeNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueMemeListTest {

    private final UniqueMemeList uniqueMemeList = new UniqueMemeList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueMemeList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueMemeList.add(ALICE);
        assertTrue(uniqueMemeList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMemeList.add(ALICE);
        Meme editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueMemeList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueMemeList.add(ALICE);
        assertThrows(DuplicateMemeException.class, () -> uniqueMemeList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMeme(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMeme(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(MemeNotFoundException.class, () -> uniqueMemeList.setMeme(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueMemeList.add(ALICE);
        uniqueMemeList.setMeme(ALICE, ALICE);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(ALICE);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueMemeList.add(ALICE);
        Meme editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueMemeList.setMeme(ALICE, editedAlice);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(editedAlice);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueMemeList.add(ALICE);
        uniqueMemeList.setMeme(ALICE, BOB);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(BOB);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueMemeList.add(ALICE);
        uniqueMemeList.add(BOB);
        assertThrows(DuplicateMemeException.class, () -> uniqueMemeList.setMeme(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(MemeNotFoundException.class, () -> uniqueMemeList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueMemeList.add(ALICE);
        uniqueMemeList.remove(ALICE);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMemes((UniqueMemeList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueMemeList.add(ALICE);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(BOB);
        uniqueMemeList.setMemes(expectedUniqueMemeList);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemeList.setMemes((List<Meme>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueMemeList.add(ALICE);
        List<Meme> memeList = Collections.singletonList(BOB);
        uniqueMemeList.setMemes(memeList);
        UniqueMemeList expectedUniqueMemeList = new UniqueMemeList();
        expectedUniqueMemeList.add(BOB);
        assertEquals(expectedUniqueMemeList, uniqueMemeList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Meme> listWithDuplicateMemes = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateMemeException.class, () -> uniqueMemeList.setMemes(listWithDuplicateMemes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueMemeList.asUnmodifiableObservableList().remove(0));
    }
}
