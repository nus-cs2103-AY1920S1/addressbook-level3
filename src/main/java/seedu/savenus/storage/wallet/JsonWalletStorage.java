package seedu.savenus.storage.wallet;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.commons.util.FileUtil;
import seedu.savenus.commons.util.JsonUtil;
import seedu.savenus.model.wallet.Wallet;

//@@author raikonen
/**
 * A class to access Wallet data stored as a json file on the hard disk.
 */
public class JsonWalletStorage implements WalletStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWalletStorage.class);

    private Path filePath;

    public JsonWalletStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getWalletFilePath() {
        return filePath;
    }

    @Override
    public Optional<Wallet> readWallet() throws DataConversionException, IOException {
        return readWallet(filePath);
    }

    @Override
    public Optional<Wallet> readWallet(Path filePath)
            throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableWallet> jsonWallet = JsonUtil.readJsonFile(filePath,
                JsonSerializableWallet.class);

        if (jsonWallet.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWallet.get().toModelType());
        } catch (IllegalValueException e) {
            logger.info(("Illegal values found in " + filePath + e.getMessage()));
            throw new DataConversionException(e);
        }
    }

    /**
     * Write into the user's wallet from the unmodifiable wallet.
     *
     * @param wallet unmodifiable wallet of the user.
     * @throws IOException from {@link #saveWallet(Wallet, Path)}
     */
    @Override
    public void saveWallet(Wallet wallet) throws IOException {
        saveWallet(wallet, filePath);
    }

    /**
     * Similar to {@link #saveWallet(Wallet)}.
     *
     * @param wallet wallet of user must be provided.
     * @param filePath location of the wallet data cannot be null.
     * @throws IOException when writing to the file fails.
     */
    @Override
    public void saveWallet(Wallet wallet, Path filePath) throws IOException {
        requireNonNull(wallet);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWallet(wallet), filePath);
    }
}
