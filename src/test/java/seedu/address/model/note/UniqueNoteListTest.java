package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_TAG_WORK;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_TITLE_DIARYONE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalNotes.SECRETDIARY;
import static seedu.address.testutil.TypicalNotes.SECRETDOC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.note.exceptions.DuplicateNoteException;
import seedu.address.model.note.exceptions.NoteNotFoundException;
import seedu.address.testutil.NoteBuilder;

public class UniqueNoteListTest {

    private final UniqueNoteList uniqueNoteList = new UniqueNoteList();

    @Test
    public void contains_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.contains(null));
    }

    @Test
    public void contains_noteNotInList_returnsFalse() {
        assertFalse(uniqueNoteList.contains(SECRETDIARY));
    }

    @Test
    public void contains_noteInList_returnsTrue() {
        uniqueNoteList.add(SECRETDIARY);
        assertTrue(uniqueNoteList.contains(SECRETDIARY));
    }

    @Test
    public void contains_noteWithSameIdentityFieldsInList_returnsTrue() {
        uniqueNoteList.add(SECRETDIARY);
        Note editedSecretDiary = new NoteBuilder(SECRETDIARY).withTags(VALID_TAG_WORK)
                .build();
        assertTrue(uniqueNoteList.contains(editedSecretDiary));
    }

    @Test
    public void add_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.add(null));
    }

    @Test
    public void add_duplicateNote_throwsDuplicateNoteException() {
        uniqueNoteList.add(SECRETDIARY);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.add(SECRETDIARY));
    }

    @Test
    public void setNote_nullTargetNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setNote(null, SECRETDIARY));
    }

    @Test
    public void setNote_nullEditedNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setNote(SECRETDIARY, null));
    }

    @Test
    public void setNote_targetNoteNotInList_throwsNoteNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> uniqueNoteList.setNote(SECRETDIARY, SECRETDIARY));
    }

    @Test
    public void setNote_editedNoteIsSameNote_success() {
        uniqueNoteList.add(SECRETDIARY);
        uniqueNoteList.setNote(SECRETDIARY, SECRETDIARY);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(SECRETDIARY);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNote_editedNoteHasSameIdentity_success() {
        uniqueNoteList.add(SECRETDIARY);
        Note editedSecretDiary = new NoteBuilder(SECRETDIARY).withTitle(VALID_TITLE_DIARYONE).withTags(VALID_TAG_WORK)
                .build();
        uniqueNoteList.setNote(SECRETDIARY, editedSecretDiary);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(editedSecretDiary);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNote_editedNoteHasDifferentIdentity_success() {
        uniqueNoteList.add(SECRETDIARY);
        uniqueNoteList.setNote(SECRETDIARY, SECRETDOC);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(SECRETDOC);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNote_editedNoteHasNonUniqueIdentity_throwsDuplicateNoteException() {
        uniqueNoteList.add(SECRETDIARY);
        uniqueNoteList.add(SECRETDOC);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.setNote(SECRETDIARY, SECRETDOC));
    }

    @Test
    public void remove_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.remove(null));
    }

    @Test
    public void remove_noteDoesNotExist_throwsNoteNotFoundException() {
        assertThrows(NoteNotFoundException.class, () -> uniqueNoteList.remove(SECRETDIARY));
    }

    @Test
    public void remove_existingNote_removesNote() {
        uniqueNoteList.add(SECRETDIARY);
        uniqueNoteList.remove(SECRETDIARY);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNotes_nullUniqueNoteList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setNotes((UniqueNoteList) null));
    }

    @Test
    public void setNotes_uniqueNoteList_replacesOwnListWithProvidedUniqueNoteList() {
        uniqueNoteList.add(SECRETDIARY);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(SECRETDOC);
        uniqueNoteList.setNotes(expectedUniqueNoteList);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNotes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNoteList.setNotes((List<Note>) null));
    }

    @Test
    public void setNotes_list_replacesOwnListWithProvidedList() {
        uniqueNoteList.add(SECRETDIARY);
        List<Note> noteList = Collections.singletonList(SECRETDOC);
        uniqueNoteList.setNotes(noteList);
        UniqueNoteList expectedUniqueNoteList = new UniqueNoteList();
        expectedUniqueNoteList.add(SECRETDOC);
        assertEquals(expectedUniqueNoteList, uniqueNoteList);
    }

    @Test
    public void setNotes_listWithDuplicateNotes_throwsDuplicateNoteException() {
        List<Note> listWithDuplicateNotes = Arrays.asList(SECRETDIARY, SECRETDIARY);
        assertThrows(DuplicateNoteException.class, () -> uniqueNoteList.setNotes(listWithDuplicateNotes));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueNoteList.asUnmodifiableObservableList().remove(0));
    }
}
