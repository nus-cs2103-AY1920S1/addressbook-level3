package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.event.TypicalEvents.getTypicalEventsRecord;
import static seedu.address.testutil.note.TypicalNotes.getTypicalNotesRecord;
import static seedu.address.testutil.question.TypicalQuestions.getTypicalSavedQuestions;
import static seedu.address.testutil.quiz.TypicalSavedQuizzes.getSavedQuizzes;
import static seedu.address.testutil.student.TypicalStudents.getTypicalStudentRecord;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventRecord;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.question.SavedQuestions;
import seedu.address.model.quiz.ReadOnlyQuizzes;
import seedu.address.model.quiz.SavedQuizzes;
import seedu.address.model.student.ReadOnlyStudentRecord;
import seedu.address.model.student.StudentRecord;
import seedu.address.storage.event.JsonEventStorage;
import seedu.address.storage.note.JsonNotesRecordStorage;
import seedu.address.storage.question.JsonQuestionStorage;
import seedu.address.storage.quiz.JsonQuizStorage;
import seedu.address.storage.student.JsonStudentRecordStorage;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonStudentRecordStorage studentRecordStorage =
                new JsonStudentRecordStorage(getTempFilePath("studentRecord"));
        JsonQuestionStorage questionStorage =
                new JsonQuestionStorage(getTempFilePath("questionStorage"));
        JsonQuizStorage quizStorage =
                new JsonQuizStorage(getTempFilePath("quizStorage"));
        JsonNotesRecordStorage notesRecordStorage =
                new JsonNotesRecordStorage(getTempFilePath("notesRecordStorage"));
        JsonEventStorage eventStorage =
                new JsonEventStorage(getTempFilePath("eventStorage"));
        storageManager = new StorageManager(userPrefsStorage, studentRecordStorage, questionStorage, quizStorage,
                notesRecordStorage , eventStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void eventRecordReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonEventStorage} class.
         */
        EventRecord original = getTypicalEventsRecord();
        storageManager.saveEvents(original);
        ReadOnlyEvents retrieved = storageManager.readEvents().get();
        assertEquals(original, new EventRecord(retrieved));
    }

    @Test
    public void notesRecordStorageReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonNotesRecordStorage} class.
         */
        NotesRecord original = getTypicalNotesRecord();
        storageManager.saveNotesRecord(original);
        ReadOnlyNotesRecord retrieved = storageManager.readNotesRecord().get();
        assertEquals(original, new NotesRecord(retrieved));
    }

    @Test
    public void savedQuestionsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonQuestionStorage} class.
         */
        SavedQuestions original = getTypicalSavedQuestions();
        storageManager.saveQuestions(original);
        ReadOnlyQuestions retrieved = storageManager.readQuestions().get();
        assertEquals(original, new SavedQuestions(retrieved));
    }

    @Test
    public void quizStorageReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonQuizStorage} class.
         */
        SavedQuizzes original = getSavedQuizzes();
        storageManager.saveQuizzes(original);
        ReadOnlyQuizzes retrieved = storageManager.readQuizzes().get();
        assertEquals(original, new SavedQuizzes(retrieved));
    }

    @Test
    public void studentRecordReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonStudentRecordStorage} class.
         */
        StudentRecord original = getTypicalStudentRecord();
        storageManager.saveStudentRecord(original);
        ReadOnlyStudentRecord retrieved = storageManager.readStudentRecord().get();
        assertEquals(original, new StudentRecord(retrieved));
    }
    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

    @Test
    public void getEventRecordFilePath() {
        assertNotNull(storageManager.getEventRecordFilePath());
    }

    @Test
    public void getNotesRecordFilePath() {
        assertNotNull(storageManager.getNotesRecordFilePath());
    }

    @Test
    public void getSavedQuestionsFilePath() {
        assertNotNull(storageManager.getSavedQuestionsFilePath());
    }

    @Test
    public void getSavedQuizzesFilePath() {
        assertNotNull(storageManager.getSavedQuizzesFilePath());
    }

    @Test
    public void getStudentRecordFilePath() {
        assertNotNull(storageManager.getStudentRecordFilePath());
    }

}
