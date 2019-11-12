package io.xpire.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.commons.util.FileUtil;
import io.xpire.commons.util.JsonUtil;
import io.xpire.model.ReadOnlyListView;
import javafx.util.Pair;

/**
 * A class to access Xpire data stored as a json file on the hard disk.
 */
public class JsonListStorage implements ListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonListStorage.class);

    private Path filePath;

    public JsonListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getListFilePath() {
        return this.filePath;
    }

    @Override
    public Pair<Optional<ReadOnlyListView>, Optional<ReadOnlyListView>> readList() throws DataConversionException {
        return readList(this.filePath);
    }

    /**
     * Similar to {@link #readList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Pair<Optional<ReadOnlyListView>, Optional<ReadOnlyListView>> readList(Path filePath)
            throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonSerializableList> jsonTracker = JsonUtil.readJsonFile(
                filePath, JsonSerializableList.class);
        Pair<Optional<ReadOnlyListView>, Optional<ReadOnlyListView>> pair;
        if (jsonTracker.isEmpty()) {
            pair = new Pair<>(Optional.empty(), Optional.empty());
            return pair;
        }
        try {
            if (jsonTracker.get().toModelType().length != 2) {
                throw new ArrayIndexOutOfBoundsException();
            }
            ReadOnlyListView xpire = jsonTracker.get().toModelType()[0];
            ReadOnlyListView replenishList = jsonTracker.get().toModelType()[1];
            return new Pair<>(Optional.of(xpire), Optional.of(replenishList));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveList(ReadOnlyListView[] lists) throws IOException {
        saveList(lists, this.filePath);
    }

    /**
     * Similar to {@link #saveList(ReadOnlyListView[])}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveList(ReadOnlyListView[] lists, Path filePath) throws IOException {
        requireNonNull(lists);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableList(lists), filePath);
    }
}
