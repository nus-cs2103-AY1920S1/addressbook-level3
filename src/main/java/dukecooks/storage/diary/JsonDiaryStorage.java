package dukecooks.storage.diary;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.commons.util.FileUtil;
import dukecooks.commons.util.JsonUtil;
import dukecooks.model.diary.ReadOnlyDiary;

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
