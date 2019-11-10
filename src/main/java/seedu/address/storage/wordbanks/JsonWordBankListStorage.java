// @@author chrischenhui
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

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbank.exceptions.WordBankNotFoundException;
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;
import seedu.address.model.wordbanklist.WordBankList;

/**
 * A class to access Dukemon word bank data stored as a json file on the hard disk.
 */
public class JsonWordBankListStorage implements WordBankListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWordBankListStorage.class);
    private WordBankList wordBankList;
    private Path wordBanksFilePath; // default : data/wordBanks

    /**
     * Creates information related to word banks.
     * It also initialises the folder and sample data if necessary.
     *
     * @param filePath of storage. By default, it is at data folder.
     * @param isSampleInitiated checks to see if sample data has been initiated before.
     * @throws DataConversionException if there is corrupted file.
     * @throws IllegalValueException if word banks contain duplicate names or it's card contain duplicate values.
     */
    public JsonWordBankListStorage(Path filePath, boolean isSampleInitiated)
            throws DataConversionException, IllegalValueException {
        initDataByDefault(filePath, isSampleInitiated);
        initWordBankList();
    }

    /**
     * Creates information related to word banks.
     * Allows you to specify the folder to contain the word banks.
     * It also initialises the folder and sample data if necessary.
     *
     * @param filePath of storage. By default, it is at data folder.
     * @param folder specifies another layer of folder to contain word banks.
     * @param isSampleInitiated checks to see if sample data has been initiated before.
     * @throws DataConversionException if there is corrupted file.
     * @throws IllegalValueException if word banks contain duplicate names or it's card contain duplicate values.
     */
    public JsonWordBankListStorage(Path filePath, String folder, boolean isSampleInitiated)
            throws DataConversionException, IllegalValueException {
        initData(filePath, folder, isSampleInitiated);
        initWordBankList();
    }

    /**
     /**
     * Creates the wordBanks folder if it has not been initialised.
     * By default, it is located at data/wordBanks.
     * Initialises some default word banks upon first initialisation.
     *
     * @param filePath of storage. By default, it is data.
     * @param isSampleInitiated checks to see if sample data has been initiated before.
     */
    private void initDataByDefault(Path filePath, boolean isSampleInitiated) {
        initData(filePath, "wordBanks", isSampleInitiated);
    }

    /**
     * Creates the wordBanks folder if it has not been initialised.
     * By default, it is located at data/wordBanks.
     * Also creates a sample.json file if there are no word banks when initialising.
     *
     * @param filePath of storage. By default, it is data.
     */

    /**
     * Creates the wordBanks folder if it has not been initialised.
     * By default, it is located at data/wordBanks.
     * Also creates a sample.json file if there are no word banks when initialising.
     *
     * @param filePath of storage. By default, it is data.
     * @param folder specifies another layer of folder to contain word banks.
     * @param isSampleInitiated checks to see if sample data has been initiated before.
     */
    private void initData(Path filePath, String folder, boolean isSampleInitiated) {
        if (folder.equals("")) {
            wordBanksFilePath = filePath;
        } else {
            wordBanksFilePath = Paths.get(filePath.toString(), folder);
        }

        try {
            if (!wordBanksFilePath.toFile().exists()) {
                Files.createDirectories(wordBanksFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!isSampleInitiated) {
            loadDefaultData();
        }
    }

    /**
     * Loads the default word banks for the user the first time Dukemon is launched.
     */
    private void loadDefaultData() {
        WordBank pokemonWb = SampleDataUtil.getPokemonWordBank();
        WordBank arithmeticWb = SampleDataUtil.getArithmeticWordBank();
        WordBank triviaWb = SampleDataUtil.getTriviaWordBank();
        WordBank cs2103tWb = SampleDataUtil.getCS2103tWordBank();
        WordBank graphWb = SampleDataUtil.getGraphWordBank();

        saveWordBank(pokemonWb);
        saveWordBank(arithmeticWb);
        saveWordBank(triviaWb);
        saveWordBank(cs2103tWb);
        saveWordBank(graphWb);
    }

    /**
     * Initialise word bank list from the word banks directory on creation.
     * All json files will initialise a word bank.
     *
     * @throws DataConversionException if word bank file is corrupted.
     * @throws IllegalValueException if the word banks contain duplicate cards.
     */
    private void initWordBankList() throws DataConversionException, IllegalValueException {
        List<WordBank> wordBankList = new ArrayList<>();
        File wordBanksDirectory = wordBanksFilePath.toFile();
        String[] pathArray = wordBanksDirectory.list();

        for (int i = 0; i < pathArray.length; i++) {
            if (pathArray[i].endsWith(".json")) {
                Path wordBankPath = Paths.get(wordBanksFilePath.toString(), pathArray[i]);
                ReadOnlyWordBank readOnlyWordBank = null;
                try {
                    readOnlyWordBank = jsonToWordBank(wordBankPath).get();
                } catch (IllegalValueException e) {
                    logger.info("Failed to initialise word bank list");
                    e.printStackTrace();
                    throw e;
                }
                WordBank wbToAdd = (WordBank) readOnlyWordBank;
                wordBankList.add(wbToAdd);
            }
        }
        this.wordBankList = new WordBankList(wordBankList);
    }

    /**
     * Creates a word bank object from the specified json file given as filePath.
     *
     * @param filePath location of the .json word bank file. Cannot be null.
     * @return an optional ReadOnlyWordBank.
     * @throws DataConversionException if word bank file is corrupted.
     * @throws IllegalValueException if the word banks contain duplicate cards.
     */
    public Optional<ReadOnlyWordBank> jsonToWordBank(Path filePath)
            throws DataConversionException, IllegalValueException {
        try {
            requireNonNull(filePath);
            Optional<JsonSerializableWordBank> jsonWordBank = JsonUtil.readJsonFile(
                    filePath, JsonSerializableWordBank.class);
            if (jsonWordBank.isEmpty()) {
                return Optional.empty();
            }
            String wordBankName = retrieveWordBankNameFromPath(filePath);
            return Optional.of(jsonWordBank.get().toModelTypeWithName(wordBankName));

        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw ive;
        }
    }

    /**
     * Retrieves word bank name from the path file.
     *
     * @param path the path of word bank file.
     * @return the word bank name.
     */
    private String retrieveWordBankNameFromPath(Path path) {
        String pathName = path.toString();
        int len = path.getParent().toString().length();
        return pathName.substring(len + 1, pathName.length() - ".json".length());
    }

    /**
     * Save a word bank into the default file location, data/wordBanks.
     * Typically used by Export command, where user writes to their system.
     *
     * @param wordBank word bank.
     */
    public void saveWordBank(ReadOnlyWordBank wordBank) {
        saveWordBank(wordBank, wordBanksFilePath);
    }

    /**
     * Save a word bank into the specified file location.
     * Typically used by Export command, where user writes to their system.
     *
     * @param filePath location of the data. Cannot be null. E.g. ~/downloads.
     */
    void saveWordBank(ReadOnlyWordBank wordBank, Path filePath) {
        try {
            requireNonNull(wordBank);
            requireNonNull(filePath);
            Path wordBankFilePath = Paths.get(filePath.toString(), wordBank.getName() + ".json");
            FileUtil.createIfMissing(wordBankFilePath);
            JsonUtil.saveJsonFile(new JsonSerializableWordBank(wordBank), wordBankFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a word bank into the internal list.
     * Called by createWordBank and importWordBank method.
     *
     * @param wordBank data. Cannot be null.
     */
    private void addWordBank(ReadOnlyWordBank wordBank) {
        WordBankList wbl = wordBankList;
        wbl.addWordBank(wordBank);
    }

    /**
     * Deletes a word bank from the storage and from the internal list.
     * Called by removeWordBank method.
     *
     * @param wordBank data. Cannot be null.
     */
    private void deleteWordBank(ReadOnlyWordBank wordBank) {
        Path filePath = Paths.get(wordBanksFilePath.toString(), wordBank.getName() + ".json");
        File toDelete = filePath.toFile();
        if (toDelete.exists()) {
            toDelete.delete();
        }
        WordBankList wbl = wordBankList;
        wbl.removeWordBank(wordBank);
    }

    /**
     * Retrieves the WordBankList that WordBankListStorage holds.
     */
    @Override
    public Optional<ReadOnlyWordBankList> getWordBankList() {
        return Optional.of(wordBankList);
    }

    /**
     * Creates a word bank, add into the internal list, and save it into storage.
     *
     * @param wordBankName cannot be null.
     */
    @Override
    public void createWordBank(String wordBankName) {
        WordBank wb = new WordBank(wordBankName);
        addWordBank(wb);
        saveWordBank(wb);
    }

    /**
     * Retrieves the specified word bank, delete from storage, and then remove from internal list.
     *
     * @param wordBankName cannot be null.
     */
    @Override
    public void removeWordBank(String wordBankName) {
        WordBank wb = wordBankList.getWordBankFromName(wordBankName);
        deleteWordBank(wb);
    }

    /**
     * Creates the word bank specified by the file path, add to internal list, and then add to storage.
     *
     * @param wordBankName cannot be null.
     * @param filePath cannot be null.
     */
    @Override
    public void importWordBank(String wordBankName, Path filePath)
            throws DataConversionException, WordBankNotFoundException, IllegalValueException {

        Path finalPath = Paths.get(filePath.toString(), wordBankName + ".json");
        Optional<ReadOnlyWordBank> optWb = jsonToWordBank(finalPath);

        if (optWb.isPresent()) {
            WordBank wb = (WordBank) optWb.get();
            addWordBank(wb);
            saveWordBank(wb);

        } else {
            throw new WordBankNotFoundException();
        }
    }


    /**
     * Retrieves the word bank, add to internal list, then add to storage.
     *
     * @param wordBankName cannot be null.
     * @param filePath cannot be null.
     */
    @Override
    public void exportWordBank(String wordBankName, Path filePath) {
        WordBank wb = wordBankList.getWordBankFromName(wordBankName);
        saveWordBank(wb, filePath);
    }

    /**
     * Updates any changes to word banks that were changed.
     *
     * @param wordBank cannot be null.
     */
    @Override
    public void updateWordBank(WordBank wordBank) {
        saveWordBank(wordBank);
    }

    /**
     * Returns an observable list of word banks.
     *
     * @return observable list of word banks
     */
    @Override
    public ObservableList<WordBank> getFilteredWordBankList() {
        return wordBankList.getFilteredWordBankList();
    }

    /**
     * Retrieves the file path of word banks.
     *
     * @return word bank file path.
     */
    @Override
    public Path getWordBanksFilePath() {
        return wordBanksFilePath;
    }

    /**
     * Returns word bank using it's name.
     *
     * @param name word bank name.
     * @return word bank.
     */
    @Override
    public WordBank getWordBankFromName(String name) {
        return wordBankList.getWordBankFromName(name);
    }
}
