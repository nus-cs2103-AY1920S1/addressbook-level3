package seedu.eatme.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eatme.commons.core.LogsCenter;
import seedu.eatme.commons.exceptions.DataConversionException;
import seedu.eatme.commons.exceptions.IllegalValueException;
import seedu.eatme.commons.util.FileUtil;
import seedu.eatme.commons.util.JsonUtil;
import seedu.eatme.model.ReadOnlyEateryList;

/**
 * A class to access EateryList data stored as a json file on the hard disk.
 */
public class JsonEateryListStorage implements EateryListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEateryListStorage.class);

    private Path filePath;

    public JsonEateryListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEateryListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEateryList> readEateryList() throws DataConversionException {
        return readEateryList(filePath);
    }

    /**
     * Similar to {@link #readEateryList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEateryList> readEateryList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableEateryList> jsonEateryList = JsonUtil.readJsonFile(
                filePath, JsonSerializableEateryList.class);
        if (!jsonEateryList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEateryList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEateryList(ReadOnlyEateryList eateryList) throws IOException {
        saveEateryList(eateryList, filePath);
    }

    /**
     * Similar to {@link #saveEateryList(ReadOnlyEateryList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEateryList(ReadOnlyEateryList eateryList, Path filePath) throws IOException {
        requireNonNull(eateryList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEateryList(eateryList), filePath);
    }

}
