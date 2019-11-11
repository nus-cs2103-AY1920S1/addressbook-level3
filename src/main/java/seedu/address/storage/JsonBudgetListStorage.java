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
import seedu.address.model.ReadOnlyBudgetList;

/**
 * A class to access BudgetList data stored as a json file on the hard disk.
 */
public class JsonBudgetListStorage implements BudgetListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBudgetListStorage.class);

    private Path filePath;

    public JsonBudgetListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBudgetListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBudgetList> readBudgetList() throws DataConversionException {
        return readBudgetList(filePath);
    }

    /**
     * Similar to {@link #readBudgetList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBudgetList> readBudgetList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBudgetList> jsonBudgetList = JsonUtil.readJsonFile(
                filePath, JsonSerializableBudgetList.class);
        if (!jsonBudgetList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBudgetList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBudgetList(ReadOnlyBudgetList addressBook) throws IOException {
        saveBudgetList(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveBudgetList(ReadOnlyBudgetList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBudgetList(ReadOnlyBudgetList budgetList, Path filePath) throws IOException {
        requireNonNull(budgetList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBudgetList(budgetList), filePath);
    }

}
