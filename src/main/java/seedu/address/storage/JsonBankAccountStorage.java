package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyBankAccount;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonBankAccountStorage implements BankAccountStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBankAccountStorage.class);

    private Path filePath;

    public JsonBankAccountStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getBankAccountFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBankAccount> readBankAccount() throws DataConversionException, IOException {
        return readBankAccount(filePath);
    }

    /**
     * Similar to {@link #readBankAccount()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBankAccount> readBankAccount(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBankAccount> jsonBankAccount = JsonUtil.readJsonFile(
                filePath, JsonSerializableBankAccount.class);
        if (!jsonBankAccount.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBankAccount.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBankAccount(ReadOnlyBankAccount bankAccount) throws IOException {
        saveBankAccount(bankAccount, filePath);
    }

    /**
     * Similar to {@link #saveBankAccount(ReadOnlyBankAccount)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBankAccount(ReadOnlyBankAccount bankAccount, Path filePath) throws IOException {
        requireNonNull(bankAccount);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBankAccount(bankAccount), filePath);
    }

}
