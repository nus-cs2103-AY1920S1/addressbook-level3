package seedu.ifridge.storage.shoppinglist;

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
import seedu.ifridge.model.ReadOnlyShoppingList;

/**
 * A class to access GroceryList data stored as a json file on the hard disk.
 */
public class JsonShoppingItemStorage implements ShoppingListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonShoppingItemStorage.class);

    private Path filePath;

    public JsonShoppingItemStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getShoppingListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyShoppingList> readShoppingList() throws DataConversionException {
        return readShoppingList(filePath);
    }

    /**
     * Similar to {@link #readShoppingList(Path)}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyShoppingList> readShoppingList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableShoppingList> jsonShoppingList = JsonUtil.readJsonFile(
                filePath, JsonSerializableShoppingList.class);
        if (!jsonShoppingList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonShoppingList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveShoppingList(ReadOnlyShoppingList shoppingList) throws IOException {
        saveShoppingList(shoppingList, filePath);
    }

    /**
     * Similar to {@link #saveShoppingList(ReadOnlyShoppingList, Path)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveShoppingList(ReadOnlyShoppingList shoppingList, Path filePath) throws IOException {
        requireNonNull(shoppingList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableShoppingList(shoppingList), filePath);
    }

}
