package tagline.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.note.TypicalNotes.PROTECTOR;
import static tagline.testutil.note.TypicalNotes.getTypicalNoteBook;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NoteBookTest {

    private final NoteBook addressBook = new NoteBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getNoteList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyNoteBook_replacesData() {
        NoteBook newData = getTypicalNoteBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    //@Test
    //public void resetData_withDuplicateNotes_throwsDuplicateNoteException() {
    //    // Two notes with the same identity fields
    //    //change withAddress to just NoteID
    //    Note editedAlice = new NoteBuilder(PROTECTOR).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
    //            .build();
    //    List<Note> newNotes = Arrays.asList(PROTECTOR, editedAlice);
    //    NoteBookStub newData = new NoteBookStub(newNotes);

    //    assertThrows(DuplicateNoteException.class, () -> addressBook.resetData(newData));
    //}

    @Test
    public void hasNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasNote(null));
    }

    @Test
    public void hasNote_noteNotInNoteBook_returnsFalse() {
        assertFalse(addressBook.hasNote(PROTECTOR));
    }

    @Test
    public void hasNote_noteInNoteBook_returnsTrue() {
        addressBook.addNote(PROTECTOR);
        assertTrue(addressBook.hasNote(PROTECTOR));
    }

    //@Test
    //public void hasNote_noteWithSameIdentityFieldsInNoteBook_returnsTrue() {
    //    addressBook.addNote(PROTECTOR);
    //  //use noteID instead of Address
    //    Note editedAlice = new NoteBuilder(PROTECTOR).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
    //            .build();
    //    assertTrue(addressBook.hasNote(editedAlice));
    //}

    @Test
    public void getNoteList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getNoteList().remove(0));
    }

    /**
     * A stub ReadOnlyNoteBook whose notes list can violate interface constraints.
     */
    private static class NoteBookStub implements ReadOnlyNoteBook {
        private final ObservableList<Note> notes = FXCollections.observableArrayList();

        NoteBookStub(Collection<Note> notes) {
            this.notes.setAll(notes);
        }

        @Override
        public ObservableList<Note> getNoteList() {
            return notes;
        }
    }

}
