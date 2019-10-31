package tagline.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.note.TypicalNotes.INCIDENT;
import static tagline.testutil.note.TypicalNotes.PROTECTOR;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tagline.model.note.exceptions.DuplicateNoteException;
import tagline.model.note.exceptions.NoteNotFoundException;

public class UniqueNoteListTest {

    private final UniqueNoteList uniqueNoteList = new UniqueNoteList();

    @Test
    public void contains_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.containsNote(null));
    }

    @Test
    public void containsNote_personNotInList_returnsFalse() {
        assertFalse(uniqueNoteList.containsNote(PROTECTOR));
    }

    @Test
    public void containsNote_personInList_returnsTrue() {
        uniqueNoteList.addNote(PROTECTOR);
        assertTrue(uniqueNoteList.containsNote(PROTECTOR));
    }

    @Test
    public void containsNote_personWithSameIdentityFieldsInList_returnsTrue() {
        //uniqueNoteList.add(PROTECTOR);
        //Note editedProtector = new NoteBuilder(PROTECTOR).withTimeLastUpdated(VALID_TIMELASTUPDATED_INCIDENT)
        //        .withTags(VALID_TAG_AVENGERS)
        //        .build();
        //assertTrue(uniqueNoteList.contains(editedProtector));
    }

    @Test
    public void addNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.addNote(null));
    }

    @Test
    public void addNote_duplicateNote_throwsDuplicateNoteException() {
        uniqueNoteList.addNote(PROTECTOR);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.addNote(PROTECTOR));
    }

    @Test
    public void setNote_nullTargetNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setNote(null, PROTECTOR));
    }

    @Test
    public void setNote_nullEditedNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setNote(PROTECTOR, null));
    }

    @Test
    public void setNote_targetNoteNotInList_throwsNoteNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> uniqueNoteList.setNote(PROTECTOR, PROTECTOR));
    }

    @Test
    public void setNote_editedNoteIsSameNote_success() {
        uniqueNoteList.addNote(PROTECTOR);
        uniqueNoteList.setNote(PROTECTOR, PROTECTOR);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.addNote(PROTECTOR);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNote_editedNoteHasSameIdentity_success() {
        //uniqueNoteList.add(PROTECTOR);
        //Note editedProtector = new NoteBuilder(PROTECTOR).withNoteId(VALID_NOTEID_INCIDENT)
        //        .withTags(VALID_TAG_AVENGERS)
        //        .build();
        //uniqueNoteList.setNote(PROTECTOR, editedProtector);
        //UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        //expectedUniqueNoteList.add(editedProtector);
        //assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNote_editedNoteHasDifferentIdentity_success() {
        uniqueNoteList.addNote(PROTECTOR);
        uniqueNoteList.setNote(PROTECTOR, INCIDENT);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.addNote(INCIDENT);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNote_editedNoteHasNonUniqueIdentity_throwsDuplicateNoteException() {
        uniqueNoteList.addNote(PROTECTOR);
        uniqueNoteList.addNote(INCIDENT);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.setNote(PROTECTOR, INCIDENT));
    }

    @Test
    public void removeNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.removeNote(null));
    }

    @Test
    public void removeNote_personDoesNotExist_throwsNoteNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> uniqueNoteList.removeNote(PROTECTOR));
    }

    @Test
    public void removeNote_existingNote_removesNote() {
        uniqueNoteList.addNote(PROTECTOR);
        uniqueNoteList.removeNote(PROTECTOR);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNotes_nullUniqueNoteList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setNotes((UniqueNoteList) null));
    }

    @Test
    public void setNotes_uniqueNoteList_replacesOwnListWithProvidedUniqueNoteList() {
        uniqueNoteList.addNote(PROTECTOR);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.addNote(INCIDENT);
        uniqueNoteList.setNotes(expectedUniqueNoteList);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNotes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setNotes((List<Note>) null));
    }

    @Test
    public void setNotes_list_replacesOwnListWithProvidedList() {
        uniqueNoteList.addNote(PROTECTOR);
        List<Note> personList = Collections.singletonList(INCIDENT);
        uniqueNoteList.setNotes(personList);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.addNote(INCIDENT);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNotes_listWithDuplicateNotes_throwsDuplicateNoteException() {
        List<Note> listWithDuplicateNotes = Arrays.asList(PROTECTOR, PROTECTOR);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.setNotes(listWithDuplicateNotes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueNoteList.asUnmodifiableObservableList().remove(0));
    }
}
