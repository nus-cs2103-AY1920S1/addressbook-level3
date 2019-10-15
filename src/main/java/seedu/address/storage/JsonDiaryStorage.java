package seedu.address.storage;

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
import seedu.address.model.ReadOnlyDiary;

/**
 * A class to access DiaryRecords data stored as a json file on the hard disk.
 */
public class JsonDiaryStorage implements DiaryStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonDiaryStorage.class);

    private Path filePath;

    public JsonDiaryStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDiaryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDiary> readDiary() throws DataConversionException {
        return readDiary(filePath);
    }

    /**
     * Similar to {@link #readDiary()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDiary> readDiary(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableDiaryCatalogue> jsonDiary = JsonUtil.readJsonFile(
                filePath, JsonSerializableDiaryCatalogue.class);
        if (!jsonDiary.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDiary.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDiary(ReadOnlyDiary diary) throws IOException {
        saveDiary(diary, filePath);
    }

    /**
     * Similar to {@link #saveDiary(ReadOnlyDiary)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveDiary(ReadOnlyDiary diary, Path filePath) throws IOException {
        requireNonNull(diary);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDiaryCatalogue(diary), filePath);
    }

}
