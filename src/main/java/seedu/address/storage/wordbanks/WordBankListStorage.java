// @@author chrischenhui
package seedu.address.storage.wordbanks;

import java.nio.file.Path;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbank.exceptions.WordBankNotFoundException;
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;

/**
 * Represents a storage for {@link WordBank}.
 * All arguments passed in should be already be validated by the respective command parsers.
 */
public interface WordBankListStorage {
    /**
     * Retrieves the WordBankList that WordBankListStorage holds.
     */
    Optional<ReadOnlyWordBankList> getWordBankList();

    /**
     * Creates a word bank, add into the internal list, and save it into storage.
     *
     * @param wordBankName cannot be null.
     */
    void createWordBank(String wordBankName);

    /**
     * Retrieves the specified word bank, delete from storage, and then remove from internal list.
     *
     * @param wordBankName cannot be null.
     */
    void removeWordBank(String wordBankName);

    /**
     * Creates the word bank specified by the file path, add to internal list, and then add to storage.
     *
     * @param wordBankName cannot be null.
     * @param filePath cannot be null.
     */
    void importWordBank(String wordBankName, Path filePath)
            throws DataConversionException, WordBankNotFoundException, IllegalValueException;

    /**
     * Retrieves the word bank, add to internal list, then add to storage.
     *
     * @param wordBankName cannot be null.
     * @param filePath cannot be null.
     */
    void exportWordBank(String wordBankName, Path filePath);

    /**
     * Updates any changes to word banks that were changed.
     *
     * @param wordBank cannot be null.
     */
    void updateWordBank(WordBank wordBank);

    /**
     * Returns an observable list of word banks.
     *
     * @return observable list of word banks.
     */
    ObservableList<WordBank> getFilteredWordBankList();

    /**
     * Retrieves the file path of word banks.
     *
     * @return word bank file path.
     */
    Path getWordBanksFilePath();

    /**
     * Returns word bank using it's name.
     *
     * @param name word bank name.
     * @return word bank.
     */
    WordBank getWordBankFromName(String name);
}
