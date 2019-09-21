package seedu.tarence.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.model.ReadOnlyStudentBook;
import seedu.tarence.model.ReadOnlyUserPrefs;
import seedu.tarence.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends StudentBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getStudentBookFilePath();

    @Override
    Optional<ReadOnlyStudentBook> readStudentBook() throws DataConversionException, IOException;

    @Override
    void saveStudentBook(ReadOnlyStudentBook studentBook) throws IOException;

}
