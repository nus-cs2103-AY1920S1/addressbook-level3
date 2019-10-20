package seedu.address.storage.wordbanks;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;

/**
 * Represents a storage for {@link WordBank}.
 */
public interface WordBankListStorage {
    /**
     * Returns the word bank list path file.
     */
    Path getWordBankListFilePath();

    /**
     * Returns an optional of ReadonlyWordBankList
     *
     * @return Optional of ReadonlyWordBankList
     */
    Optional<ReadOnlyWordBankList> getWordBankList();

    /**
     * Save a word bank into the file location
     *
     * @param filePath location of the data. Cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWordBank(ReadOnlyWordBank wordBank, Path filePath) throws IOException;
}
