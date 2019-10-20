package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    private Path wordBanksFilePath;

    public JsonWordBankListStorage(Path dataFilePath) {
        Path dataPath = dataFilePath;
        Path wordBanksFilePath = Paths.get(dataFilePath.toString(), "wordbanks");
        try {
            if (!dataPath.toFile().exists()) {
                Files.createDirectory(dataPath);
            }
            if (!wordBanksFilePath.toFile().exists()) {
                Files.createDirectory(wordBanksFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.wordBanksFilePath = wordBanksFilePath;
        initWordBankList();
    }

    public Path getWordBankListFilePath() {
        return wordBanksFilePath;
    }

    public Optional<ReadOnlyWordBank> readAddressBook() throws DataConversionException {
        return readAddressBook(wordBanksFilePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    private Optional<ReadOnlyWordBank> readAddressBook(Path filePath) throws DataConversionException {
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
    public void saveWordBanks(ReadOnlyWordBank addressBook) throws IOException {
        saveWordBanks(addressBook, wordBanksFilePath);
    }

    /**
     * Similar to {@link #saveWordBanks(ReadOnlyWordBank)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveWordBanks(ReadOnlyWordBank addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWordBank(addressBook), filePath);
    }

    /**
     * Initialise word bank list on creation.
     */
    public void initWordBankList() {
        List<WordBank> wordBankList = new ArrayList<>();
        File dataDirectory = wordBanksFilePath.toFile();
        String[] pathArray = dataDirectory.list();

        for (int i = 0; i < pathArray.length; i++) {
            if (!pathArray[i].endsWith(".json")) {
                continue;
            }
            Path wordBankPath = Paths.get(wordBanksFilePath.toString() + pathArray[i]);
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
