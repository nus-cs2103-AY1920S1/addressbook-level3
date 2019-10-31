package com.typee.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.commons.exceptions.DataConversionException;
import com.typee.commons.exceptions.IllegalValueException;
import com.typee.commons.util.FileUtil;
import com.typee.commons.util.JsonUtil;
import com.typee.model.ReadOnlyEngagementList;

/**
 * A class to access EngagementList data stored as a json file on the hard disk.
 */
public class JsonEngagementListStorage implements EngagementListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEngagementListStorage.class);

    private Path filePath;

    public JsonEngagementListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEngagementListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEngagementList> readEngagementList() throws DataConversionException {
        return readEngagementList(filePath);
    }

    /**
     * Similar to {@link #readEngagementList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEngagementList> readEngagementList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableEngagementList> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableEngagementList.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEngagementList(ReadOnlyEngagementList engagementList) throws IOException {
        saveEngagementList(engagementList, filePath);
    }

    /**
     * Similar to {@link #saveEngagementList(ReadOnlyEngagementList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEngagementList(ReadOnlyEngagementList engagementList, Path filePath) throws IOException {
        requireNonNull(engagementList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEngagementList(engagementList), filePath);
    }

}
