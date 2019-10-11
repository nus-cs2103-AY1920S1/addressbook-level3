package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyExpenseList;

/**
 * A class to access ExpenseList data stored as a json file on the hard disk.
 */
public class JsonExpenseListStorage implements ExpenseListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonExpenseListStorage.class);

    private Path filePath;

    public JsonExpenseListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getExpenseListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyExpenseList> readExpenseList() throws DataConversionException {
        return readExpenseList(filePath);
    }

    /**
     * Similar to {@link #readExpenseList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyExpenseList> readExpenseList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableExpenseList> jsonExpenseList = JsonUtil.readJsonFile(
                filePath, JsonSerializableExpenseList.class);
        if (!jsonExpenseList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonExpenseList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveExpenseList(ReadOnlyExpenseList addressBook) throws IOException {
        saveExpenseList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveExpenseList(ReadOnlyExpenseList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveExpenseList(ReadOnlyExpenseList addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableExpenseList(addressBook), filePath);
    }

}
