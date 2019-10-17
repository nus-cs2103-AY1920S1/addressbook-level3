package tagline.storage.tag;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tagline.commons.core.LogsCenter;
import tagline.commons.exceptions.DataConversionException;
import tagline.commons.exceptions.IllegalValueException;
import tagline.commons.util.FileUtil;
import tagline.commons.util.JsonUtil;
import tagline.model.tag.ReadOnlyTagList;
import tagline.storage.note.JsonNoteBookStorage;

/**
 * A class to access TagList data stored as a json file on the hard disk.
 */
public class JsonTagListStorage implements TagListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonNoteBookStorage.class);

    private Path filePath;

    public JsonTagListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTagListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTagList> readTagList() throws DataConversionException {
        return readTagList(filePath);
    }

    /**
     * Similar to {@link #readTagList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTagList> readTagList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSeriazableTagList> jsonTagList = JsonUtil.readJsonFile(
            filePath, JsonSeriazableTagList.class);
        if (!jsonTagList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTagList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTagList(ReadOnlyTagList tagList) throws IOException {
        saveTagList(tagList, filePath);
    }

    /**
     * Similar to {@link #saveTagList(ReadOnlyTagList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTagList(ReadOnlyTagList tagList, Path filePath) throws IOException {
        requireNonNull(tagList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSeriazableTagList(tagList), filePath);
    }

}
