package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.quiz.ReadOnlyQuizzes;
import seedu.address.model.student.ReadOnlyStudentRecord;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.event.JsonEventStorage;
import seedu.address.storage.note.JsonNotesRecordStorage;
import seedu.address.storage.question.JsonQuestionStorage;
import seedu.address.storage.quiz.JsonQuizStorage;
import seedu.address.storage.student.JsonStudentRecordStorage;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonStudentRecordStorage studentRecordStorage =
                new JsonStudentRecordStorage(temporaryFolder.resolve("studentRecord.json"));
        JsonQuizStorage quizStorage = new JsonQuizStorage(temporaryFolder.resolve("quiz.json"));
        JsonQuestionStorage questionStorage = new JsonQuestionStorage(temporaryFolder.resolve("question.json"));
        JsonNotesRecordStorage notesRecordStorage =
                new JsonNotesRecordStorage(temporaryFolder.resolve("notesRecord.json"));
        JsonEventStorage eventStorage = new JsonEventStorage(temporaryFolder.resolve("events.json"));
        StorageManager storage = new StorageManager(userPrefsStorage, studentRecordStorage,
                questionStorage, quizStorage, notesRecordStorage, eventStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }


    @Test
    public void execute_parseExceptionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertParseException(deleteCommand, MESSAGE_UNKNOWN_COMMAND);
    }


    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredStudentList().remove(0));
    }

    @Test
    public void getFilteredNotesList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredNotesList().remove(0));
    }

    @Test
    public void getAllQuestions_modifyList_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> logic.getAllQuestions().remove(0));
    }

    @Test
    public void getSlideShowQuestions_modifyList_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> logic.getSlideshowQuestions().remove(0));
    }

    @Test
    public void getSearchQuestions_modifyList_throwsIndexOutOfBoundsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> logic.getSearchQuestions().remove(1));
    }

    @Test
    public void getProcessedStatistics_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getProcessedStatistics().remove(0));
    }


    @Test
    public void getQuestionsInQuiz_modifyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> logic.getQuestionsInQuiz().remove(0));
    }

    @Test
    public void getAnswersInQuiz_modifyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> logic.getAnswersInQuiz().remove(0));
    }

    @Test
    public void getQuestionsAndAnswersInQuiz_modifyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> logic.getQuestionsAndAnswersInQuiz().remove(0));
    }

    @Test
    public void getVEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getVEventList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException, IOException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getStudentRecord(), model.getSavedQuestions(),
                model.getSavedQuizzes(), model.getNotesRecord(), model.getEventRecord(),
                model.getStatisticsRecord(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }


    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called for the student record.
     */
    private static class JsonStudentRecordIoExceptionThrowingStub extends JsonStudentRecordStorage {
        private JsonStudentRecordIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveStudentRecord(ReadOnlyStudentRecord studentRecord, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called for the quiz storage.
     */
    private static class JsonQuizIoExceptionThrowingStub extends JsonQuizStorage {
        private JsonQuizIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveQuizzes(ReadOnlyQuizzes quizzes, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called for the question storage.
     */
    private static class JsonQuestionIoExceptionThrowingStub extends JsonQuestionStorage {
        private JsonQuestionIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveQuestions(ReadOnlyQuestions questions, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called for the notes storage.
     */
    private static class JsonNotesStorageIoExceptionThrowingStub extends JsonNotesRecordStorage {
        private JsonNotesStorageIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveNotesRecord(ReadOnlyNotesRecord notesRecord, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called for the events storage.
     */
    private static class JsonEventsStorageIoExceptionThrowingStub extends JsonEventStorage {
        private JsonEventsStorageIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveEvents(ReadOnlyEvents events, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
