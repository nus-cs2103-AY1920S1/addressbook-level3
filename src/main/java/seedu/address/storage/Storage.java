package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.model.student.ReadOnlyStudentRecord;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.storage.note.NotesRecordStorage;
import seedu.address.storage.question.QuestionStorage;
import seedu.address.storage.student.StudentRecordStorage;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, StudentRecordStorage,
    QuestionStorage, NotesRecordStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    //region AddressBook methods
    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;
    //endregion

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

    //region NotesRecord methods
    @Override
    Path getNotesRecordFilePath();

    @Override
    Optional<ReadOnlyNotesRecord> readNotesRecord() throws DataConversionException, IOException;

    @Override
    void saveNotesRecord(ReadOnlyNotesRecord notesRecord) throws IOException;
    //endregion
}
