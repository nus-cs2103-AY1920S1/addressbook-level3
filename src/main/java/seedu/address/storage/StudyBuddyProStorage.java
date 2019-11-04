package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.StudyBuddyPro;

/**
 * Represents a storage for {@link StudyBuddyPro}.
 */

public interface StudyBuddyProStorage {
    // TO RENAME THE INTERFACE NAME
    /**
     * Returns the file path of the data file.
     */
    Path getCheatSheetFilePath();
    Path getFlashcardFilePath();
    Path getNoteFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyStudyBuddyPro}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyBuddyPro> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyStudyBuddyPro> readAddressBook(Path flashcardFilePath, Path noteFilePath,
                                                    Path cheatsheetFilePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyStudyBuddyPro} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyStudyBuddyPro addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyStudyBuddyPro)
     */
    void saveAddressBook(ReadOnlyStudyBuddyPro addressBook, Path flashcardFilePath, Path noteFilePath,
                         Path cheatsheetFilePath) throws IOException;
}

