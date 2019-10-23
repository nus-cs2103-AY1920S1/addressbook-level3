package seedu.address.storage.shoppinglist;

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
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyShoppingList;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonBoughtItemStorage implements BoughtListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonShoppingItemStorage.class);

    private Path filePath;

    public JsonBoughtItemStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBoughtListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readBoughtList() throws DataConversionException {
        return readBoughtList(filePath);
    }

    /**
     * Similar to {@link #readShoppingList(Path)}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readBoughtList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBoughtList> jsonBoughtList = JsonUtil.readJsonFile(
                filePath, JsonSerializableBoughtList.class);
        if (!jsonBoughtList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBoughtList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBoughtList(ReadOnlyAddressBook boughtList) throws IOException {
        saveBoughtList(boughtList, filePath);
    }

    /**
     * Similar to {@link #saveShoppingList(ReadOnlyShoppingList, Path)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBoughtList(ReadOnlyAddressBook boughtList, Path filePath) throws IOException {
        requireNonNull(boughtList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBoughtList(boughtList), filePath);
    }

}
