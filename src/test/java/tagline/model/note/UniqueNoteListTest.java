package tagline.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static tagline.logic.commands.NoteCommandTestUtil.VALID_NOTEID_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TAG_AVENGERS;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TIMELASTUPDATED_INCIDENT;

import static tagline.testutil.Assert.assertThrows;
import static tagline.testutil.TypicalNotes.INCIDENT;
import static tagline.testutil.TypicalNotes.PROTECTOR;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tagline.model.note.exceptions.DuplicateNoteException;
import tagline.model.note.exceptions.NoteNotFoundException;
import tagline.testutil.NoteBuilder;

public class UniqueNoteListTest {

    private final UniqueNoteList uniqueNoteList = new UniqueNoteList();

    @Test
    public void contains_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueNoteList.contains(PROTECTOR));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueNoteList.add(PROTECTOR);
        assertTrue(uniqueNoteList.contains(PROTECTOR));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueNoteList.add(PROTECTOR);
        Note editedProtector = new NoteBuilder(PROTECTOR).withTimeLastUpdated(VALID_TIMELASTUPDATED_INCIDENT)
                .withTags(VALID_TAG_AVENGERS)
                .build();
        assertTrue(uniqueNoteList.contains(editedProtector));
    }

    @Test
    public void add_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.add(null));
    }

    @Test
    public void add_duplicateNote_throwsDuplicateNoteException() {
        uniqueNoteList.add(PROTECTOR);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.add(PROTECTOR));
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
        uniqueNoteList.add(PROTECTOR);
        uniqueNoteList.setNote(PROTECTOR, PROTECTOR);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(PROTECTOR);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNote_editedNoteHasSameIdentity_success() {
        uniqueNoteList.add(PROTECTOR);
        Note editedProtector = new NoteBuilder(PROTECTOR).withNoteId(VALID_NOTEID_INCIDENT)
                .withTags(VALID_TAG_AVENGERS)
                .build();
        uniqueNoteList.setNote(PROTECTOR, editedProtector);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(editedProtector);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNote_editedNoteHasDifferentIdentity_success() {
        uniqueNoteList.add(PROTECTOR);
        uniqueNoteList.setNote(PROTECTOR, INCIDENT);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(INCIDENT);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNote_editedNoteHasNonUniqueIdentity_throwsDuplicateNoteException() {
        uniqueNoteList.add(PROTECTOR);
        uniqueNoteList.add(INCIDENT);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.setNote(PROTECTOR, INCIDENT));
    }

    @Test
    public void remove_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsNoteNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> uniqueNoteList.remove(PROTECTOR));
    }

    @Test
    public void remove_existingNote_removesNote() {
        uniqueNoteList.add(PROTECTOR);
        uniqueNoteList.remove(PROTECTOR);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNotes_nullUniqueNoteList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setNotes((UniqueNoteList) null));
    }

    @Test
    public void setNotes_uniqueNoteList_replacesOwnListWithProvidedUniqueNoteList() {
        uniqueNoteList.add(PROTECTOR);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(INCIDENT);
        uniqueNoteList.setNotes(expectedUniqueNoteList);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNotes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setNotes((List<Note>) null));
    }

    @Test
    public void setNotes_list_replacesOwnListWithProvidedList() {
        uniqueNoteList.add(PROTECTOR);
        List<Note> personList = Collections.singletonList(INCIDENT);
        uniqueNoteList.setNotes(personList);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(INCIDENT);
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
