package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AccountBook;
import seedu.address.model.account.Account;

/**
 * A class to access AccountBook data stored as a json file on the hard disk.
 */
public class JsonAccountStorage implements AccountStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAccountStorage.class);

    private static final Path accountBookFilePath = Paths.get("data", "accountslist.json");
    private static File dataFolder = new File("data");
    private static File file = new File("data/accountslist.json");

    private Path filePath;

    public JsonAccountStorage() {
        this.filePath = accountBookFilePath;
    }

    public JsonAccountStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAccountBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<AccountBook> getAccountsList() throws DataConversionException, IOException {
        return getAccountsList(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<AccountBook> getAccountsList(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        dataFolder.mkdir();

        if (file.createNewFile()) {
            logger.info("New File created.");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("{\"accounts\" : [] }");
            writer.close();
        } else {
            logger.info("Checking with file.");
        }

        Optional<JsonSerializableAccountBook> jsonAccount = JsonUtil.readJsonFile(
                filePath, JsonSerializableAccountBook.class);
        if (!jsonAccount.isPresent()) {
            throw new FileNotFoundException("No accounts registered. Please register first!");
        }

        try {
            return Optional.of(jsonAccount.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }



    @Override
    public void saveAccount(Account account) throws IOException {
        saveAccount(account, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAccount(Account account, Path filePath) throws IOException {
        requireNonNull(account);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        try {
            AccountBook acctBook = getAccountsList().get();
            acctBook.addAccount(account);
            JsonUtil.saveJsonFile(new JsonSerializableAccountBook(acctBook), filePath);
        } catch (DataConversionException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            //throw new DataConversionException(e);
        }

    }

}
