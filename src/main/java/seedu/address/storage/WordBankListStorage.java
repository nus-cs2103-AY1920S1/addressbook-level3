package seedu.address.storage;

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
     * Returns the word bank list path file.
     */
    Path getWordBankListFilePath();

    /**
     * Saves the given {@link ReadOnlyWordBank} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWordBanks(ReadOnlyWordBank addressBook) throws IOException;

    /**
     * @see #saveWordBanks(ReadOnlyWordBank)
     */
    void saveWordBanks(ReadOnlyWordBank addressBook, Path filePath) throws IOException;

    Optional<ReadOnlyWordBankList> getWordBankList();
}
