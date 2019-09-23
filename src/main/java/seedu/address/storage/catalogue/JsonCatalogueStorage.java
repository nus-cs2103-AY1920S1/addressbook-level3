package seedu.address.storage.catalogue;

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
import seedu.address.model.ReadOnlyCatalogue;

/**
 * A class to access Catalogue data stored as a json file on the hard disk.
 */
public class JsonCatalogueStorage implements CatalogueStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCatalogueStorage.class);

    private Path filePath;

    public JsonCatalogueStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCatalogueFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCatalogue> readCatalogue() throws DataConversionException {
        return readCatalogue(filePath);
    }

    /**
     * Similar to {@link #readCatalogue()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCatalogue> readCatalogue(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCatalogue> jsonCatalogue = JsonUtil.readJsonFile(
                filePath, JsonSerializableCatalogue.class);
        if (!jsonCatalogue.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCatalogue.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCatalogue(ReadOnlyCatalogue Catalogue) throws IOException {
        saveCatalogue(Catalogue, filePath);
    }

    /**
     * Similar to {@link #saveCatalogue(ReadOnlyCatalogue)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCatalogue(ReadOnlyCatalogue catalogue, Path filePath) throws IOException {
        requireNonNull(catalogue);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCatalogue(catalogue), filePath);
    }

}
