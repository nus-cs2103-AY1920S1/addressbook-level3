package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.EventUtil.eventToVEventMapper;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.note.Note;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.question.Question;
import seedu.address.model.question.SavedQuestions;
import seedu.address.model.statistics.StatisticsRecord;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentRecord;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.testutil.event.EventBuilder;
import seedu.address.testutil.note.NoteBuilder;
import seedu.address.testutil.question.QuestionBuilder;
import seedu.address.testutil.student.StudentBuilder;
import seedu.address.testutil.student.TypicalStudents;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new StatisticsRecord(), new StatisticsRecord(modelManager.getStatisticsRecord()));
        assertEquals(new NotesRecord(), new NotesRecord(modelManager.getNotesRecord()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void hasNote_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasNote(null));
    }

    @Test
    public void hasNote_noteNotInNotesRecord_returnsFalse() {
        Note note = new NoteBuilder().build();
        assertFalse(modelManager.hasNote(note));
    }

    @Test
    public void hasNote_noteInNotesRecord_returnsTrue() {
        Note note = new NoteBuilder().build();
        modelManager.addNote(note);
        assertTrue(modelManager.hasNote(note));
    }

    @Test
    public void hasEvent_eventInEventStorage_returnsTrue() {
        VEvent event = eventToVEventMapper(new EventBuilder().build());
        modelManager.addVEvent(event);
        assertTrue(modelManager.hasVEvent(event));
    }

    @Test
    public void deleteEvent_decreaseEventStorageSize_returnsTrue() {
        VEvent event = eventToVEventMapper(new EventBuilder().build());
        modelManager.addVEvent(event);
        int initialSize = modelManager.getEventRecord().getAllEvents().size();
        modelManager.deleteVEvent(Index.fromOneBased(1));
        assertEquals(initialSize - 1, modelManager.getEventRecord().getAllEvents().size());
    }

    @Test
    public void addEvent_increaseEventStorageSize_returnsTrue() {
        VEvent event = eventToVEventMapper(new EventBuilder().build());
        int initialSize = modelManager.getEventRecord().getAllEvents().size();
        modelManager.addVEvent(event);
        assertEquals(initialSize + 1, modelManager.getEventRecord().getAllEvents().size());
    }


    @Test
    public void deleteNote_decreaseNotesRecordSize_returnsTrue() {
        Note note = new NoteBuilder().build();
        modelManager.addNote(note);
        int initialSize = modelManager.getNotesRecord().getNotesList().size();
        modelManager.deleteNote(note);
        assertEquals(initialSize - 1, modelManager.getNotesRecord().getNotesList().size());
    }

    @Test
    public void addNote_increaseNotesRecordSize_returnsTrue() {
        Note note = new NoteBuilder().build();
        int initialSize = modelManager.getNotesRecord().getNotesList().size();
        modelManager.addNote(note);
        assertEquals(initialSize + 1, modelManager.getNotesRecord().getNotesList().size());
    }

    @Test
    public void sameNotesRecord_equals() {
        NotesRecord notesRecord = new NotesRecord();
        modelManager.setNotesRecord(notesRecord);
        assertEquals(notesRecord, modelManager.getNotesRecord());
    }

    @Test
    public void addStatistics_nullStatistics_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addStatistics(null));
    }

    @Test
    public void getFilteredNotesList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredNotesList().remove(0));
    }

    @Test
    public void getFilteredStudent_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudentList().remove(0));
    }

    @Test
    public void getStudent_emptyStudentRecord_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.getStudent(1));
    }

    @Test
    public void getQuestion_emptyQuestionStorage_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.getQuestion(Index.fromOneBased(1)));
    }

    @Test
    public void setStudent_emptyStudentRecord_throwsStudentNotFoundException() {
        Student oldStudent = TypicalStudents.STUDENT_ONE;
        Student newStudent = TypicalStudents.STUDENT_FIVE;
        assertThrows(StudentNotFoundException.class, () -> modelManager.setStudent(oldStudent, newStudent));
    }

    @Test
    public void addStudentToGroup_emptyStudentRecord_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager
                .addStudentToGroup("test", 1, 1));
    }

    @Test
    public void removeStudentFromGroup_groupNotCreated_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> modelManager
                .removeStudentFromGroup("test", 1));
    }


    @Test
    public void hasStudent_studentInStudentRecord_returnsTrue() {
        Student student = new StudentBuilder().build();
        modelManager.addStudent(student);
        assertTrue(modelManager.hasStudent(student));
    }

    @Test
    public void hasQuestion_questionInQuestionStorage_returnsTrue() {
        Question question = new QuestionBuilder().build();
        modelManager.addQuestion(question);
        assertTrue(modelManager.hasQuestion(question));
    }

    @Test
    public void sameStudentRecord_equals() {
        StudentRecord studentRecord = new StudentRecord();
        modelManager.setStudentRecord(studentRecord);
        assertEquals(studentRecord, modelManager.getStudentRecord());
    }

    @Test
    public void deleteStudent_decreaseStudentRecordSize_returnsTrue() {
        Student student = new StudentBuilder().build();
        modelManager.addStudent(student);
        int initialSize = modelManager.getStudentRecord().getStudentList().size();
        modelManager.deleteStudent(student);
        assertEquals(initialSize - 1, modelManager.getStudentRecord().getStudentList().size());
    }

    @Test
    public void addStudent_increaseStudentRecordSize_returnsTrue() {
        Student student = new StudentBuilder().build();
        int initialSize = modelManager.getStudentRecord().getStudentList().size();
        modelManager.addStudent(student);
        assertEquals(initialSize + 1, modelManager.getStudentRecord().getStudentList().size());
    }

    @Test
    public void checkGroupExist_groupDoesNotExist_returnsFalse() {
        assertFalse(modelManager.checkGroupExists("Test group"));
    }

    @Test
    public void exportGroup_groupDoesNotExist_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> modelManager.exportGroup("Test Group"));
    }

    @Test
    public void deleteQuestion_decreaseQuestionStorageSize_returnsTrue() {
        Question question = new QuestionBuilder().build();
        modelManager.addQuestion(question);
        int initialSize = modelManager.getSavedQuestions().getSavedQuestions().size();
        modelManager.deleteQuestion(Index.fromOneBased(1));
        assertEquals(initialSize - 1, modelManager.getSavedQuestions().getSavedQuestions().size());
    }

    @Test
    public void addQuestion_increaseQuestionStorageSize_returnsTrue() {
        Question question = new QuestionBuilder().build();
        int initialSize = modelManager.getSavedQuestions().getSavedQuestions().size();
        modelManager.addQuestion(question);
        assertEquals(initialSize + 1, modelManager.getSavedQuestions().getSavedQuestions().size());
    }

    @Test
    public void sameQuestionStorage_equals() {
        SavedQuestions savedQuestions = new SavedQuestions();
        modelManager.setSavedQuestions(savedQuestions);
        assertEquals(savedQuestions, modelManager.getSavedQuestions());
    }

    @Test
    public void equals() {
        // same values -> returns true
        modelManager = new ModelManager();
        ModelManager modelManagerCopy = new ModelManager();
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));
    }
}
