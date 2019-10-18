package seedu.address.storage.note;

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
import seedu.address.model.note.ReadOnlyNotesRecord;

/**
 * A class to access Notes data stored as a json file on the hard disk.
 */
public class JsonNotesRecordStorage implements NotesRecordStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonNotesRecordStorage.class);

    private Path filePath;

    public JsonNotesRecordStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getNotesRecordFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyNotesRecord> readNotesRecord() throws DataConversionException {
        return readNotesRecord(filePath);
    }

    /**
     * Similar to {@link #readNotesRecord()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyNotesRecord> readNotesRecord(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableNotesRecord> jsonNotesRecord = JsonUtil.readJsonFile(
                filePath, JsonSerializableNotesRecord.class);
        if (!jsonNotesRecord.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonNotesRecord.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveNotesRecord(ReadOnlyNotesRecord notesRecord) throws IOException {
        saveNotesRecord(notesRecord, filePath);
    }

    /**
     * Similar to {@link #saveNotesRecord(ReadOnlyNotesRecord)}.
     *
     * @param filePath location of the data.
     */
    public void saveNotesRecord(ReadOnlyNotesRecord notesRecord, Path filePath) throws IOException {
        requireNonNull(notesRecord);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableNotesRecord(notesRecord), filePath);
    }

}
