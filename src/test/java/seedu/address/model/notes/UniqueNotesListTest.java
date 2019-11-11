package seedu.address.model.notes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNotes.NOTES_CS2103T;
import static seedu.address.testutil.TypicalNotes.NOTES_CS3235;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.note.Notes;
import seedu.address.model.note.UniqueNotesList;
import seedu.address.model.note.exceptions.DuplicateNotesExceptions;
import seedu.address.model.note.exceptions.NotesNotFoundException;
import seedu.address.testutil.NotesBuilder;

public class UniqueNotesListTest {
    private static final String VALID_NOTE_TYPE = "tut";
    private final UniqueNotesList uniqueNotesList = new UniqueNotesList();

    @Test
    public void contains_nullNotes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.contains(null));
    }

    @Test
    public void contains_notesNotInList_returnsFalse() {
        assertFalse(uniqueNotesList.contains(NOTES_CS2103T));
    }

    @Test
    public void contains_notesInList_returnsTrue() {
        uniqueNotesList.add(NOTES_CS2103T);
        assertTrue(uniqueNotesList.contains(NOTES_CS2103T));
    }

    @Test
    public void add_nullNotes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.add(null));
    }

    @Test
    public void add_duplicateNotes_throwsDuplicatePersonException() {
        uniqueNotesList.add(NOTES_CS2103T);
        assertThrows(DuplicateNotesExceptions.class, () -> uniqueNotesList.add(NOTES_CS2103T));
    }

    @Test
    public void setNotes_nullTargetNotes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.setNotes(null, NOTES_CS2103T));
    }

    @Test
    public void setNotes_nullEditedEarnings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.setNotes(NOTES_CS2103T, null));
    }

    @Test
    public void setNotes_targetNotesNotInList_throwsNotesNotFoundException() {
        assertThrows(NotesNotFoundException.class, () -> uniqueNotesList.setNotes(NOTES_CS2103T, NOTES_CS2103T));
    }

    @Test
    public void setNotes_editedNotesIsSameNotes_success() {
        uniqueNotesList.add(NOTES_CS2103T);
        uniqueNotesList.setNotes(NOTES_CS2103T, NOTES_CS2103T);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        expectedUniqueNotesList.add(NOTES_CS2103T);
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNotes_editedNotesHasSameIdentity_success() {
        uniqueNotesList.add(NOTES_CS2103T);
        Notes editedCS2103T = new NotesBuilder(NOTES_CS2103T).withClassType(VALID_NOTE_TYPE)
                .build();
        uniqueNotesList.setNotes(NOTES_CS2103T, editedCS2103T);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        expectedUniqueNotesList.add(editedCS2103T);
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNotes_editedNotesHasDifferentIdentity_success() {
        uniqueNotesList.add(NOTES_CS2103T);
        uniqueNotesList.setNotes(NOTES_CS2103T, NOTES_CS3235);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        expectedUniqueNotesList.add(NOTES_CS3235);
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNotes_editedNotessHasNonUniqueIdentity_throwsDuplicateNotesException() {
        uniqueNotesList.add(NOTES_CS2103T);
        uniqueNotesList.add(NOTES_CS3235);
        assertThrows(DuplicateNotesExceptions.class, () -> uniqueNotesList.setNotes(NOTES_CS2103T, NOTES_CS3235));
    }

    @Test
    public void remove_nullNotes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.remove(null));
    }

    @Test
    public void remove_notesDoesNotExist_throwsNotesNotFoundException() {
        assertThrows(NotesNotFoundException.class, () -> uniqueNotesList.remove(NOTES_CS2103T));
    }

    @Test
    public void remove_existingEarnings_removesEarnings() {
        uniqueNotesList.add(NOTES_CS2103T);
        uniqueNotesList.remove(NOTES_CS2103T);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNotes_nullUniqueNotesList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.setNotes((UniqueNotesList) null));
    }

    @Test
    public void setNotes_uniqueNotesList_replacesOwnListWithProvidedUniqueNotesList() {
        uniqueNotesList.add(NOTES_CS2103T);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        expectedUniqueNotesList.add(NOTES_CS3235);
        uniqueNotesList.setNotes(expectedUniqueNotesList);
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNotes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNotesList.setNotes((List<Notes>) null));
    }

    @Test
    public void setNotes_list_replacesOwnListWithProvidedList() {
        uniqueNotesList.add(NOTES_CS3235);
        List<Notes> notesList = Collections.singletonList(NOTES_CS2103T);
        uniqueNotesList.setNotes(notesList);
        UniqueNotesList expectedUniqueNotesList = new UniqueNotesList();
        expectedUniqueNotesList.add(NOTES_CS2103T);
        assertEquals(expectedUniqueNotesList, uniqueNotesList);
    }

    @Test
    public void setNotes_listWithDuplicateNotes_throwsDuplicateNotesException() {
        List<Notes> listWithDuplicateNotes = Arrays.asList(NOTES_CS2103T, NOTES_CS2103T);
        assertThrows(DuplicateNotesExceptions.class, () -> uniqueNotesList.setNotes(listWithDuplicateNotes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueNotesList.asUnmodifiableObservableList().remove(0));
    }
}
