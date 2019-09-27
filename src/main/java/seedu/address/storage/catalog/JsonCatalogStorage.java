package seedu.address.storage.catalog;

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
import seedu.address.model.ReadOnlyCatalog;

/**
 * A class to access Catalog data stored as a json file on the hard disk.
 */
public class JsonCatalogStorage implements CatalogStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCatalogStorage.class);

    private Path filePath;

    public JsonCatalogStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCatalogFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCatalog> readCatalog() throws DataConversionException {
        return readCatalog(filePath);
    }

    /**
     * Similar to {@link #readCatalog()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCatalog> readCatalog(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCatalog> jsonCatalog = JsonUtil.readJsonFile(
                filePath, JsonSerializableCatalog.class);
        if (!jsonCatalog.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCatalog.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCatalog(ReadOnlyCatalog Catalog) throws IOException {
        saveCatalog(Catalog, filePath);
    }

    /**
     * Similar to {@link #saveCatalog(ReadOnlyCatalog)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCatalog(ReadOnlyCatalog catalog, Path filePath) throws IOException {
        requireNonNull(catalog);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCatalog(catalog), filePath);
    }

}
