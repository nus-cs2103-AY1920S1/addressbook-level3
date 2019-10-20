package seedu.address.storage.wordbanks;

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
    private ReadOnlyWordBankList readOnlyWordBankList;
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

    public Optional<ReadOnlyWordBank> getWordBank() throws DataConversionException {
        return getWordBank(wordBanksFilePath);
    }

    /**
     * Similar to {@link #getWordBank()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    private Optional<ReadOnlyWordBank> getWordBank(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableWordBank> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableWordBank.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            String pathName = filePath.toString();
            int len = wordBanksFilePath.toString().length();
            String wordBankName = pathName.substring(len + 1, pathName.length() - 5);
            return Optional.of(jsonAddressBook.get().toModelType(wordBankName));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    private void saveWordBank(ReadOnlyWordBank wordBank) throws IOException {
        saveWordBank(wordBank, wordBanksFilePath);
    }

    /**
     * Similar to {@link #saveWordBank(ReadOnlyWordBank)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveWordBank(ReadOnlyWordBank wordBank, Path filePath) throws IOException {
        requireNonNull(wordBank);
        requireNonNull(filePath);
        Path wordBankFilePath = Paths.get(filePath.toString(), wordBank.getName() + ".json");
        FileUtil.createIfMissing(wordBankFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableWordBank(wordBank), wordBankFilePath);
    }

    /**
     * Initialise word bank list on creation.
     */
    private void initWordBankList() {
        List<WordBank> wordBankList = new ArrayList<>();
        File dataDirectory = wordBanksFilePath.toFile();
        String[] pathArray = dataDirectory.list();

        for (int i = 0; i < pathArray.length; i++) {
            if (!pathArray[i].endsWith(".json")) {
                continue;
            }
            Path wordBankPath = Paths.get(wordBanksFilePath.toString(), pathArray[i]);
            try {
                System.out.println(wordBankPath.toString());
                Optional<ReadOnlyWordBank> wordBank = getWordBank(wordBankPath);
                ReadOnlyWordBank wb = wordBank.get();
                WordBank wbToAdd = (WordBank) wb;
                wordBankList.add(wbToAdd);
            } catch (DataConversionException e) {
                e.printStackTrace();
            }
        }
        this.readOnlyWordBankList = new WordBankList(wordBankList);
    }

    public Optional<ReadOnlyWordBankList> getWordBankList() {
        return Optional.of(readOnlyWordBankList);
    }
}
