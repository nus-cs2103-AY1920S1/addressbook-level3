package seedu.address.storage;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.statistics.GameStatistics;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWordBank> readAddressBook() throws DataConversionException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWordBank> readAddressBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            String wordBankName = filePath.toString();
            return Optional.of(jsonAddressBook.get().toModelType(wordBankName));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyWordBank addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyWordBank)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(ReadOnlyWordBank addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook, addressBook.getId()), filePath);
    }

    @Override
    public void saveAddressBookStatistics(ReadOnlyWordBank addressBook, GameStatistics statistics) throws IOException {
        saveAddressBookStatistics(addressBook, statistics, filePath);
    }

    /**
     * Similar to {@link #saveAddressBookStatistics(ReadOnlyWordBank, GameStatistics)}.
     *
     * @param statistics statistics to be saved, cannot be null.
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveAddressBookStatistics(ReadOnlyWordBank addressBook, GameStatistics statistics, Path filePath) throws IOException {
        requireAllNonNull(addressBook, statistics, filePath);

        if (!FileUtil.isFileExists(filePath)) {
            throw new IOException("Cannot save statistics to a word bank that does not exist.");
        }

        // todo SAVE ADDRESS BOOK STATISTICS
    }

    public Optional<List<WordBank>> getWordBankList() {
        List<WordBank> wordBankList = new ArrayList<>();
        String pathString = "data/";
        File dataDirectory = new File(pathString);
        String[] pathArray = dataDirectory.list();

        for (int i = 0; i < pathArray.length; i++) {
            String wordBankPathString = "data/" + pathArray[i];
            Path wordBankPath = Paths.get(wordBankPathString);
            try {
                Optional<ReadOnlyWordBank> wordBank = readAddressBook(wordBankPath);
                ReadOnlyWordBank wb = wordBank.get();
                WordBank wbToAdd = (WordBank) wb;
                wordBankList.add(wbToAdd);
            } catch (DataConversionException e) {
                e.printStackTrace();
            }
        }
        return Optional.of(wordBankList);
    }
}
