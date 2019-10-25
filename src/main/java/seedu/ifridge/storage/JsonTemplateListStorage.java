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
import seedu.ifridge.model.ReadOnlyTemplateList;

/**
 * A class to access GroceryList data stored as a json file on the hard disk.
 */
public class JsonTemplateListStorage implements TemplateListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTemplateListStorage.class);

    private Path filePath;

    public JsonTemplateListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTemplateListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTemplateList> readTemplateList() throws DataConversionException {
        return readTemplateList(filePath);
    }

    /**
     * Similar to {@link #readTemplateList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTemplateList> readTemplateList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTemplateList> jsonTemplateList = JsonUtil.readJsonFile(
                filePath, JsonSerializableTemplateList.class);
        if (!jsonTemplateList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTemplateList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTemplateList(ReadOnlyTemplateList templateList) throws IOException {
        saveTemplateList(templateList, filePath);
    }

    /**
     * Similar to {@link #saveTemplateList(ReadOnlyTemplateList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTemplateList(ReadOnlyTemplateList templateList, Path filePath) throws IOException {
        requireNonNull(templateList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTemplateList(templateList), filePath);
    }

}
