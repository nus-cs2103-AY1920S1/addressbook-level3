package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.KeyboardFlashCards;
import seedu.address.model.ReadOnlyKeyboardFlashCards;

/**
 * Represents a storage for {@link KeyboardFlashCards}.
 */
public interface KeyboardFlashCardsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getKeyboardFlashCardsFilePath();

    /**
     * Returns KeyboardFlashCards data as a {@link ReadOnlyKeyboardFlashCards}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyKeyboardFlashCards> readKeyboardFlashCards() throws DataConversionException, IOException;

    /**
     * @see #getKeyboardFlashCardsFilePath()
     */
    Optional<ReadOnlyKeyboardFlashCards> readKeyboardFlashCards(Path filePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyKeyboardFlashCards} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyKeyboardFlashCards addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyKeyboardFlashCards)
     */
    void saveAddressBook(ReadOnlyKeyboardFlashCards addressBook, Path filePath) throws IOException;

}
