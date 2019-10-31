package seedu.savenus.storage.alias;

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
import seedu.savenus.model.alias.AliasList;

/**
 * A class to access AliasList data stored as a json file on the hard disk.
 */
public class JsonAliasListStorage implements AliasStorage {

    private static final Logger logger = LogsCenter.getLogger(AliasStorage.class);

    private Path filePath;

    public JsonAliasListStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getAliasFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<AliasList> readList() throws DataConversionException, IOException {
        return readList(filePath);
    }

    @Override
    public Optional<AliasList> readList(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableAliasList> jsonSort = JsonUtil.readJsonFile(
                filePath, JsonSerializableAliasList.class);
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
    public void saveList(AliasList list) throws IOException {
        saveList(list, filePath);
    }

    @Override
    public void saveList(AliasList list, Path filePath) throws IOException {
        requireNonNull(list);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAliasList(list), filePath);
    }
}
