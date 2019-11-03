package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.model.note.exceptions.DuplicateNoteException;
import seedu.address.model.note.exceptions.NoteNotFoundException;
import seedu.address.testutil.note.NoteBuilder;

public class UniqueNotesListTest {

    private final UniqueNotesList uniqueNotesList = new UniqueNotesList();
    private final Note note = new NoteBuilder().withNote("note1").withDesc("desc").build();
    private final Note differentNote = new NoteBuilder().withNote("note2").withDesc("desc").build();

    @Test
    public void contains_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.contains(null));
    }

    @Test
    public void contains_noteNotInList_returnsFalse() {
        Assertions.assertFalse(uniqueNotesList.contains(note));
    }

    @Test
    public void contains_noteInList_returnsTrue() {
        uniqueNotesList.add(note);
        Assertions.assertTrue(uniqueNotesList.contains(note));
    }

    @Test
    public void contains_noteWithSameIdentityFieldsInList_returnsTrue() {
        uniqueNotesList.add(note);
        Note editedNote = new NoteBuilder(note).withDesc("new desc").withPriority(Priority.UNMARKED).build();
        Assertions.assertTrue(uniqueNotesList.contains(editedNote));
    }

    @Test
    public void add_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.add(null));
    }

    @Test
    public void add_duplicateNote_throwsDuplicatePersonException() {
        uniqueNotesList.add(note);
        assertThrows(DuplicateNoteException.class, () -> uniqueNotesList.add(note));
    }

    @Test
    public void setNote_nullTargetNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.setNote(null, note));
    }

    @Test
    public void setNote_nullEditedNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.setNote(note, null));
    }

    @Test
    public void setNote_targetNoteNotInList_throwsNoteNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> uniqueNotesList.setNote(note, note));
    }

    @Test
    public void setNote_editedNoteIsSameNote_success() {
        uniqueNotesList.add(note);
        uniqueNotesList.setNote(note, note);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        expectedUniqueNotesList.add(note);
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNote_editedPersonHasSameIdentity_success() {
        uniqueNotesList.add(note);
        Note editedNote = new NoteBuilder(note).withDesc("new desc").withPriority(Priority.UNMARKED).build();
        uniqueNotesList.setNote(note, editedNote);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        expectedUniqueNotesList.add(editedNote);
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNote_editedNoteHasDifferentIdentity_success() {
        uniqueNotesList.add(note);
        uniqueNotesList.setNote(note, differentNote);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        expectedUniqueNotesList.add(differentNote);
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNote_editedNoteHasNonUniqueIdentity_throwsDuplicateNoteException() {
        uniqueNotesList.add(note);
        uniqueNotesList.add(differentNote);
        assertThrows(DuplicateNoteException.class, () -> uniqueNotesList.setNote(note, differentNote));
    }

    @Test
    public void remove_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.remove(null));
    }

    @Test
    public void remove_noteDoesNotExist_throwsNoteNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> uniqueNotesList.remove(note));
    }

    @Test
    public void remove_existingNote_removesNote() {
        uniqueNotesList.add(note);
        uniqueNotesList.remove(note);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNotes_nullUniqueNotesList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.setNotes((UniqueNotesList) null));
    }

    @Test
    public void setNotes_uniqueNotesList_replacesOwnListWithProvidedUniqueNotesList() {
        uniqueNotesList.add(note);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        expectedUniqueNotesList.add(differentNote);
        uniqueNotesList.setNotes(expectedUniqueNotesList);
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNotes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.setNotes((List<Note>) null));
    }

    @Test
    public void setNotes_list_replacesOwnListWithProvidedList() {
        uniqueNotesList.add(note);
        List<Note> notesList = Collections.singletonList(differentNote);
        uniqueNotesList.setNotes(notesList);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        expectedUniqueNotesList.add(differentNote);
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNotes_listWithDuplicateNotes_throwsDuplicateNoteException() {
        List<Note> listWithDuplicateNotes = Arrays.asList(note, note);
        assertThrows(DuplicateNoteException.class, () -> uniqueNotesList.setNotes(listWithDuplicateNotes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueNotesList.asUnmodifiableObservableList().remove(0));
    }
}
