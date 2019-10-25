package seedu.ifridge.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.ifridge.commons.core.LogsCenter;
import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.commons.exceptions.IllegalValueException;
import seedu.ifridge.commons.util.FileUtil;
import seedu.ifridge.commons.util.JsonUtil;
import seedu.ifridge.model.ReadOnlyGroceryList;

/**
 * A class to access GroceryList data stored as a json file on the hard disk.
 */
public class JsonGroceryListStorage implements GroceryListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGroceryListStorage.class);

    private Path filePath;

    public JsonGroceryListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGroceryListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGroceryList> readGroceryList() throws DataConversionException {
        return readGroceryList(filePath);
    }

    /**
     * Similar to {@link #readGroceryList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyGroceryList> readGroceryList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableGroceryList> jsonGroceryList = JsonUtil.readJsonFile(
                filePath, JsonSerializableGroceryList.class);
        if (!jsonGroceryList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonGroceryList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveGroceryList(ReadOnlyGroceryList groceryList) throws IOException {
        saveGroceryList(groceryList, filePath);
    }

    /**
<<<<<<< HEAD:src/main/java/seedu/address/storage/JsonGroceryListStorage.java
     * Similar to {@link #saveGroceryList(ReadOnlyGroceryList, Path)}.
=======
     * Similar to {@link #saveGroceryList(ReadOnlyGroceryList)}.
>>>>>>> master:src/main/java/seedu/address/storage/JsonGroceryListStorage.java
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGroceryList(ReadOnlyGroceryList groceryList, Path filePath) throws IOException {
        requireNonNull(groceryList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGroceryList(groceryList), filePath);
    }

}
