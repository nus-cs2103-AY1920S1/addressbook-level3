package seedu.savenus.storage.wallet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.wallet.Wallet;

//@@author raikonen
/**
 * Represents a storage for {@link Wallet}.
 */
public interface WalletStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getWalletFilePath();

    /**
     * Returns Wallet data as a {@link Wallet}.
     * If storage file is not found, returns {@code Optional.empty()}.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Wallet> readWallet() throws DataConversionException, IOException;

    /**
     * Implement another read method to read from filePath.
     */
    Optional<Wallet> readWallet(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link Wallet} to the storage.
     * @param wallet cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWallet(Wallet wallet) throws IOException;

    /**
     * @see #saveWallet(Wallet)
     */
    void saveWallet(Wallet wallet, Path filePath) throws IOException;
}
