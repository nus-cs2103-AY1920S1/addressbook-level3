package seedu.address.storage.wordbanks;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
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
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWordBank(ReadOnlyWordBank wordBank) throws IOException;

    /**
     * Save a word bank into the file location
     *
     * @param filePath location of the data. Cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWordBank(ReadOnlyWordBank wordBank, Path filePath) throws IOException;

    /**
     * Add a word bank into the word bank list.
     *
     * @param wordBank data. Cannot be null.
     */
    void addWordBank(ReadOnlyWordBank wordBank);

    /**
     * Remove a word bank
     *
     * @param wordBankName data. Cannot be null.
     */
    void removeWordBank(String wordBankName);

    /**
     * Get a word bank
     *
     * @param wordBankPathFile data. Cannot be null.
     */
    Optional<ReadOnlyWordBank> createWordBank(Path wordBankPathFile) throws DataConversionException;
}
