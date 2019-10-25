package seedu.address.storage.wordbanks;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.card.exceptions.DuplicateWordBankException;
import seedu.address.model.card.exceptions.WordBankNotFoundException;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;

/**
 * Represents a storage for {@link WordBank}.
 */
public interface WordBankListStorage {
    /**
     * Returns an optional of ReadonlyWordBankList
     *
     * @return Optional of ReadonlyWordBankList
     */
    Optional<ReadOnlyWordBankList> getWordBankList();

    /**
     * Save a word bank into the default file location.
     *
     * @param wordBank cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWordBank(ReadOnlyWordBank wordBank) throws IOException;

    /**
     * Save a word bank into the default file location.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWordBank(ReadOnlyWordBank wordBank, Path filePath) throws IOException;

    /**
     * Add a word bank into the word bank list.
     *
     * @param wordBank cannot be null.
     * @throws DuplicateWordBankException if duplicate exists.
     */
    void addWordBank(ReadOnlyWordBank wordBank) throws DuplicateWordBankException;

    /**
     * Remove a word bank
     *
     * @param wordBankName cannot be null.
     * @throws WordBankNotFoundException if word bank not found.
     */
    void removeWordBank(String wordBankName) throws WordBankNotFoundException;


    /**
     * Get a word bank
     *
     * @param wordBankPathFile data. Cannot be null.
     */
    Optional<ReadOnlyWordBank> createWordBank(Path wordBankPathFile) throws DataConversionException;
}
