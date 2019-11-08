package seedu.address.storage.quiz;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.quiz.ReadOnlyQuizBook;


/**
 * Represents a storage for {@link seedu.address.model.quiz}.
 */
public interface QuizBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressQuizBook data as a {@link ReadOnlyQuizBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyQuizBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyQuizBook> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyQuizBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyQuizBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyQuizBook)
     */
    void saveAddressBook(ReadOnlyQuizBook addressBook, Path filePath) throws IOException;

}
