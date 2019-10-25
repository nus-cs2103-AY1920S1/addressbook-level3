package seedu.address.storage.wordbanks;


import seedu.address.model.wordbank.WordBank;

import java.nio.file.Path;

/**
 * Represents a storage for {@link WordBank}.
 * All arguments passed in should be already be validated by the respective command parsers.
 */
public interface WordBankListStorage {
    /**
     * Called by CreateCommand to create a word bank, add into the internal list, and
     * save it into storage.
     *
     * @param wordBankName cannot be null.
     */
    void createWordBank(String wordBankName);

    /**
     * Called by RemoveCommand to retrieve the specified word bank, delete from storage,
     * and then remove from internal list.
     *
     * @param wordBankName cannot be null.
     */
    void removeWordBank(String wordBankName);

    /**
     * Called by ImportCommand to create the word bank specified by the file path,
     * add to internal list, and then add to storage.
     *
     * @param filePath cannot be null.
     */
    void importWordBank(Path filePath);

    /**
     * Called by ExportCommand, to retrieve the word bank, add to internal list,
     * then add to storage.
     *
     * @param wordBankName cannot be null.
     * @param filePath cannot be null.
     */
    void exportWordBank(String wordBankName, Path filePath);

    /**
     * Called by CardCommand to automatically update any changes to word banks that were
     * manipulated in ModelManager.
     *
     * @param wordBank cannot be null.
     */
    void updateWordBank(WordBank wordBank);
}
