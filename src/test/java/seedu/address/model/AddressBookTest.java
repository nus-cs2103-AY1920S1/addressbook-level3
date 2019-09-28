package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMemes.ALICE;
import static seedu.address.testutil.TypicalMemes.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.meme.Meme;
import seedu.address.model.meme.exceptions.DuplicateMemeException;
import seedu.address.testutil.MemeBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getMemeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateMemes_throwsDuplicateMemeException() {
        // Two memes with the same identity fields
        Meme editedAlice = new MemeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Meme> newMemes = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newMemes);

        assertThrows(DuplicateMemeException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasMeme_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasMeme(null));
    }

    @Test
    public void hasMeme_memeNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasMeme(ALICE));
    }

    @Test
    public void hasMeme_memeInAddressBook_returnsTrue() {
        addressBook.addMeme(ALICE);
        assertTrue(addressBook.hasMeme(ALICE));
    }

    @Test
    public void hasMeme_memeWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addMeme(ALICE);
        Meme editedAlice = new MemeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasMeme(editedAlice));
    }

    @Test
    public void getMemeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getMemeList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose memes list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Meme> memes = FXCollections.observableArrayList();

        AddressBookStub(Collection<Meme> memes) {
            this.memes.setAll(memes);
        }

        @Override
        public ObservableList<Meme> getMemeList() {
            return memes;
        }
    }

}
