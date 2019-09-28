package thrift.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import thrift.commons.core.LogsCenter;
import thrift.commons.exceptions.DataConversionException;
import thrift.commons.exceptions.IllegalValueException;
import thrift.commons.util.FileUtil;
import thrift.commons.util.JsonUtil;
import thrift.model.ReadOnlyThrift;

/**
 * A class to access THRIFT data stored as a json file on the hard disk.
 */
public class JsonThriftStorage implements ThriftStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonThriftStorage.class);

    private Path filePath;

    public JsonThriftStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getThriftFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyThrift> readThrift() throws DataConversionException {
        return readThrift(filePath);
    }

    /**
     * Similar to {@link #readThrift()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyThrift> readThrift(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableThrift> jsonThrift = JsonUtil.readJsonFile(
                filePath, JsonSerializableThrift.class);
        if (!jsonThrift.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonThrift.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveThrift(ReadOnlyThrift thrift) throws IOException {
        saveThrift(thrift, filePath);
    }

    /**
     * Similar to {@link #saveThrift(ReadOnlyThrift)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveThrift(ReadOnlyThrift thrift, Path filePath) throws IOException {
        requireNonNull(thrift);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableThrift(thrift), filePath);
    }

}
