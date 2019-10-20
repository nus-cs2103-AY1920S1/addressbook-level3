package seedu.address.storage;

import static java.util.Objects.requireNonNull;

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
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;
import seedu.address.model.wordbanklist.WordBankList;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonWordBankListStorage implements WordBankListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWordBankListStorage.class);
    private ReadOnlyWordBankList rowbl;

    private Path filePath;

    public JsonWordBankListStorage(Path filePath) {
        this.filePath = filePath;
        initWordBankList();
    }

    public Path getWordBankListFilePath() {
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

        Optional<JsonSerializableWordBank> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableWordBank.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            String pathName = filePath.toString();
            String wordBankName = pathName.substring(5, pathName.length() - 5);
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
        JsonUtil.saveJsonFile(new JsonSerializableWordBank(addressBook), filePath);
    }

    public void initWordBankList() {
        List<WordBank> wordBankList = new ArrayList<>();
        String pathString = "data/";
        File dataDirectory = new File(pathString);
        String[] pathArray = dataDirectory.list();

        for (int i = 0; i < pathArray.length; i++) {
            if (!pathArray[i].endsWith(".json")) {
                continue;
            }
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
        this.rowbl = new WordBankList(wordBankList);
    }

    public Optional<ReadOnlyWordBankList> getWordBankList() {
        return Optional.of(rowbl);
    }
}
