package seedu.ifridge.storage.unitdictionary;

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
import seedu.ifridge.model.UnitDictionary;



/**
 * A class to access UnitDictionary data stored as a json file on the hard disk.
 */
public class JsonUnitDictionaryStorage implements UnitDictionaryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUnitDictionaryStorage.class);

    private Path filePath;

    public JsonUnitDictionaryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getUnitDictionaryFilePath() {
        return filePath;
    }

    @Override
    public Optional<UnitDictionary> readUnitDictionary() throws DataConversionException {
        return readUnitDictionary(filePath);
    }

    /**
     * Similar to {@link #readUnitDictionary()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<UnitDictionary> readUnitDictionary(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableUnitDictionary> jsonUnitDictionary = JsonUtil.readJsonFile(
                filePath, JsonSerializableUnitDictionary.class);
        if (!jsonUnitDictionary.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonUnitDictionary.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUnitDictionary(UnitDictionary unitDictionary) throws IOException {
        saveUnitDictionary(unitDictionary, filePath);
    }

    /**
     * Similar to {@link #saveUnitDictionary(UnitDictionary)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveUnitDictionary(UnitDictionary unitDictionary, Path filePath) throws IOException {
        requireNonNull(unitDictionary);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableUnitDictionary(unitDictionary), filePath);
    }

}
