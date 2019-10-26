package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyKeyboardFlashCards;

/**
 * A class to access KeyboardFlashCards data stored as a json file on the hard disk.
 */
public class JsonKeyboardFlashCardsStorage implements KeyboardFlashCardsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonKeyboardFlashCardsStorage.class);

    private Path filePath;

    public JsonKeyboardFlashCardsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getKeyboardFlashCardsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyKeyboardFlashCards> readKeyboardFlashCards() throws DataConversionException {
        return readKeyboardFlashCards(filePath);
    }

    /**
     * Similar to {@link #readKeyboardFlashCards()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyKeyboardFlashCards> readKeyboardFlashCards(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableKeyboardFlashCards> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableKeyboardFlashCards.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyKeyboardFlashCards addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyKeyboardFlashCards)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyKeyboardFlashCards addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableKeyboardFlashCards(addressBook), filePath);
    }

}
