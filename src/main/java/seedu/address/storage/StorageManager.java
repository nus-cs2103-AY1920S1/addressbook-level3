package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.event.ReadOnlyVEvents;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.quiz.ReadOnlyQuizzes;
import seedu.address.model.student.ReadOnlyStudentRecord;
import seedu.address.storage.event.EventExport;
import seedu.address.storage.event.EventStorage;
import seedu.address.storage.event.IcsEventExport;
import seedu.address.storage.note.NotesRecordStorage;
import seedu.address.storage.printable.NjoyPrintable;
import seedu.address.storage.question.QuestionStorage;
import seedu.address.storage.quiz.QuizStorage;
import seedu.address.storage.student.StudentRecordStorage;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private StudentRecordStorage studentRecordStorage;
    private QuestionStorage questionStorage;
    private QuizStorage quizStorage;
    private NotesRecordStorage notesRecordStorage;
    private UserPrefsStorage userPrefsStorage;
    private EventStorage eventStorage;
    private EventExport eventExport;

    public StorageManager(UserPrefsStorage userPrefsStorage,
                          StudentRecordStorage studentRecordStorage, QuestionStorage questionStorage,
                          QuizStorage quizStorage, NotesRecordStorage notesStorage, EventStorage eventStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.studentRecordStorage = studentRecordStorage;
        this.questionStorage = questionStorage;
        this.eventStorage = eventStorage;
        this.quizStorage = quizStorage;
        this.notesRecordStorage = notesStorage;
        this.eventExport = new IcsEventExport();
    }

    //region UserPrefs
    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }
    //endregion

    //region StudentRecord methods
    @Override
    public Path getStudentRecordFilePath() {
        return studentRecordStorage.getStudentRecordFilePath();
    }

    @Override
    public Optional<ReadOnlyStudentRecord> readStudentRecord()
        throws DataConversionException, IOException {
        return readStudentRecord(studentRecordStorage.getStudentRecordFilePath());
    }

    @Override
    public Optional<ReadOnlyStudentRecord> readStudentRecord(Path filePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read student data from file: " + filePath);
        return studentRecordStorage.readStudentRecord(filePath);
    }

    @Override
    public void saveStudentRecord(ReadOnlyStudentRecord studentRecord) throws IOException {
        saveStudentRecord(studentRecord, studentRecordStorage.getStudentRecordFilePath());
    }

    @Override
    public void saveStudentRecord(ReadOnlyStudentRecord studentRecord, Path filePath)
        throws IOException {
        logger.fine("Attempting to write to student data file: " + filePath);
        studentRecordStorage.saveStudentRecord(studentRecord, filePath);
    }
    //endregion

    //region Question methods
    @Override
    public Path getSavedQuestionsFilePath() {
        return questionStorage.getSavedQuestionsFilePath();
    }

    @Override
    public Optional<ReadOnlyQuestions> readQuestions() throws DataConversionException, IOException {
        return readQuestions(questionStorage.getSavedQuestionsFilePath());
    }

    @Override
    public Optional<ReadOnlyQuestions> readQuestions(Path filePath)
        throws DataConversionException, IOException {
        logger.fine("Attempting to read question data from file: " + filePath);
        return questionStorage.readQuestions(filePath);
    }

    @Override
    public void saveQuestions(ReadOnlyQuestions questions) throws IOException {
        saveQuestions(questions, questionStorage.getSavedQuestionsFilePath());
    }

    @Override
    public void saveQuestions(ReadOnlyQuestions questions, Path filePath) throws IOException {
        logger.fine("Attempting to write to questions data file: " + filePath);
        questionStorage.saveQuestions(questions, filePath);
    }
    //endregion

    //region Quiz methods
    @Override
    public Path getSavedQuizzesFilePath() {
        return quizStorage.getSavedQuizzesFilePath();
    }

    @Override
    public Optional<ReadOnlyQuizzes> readQuizzes() throws DataConversionException, IOException {
        return readQuizzes(quizStorage.getSavedQuizzesFilePath());
    }

    @Override
    public Optional<ReadOnlyQuizzes> readQuizzes(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read quiz data from file: " + filePath);
        return quizStorage.readQuizzes(filePath);
    }

    @Override
    public void saveQuizzes(ReadOnlyQuizzes quizzes) throws IOException {
        saveQuizzes(quizzes, quizStorage.getSavedQuizzesFilePath());
    }

    @Override
    public void saveQuizzes(ReadOnlyQuizzes quizzes, Path filePath) throws IOException {
        logger.fine("Attempting to write to quiz file: " + filePath);
        quizStorage.saveQuizzes(quizzes, filePath);
    }
    //endregion

    //region NotesRecord methods
    @Override
    public Path getNotesRecordFilePath() {
        return notesRecordStorage.getNotesRecordFilePath();
    }

    @Override
    public Optional<ReadOnlyNotesRecord> readNotesRecord() throws DataConversionException, IOException {
        return readNotesRecord(notesRecordStorage.getNotesRecordFilePath());
    }

    @Override
    public Optional<ReadOnlyNotesRecord> readNotesRecord(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read student data from file: " + filePath);
        return notesRecordStorage.readNotesRecord(filePath);
    }

    @Override
    public void saveNotesRecord(ReadOnlyNotesRecord notesRecord) throws IOException {
        saveNotesRecord(notesRecord, notesRecordStorage.getNotesRecordFilePath());
    }

    @Override
    public void saveNotesRecord(ReadOnlyNotesRecord notesRecord, Path filePath) throws IOException {
        logger.fine("Attempting to write to student data file: " + filePath);
        notesRecordStorage.saveNotesRecord(notesRecord, filePath);
    }
    //endregion


    //region EventRecord methods
    @Override
    public Path getEventRecordFilePath() {
        return eventStorage.getEventRecordFilePath();
    }

    @Override
    public Optional<ReadOnlyEvents> readEvents() throws DataConversionException, IOException {
        return readEvents(eventStorage.getEventRecordFilePath());
    }

    @Override
    public Optional<ReadOnlyEvents> readEvents(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read events data from file: " + filePath);
        return eventStorage.readEvents(filePath);
    }

    @Override
    public void saveEvents(ReadOnlyEvents events) throws IOException {
        saveEvents(events, eventStorage.getEventRecordFilePath());
    }

    @Override
    public void saveEvents(ReadOnlyEvents events, Path filePath) throws IOException {
        logger.fine("Attempting to write to events data file: " + filePath);
        eventStorage.saveEvents(events, filePath);
    }

    @Override
    public String exportEvent(ReadOnlyVEvents eventRecord) throws IOException {
        return eventExport.exportEvent(eventRecord);
    }
    //endRegion

    @Override
    public void savePrintable(NjoyPrintable printable) throws IOException {
        printable.savePrintable();
    }
}
