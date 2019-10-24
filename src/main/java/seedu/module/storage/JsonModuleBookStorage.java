package seedu.module.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.module.commons.core.LogsCenter;
import seedu.module.commons.exceptions.DataConversionException;
import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.commons.util.FileUtil;
import seedu.module.commons.util.JsonUtil;
import seedu.module.model.ModuleBook;
import seedu.module.model.ReadOnlyModuleBook;
import seedu.module.model.module.ArchivedModuleList;

/**
 * A class to access ModuleBook data stored as a json file on the hard disk.
 */
public class JsonModuleBookStorage implements ModuleBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonModuleBookStorage.class);
    private static final String ARCHIVED_MODULES_RESOURCE_FILE_NAME = "data/archivedModules.json";

    private Path filePath;

    public JsonModuleBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getModuleBookFilePath() {
        return filePath;
    }

    @Override
    public ReadOnlyModuleBook readModuleBook() {
        return readModuleBook(filePath);
    }

    /**
     * Similar to {@link #readModuleBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public ReadOnlyModuleBook readModuleBook(Path filePath) {
        requireNonNull(filePath);

        ArchivedModuleList archivedModules = JsonArchivedModuleList.readArchivedModules(
            ARCHIVED_MODULES_RESOURCE_FILE_NAME);
        ModuleBook moduleBook;

        try {
            Optional<JsonSerializableModuleBook> jsonModuleBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableModuleBook.class);
            if (!jsonModuleBook.isPresent()) {
                throw new Exception();
            }
            moduleBook = jsonModuleBook.get().toModelType(archivedModules);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ModuleBook");
            moduleBook = new ModuleBook(archivedModules);
        } catch (IllegalValueException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ModuleBook");
            moduleBook = new ModuleBook(archivedModules);
        } catch (Exception e) {
            //TODO Unique Exception
            logger.warning("Data file not found. Will be starting with an empty ModuleBook");
            moduleBook = new ModuleBook(archivedModules);
        }

        return moduleBook;
    }

    @Override
    public void saveModuleBook(ReadOnlyModuleBook moduleBook) throws IOException {
        saveModuleBook(moduleBook, filePath);
    }

    /**
     * Similar to {@link #saveModuleBook(ReadOnlyModuleBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveModuleBook(ReadOnlyModuleBook moduleBook, Path filePath) throws IOException {
        requireNonNull(moduleBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableModuleBook(moduleBook), filePath);
    }

}
