package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
import seedu.address.storage.note.NotesRecordStorage;
import seedu.address.storage.printable.NjoyPrintable;
import seedu.address.storage.question.QuestionStorage;
import seedu.address.storage.quiz.QuizStorage;
import seedu.address.storage.student.StudentRecordStorage;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage, StudentRecordStorage,
        QuestionStorage, QuizStorage, NotesRecordStorage, EventStorage, EventExport {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;


    //region StudentRecord methods
    @Override
    Path getStudentRecordFilePath();

    @Override
    Optional<ReadOnlyStudentRecord> readStudentRecord() throws DataConversionException, IOException;

    @Override
    void saveStudentRecord(ReadOnlyStudentRecord studentRecord) throws IOException;
    //endregion

    //region Question methods
    @Override
    Path getSavedQuestionsFilePath();

    @Override
    Optional<ReadOnlyQuestions> readQuestions() throws DataConversionException, IOException;

    @Override
    void saveQuestions(ReadOnlyQuestions savedQuestions) throws IOException;

    //endregion

    // region Event Methods

    @Override
    Path getEventRecordFilePath();

    @Override
    Optional<ReadOnlyEvents> readEvents() throws DataConversionException, IOException;

    @Override
    void saveEvents(ReadOnlyEvents events) throws IOException;

    @Override
    String exportEvent(ReadOnlyVEvents eventRecord) throws IOException;

    //endregion

    //region Quiz methods

    @Override
    Path getSavedQuizzesFilePath();

    @Override
    Optional<ReadOnlyQuizzes> readQuizzes() throws DataConversionException, IOException;

    @Override
    void saveQuizzes(ReadOnlyQuizzes savedQuizzes) throws IOException;
    //endregion

    //region NotesRecord methods
    @Override
    Path getNotesRecordFilePath();

    @Override
    Optional<ReadOnlyNotesRecord> readNotesRecord() throws DataConversionException, IOException;

    @Override
    void saveNotesRecord(ReadOnlyNotesRecord notesRecord) throws IOException;
    //endregion

    void savePrintable(NjoyPrintable printable) throws IOException;
}
