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
import seedu.address.model.util.SampleDataUtil;
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
    private Path wordBanksFilePath; // default : data/wordbanks

    /**
     * Storage that contains all information related to word banks.
     * It also initialises the folder and sample data if necessary.
     *
     * @param filePath of storage. By default, it is data.
     */
    public JsonWordBankListStorage(Path filePath) {
        initData(filePath);
        initWordBankList();
    }

    /**
     * Initialise word bank list from the word banks directory on creation
     * All .json files will initialise a word bank.
     */
    private void initWordBankList() {
        List<WordBank> wordBankList = new ArrayList<>();
        File wordBanksDirectory = wordBanksFilePath.toFile();
        String[] pathArray = wordBanksDirectory.list();

        for (int i = 0; i < pathArray.length; i++) {
            if (pathArray[i].endsWith(".json")) {
                Path wordBankPath = Paths.get(wordBanksFilePath.toString(), pathArray[i]);
                try {
                    Optional<ReadOnlyWordBank> wordBank = createWordBank(wordBankPath);
                    ReadOnlyWordBank wb = wordBank.get();
                    WordBank wbToAdd = (WordBank) wb;
                    wordBankList.add(wbToAdd);
                } catch (DataConversionException e) {
                    e.printStackTrace();
                }
            }
        }
        this.readOnlyWordBankList = new WordBankList(wordBankList);
    }

    /**
     * Creates the wordbanks folder if it has not been initialised.
     * By default, it is located at data/wordbanks
     * Also creates a sample.json file if there are no word banks when initialising.
     *
     * @param filePath of storage. By default, it is data.
     */
    private void initData(Path filePath) {
        wordBanksFilePath = Paths.get(filePath.toString(), "wordbanks");
        try {
            if (!filePath.toFile().exists()) {
                Files.createDirectory(filePath);
            }
            if (!wordBanksFilePath.toFile().exists()) {
                Files.createDirectory(wordBanksFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File wordBanksDirectory = wordBanksFilePath.toFile();
        String[] wordBanks = wordBanksDirectory.list();
        boolean haveSampleWordBank = false;
        for (int i = 0; i < wordBanks.length; i++) {
            if (wordBanks[i].equals(SampleDataUtil.getName())) {
                haveSampleWordBank = true;
                break;
            }
        }

        if (!haveSampleWordBank) {
            WordBank sampleWb = SampleDataUtil.getSampleWordBank();
            try {
                saveWordBank(sampleWb);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Save a word bank into the default file location.
     *
     * @throws IOException if there was any problem writing to the file.
     */
    public void saveWordBank(ReadOnlyWordBank wordBank) throws IOException {
        saveWordBank(wordBank, wordBanksFilePath);
    }

    /**
     * Save a word bank into the specified file location.
     * Typically used by Export command, where user writes to their system.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public void saveWordBank(ReadOnlyWordBank wordBank, Path filePath) throws IOException {
        requireNonNull(wordBank);
        requireNonNull(filePath);
        Path wordBankFilePath = Paths.get(filePath.toString(), wordBank.getName() + ".json");
        FileUtil.createIfMissing(wordBankFilePath);
        JsonUtil.saveJsonFile(new JsonSerializableWordBank(wordBank), wordBankFilePath);
    }

    /**
     * Creates a word bank object from the specified .json file given as filePath.
     *
     * @param filePath location of the .json word bank file. Cannot be null.
     * @throws DataConversionException if the .json file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyWordBank> createWordBank(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableWordBank> jsonWordBank = JsonUtil.readJsonFile(
                filePath, JsonSerializableWordBank.class);
        if (jsonWordBank.isEmpty()) {
            return Optional.empty();
        }

        try {
            String pathName = filePath.toString();
            int len = filePath.getParent().toString().length();
            String wordBankName = pathName.substring(len + 1, pathName.length() - ".json".length());
            return Optional.of(jsonWordBank.get().toModelType(wordBankName));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Adds a word bank into the word bank list.
     * Saves the word bank afterwards.
     *
     * @param wordBank data. Cannot be null.
     */
    @Override
    public void addWordBank(ReadOnlyWordBank wordBank) {
        WordBankList wbl = (WordBankList) readOnlyWordBankList;
        wbl.addBank(wordBank);
        try {
            saveWordBank(wordBank);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeWordBank(String wordBankName) {
        Path filePath = Paths.get(wordBanksFilePath.toString(), wordBankName + ".json");
        File toDelete = filePath.toFile();
        WordBank wb = readOnlyWordBankList.getWordBank(wordBankName);
        if (toDelete.exists()) {
            toDelete.delete();
        }
        WordBankList wbl = ((WordBankList) readOnlyWordBankList);
        wbl.removeWordBank(wb);
    }

    private void deleteWordBank() {

    }

    public Optional<ReadOnlyWordBankList> getWordBankList() {
        return Optional.of(readOnlyWordBankList);
    }
}
