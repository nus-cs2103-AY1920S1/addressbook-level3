package seedu.address.storage.quiz;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.quiz.ReadOnlyQuizBook;
import seedu.address.model.quiz.ReadOnlyUserPrefs;
import seedu.address.model.quiz.UserPrefs;



/**
 * API of the Storage component
 */
public interface Storage extends QuizBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyQuizBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyQuizBook addressBook) throws IOException;

}
