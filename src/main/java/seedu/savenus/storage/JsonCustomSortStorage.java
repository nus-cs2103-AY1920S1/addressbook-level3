package seedu.savenus.storage;

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
import seedu.savenus.model.sorter.CustomSorter;

/**
 * A class to access CustomSorter data stored as a json file on the hard disk.
 */
public class JsonCustomSortStorage implements CustomSortStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMenuStorage.class);

    private Path filePath;

    public JsonCustomSortStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSortFilePath() {
        return filePath;
    }

    @Override
    public Optional<CustomSorter> readFields() throws DataConversionException {
        return readFields(filePath);
    }

    /**
     * Similar to {@link #readFields()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<CustomSorter> readFields(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCustomSort> jsonSort = JsonUtil.readJsonFile(
                filePath, JsonSerializableCustomSort.class);
        if (!jsonSort.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSort.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveFields(CustomSorter recs) throws IOException {
        saveFields(recs, filePath);
    }

    /**
     * Similar to {@link #saveFields(CustomSorter)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFields(CustomSorter sort, Path filePath) throws IOException {
        requireNonNull(sort);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCustomSort(sort), filePath);
    }
}
