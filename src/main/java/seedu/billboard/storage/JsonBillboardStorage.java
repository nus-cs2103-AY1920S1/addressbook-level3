package seedu.billboard.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.billboard.commons.core.LogsCenter;
import seedu.billboard.commons.exceptions.DataConversionException;
import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.commons.util.FileUtil;
import seedu.billboard.commons.util.JsonUtil;
import seedu.billboard.model.ReadOnlyBillboard;

/**
 * A class to access Billboard data stored as a json file on the hard disk.
 */
public class JsonBillboardStorage implements BillboardStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBillboardStorage.class);

    private Path filePath;

    public JsonBillboardStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBillboardFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBillboard> readBillboard() throws DataConversionException {
        return readBillboard(getBillboardFilePath());
    }

    /**
     * Similar to {@link #readBillboard()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBillboard> readBillboard(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBillboard> jsonBillboard = JsonUtil.readJsonFile(
                filePath, JsonSerializableBillboard.class);
        if (jsonBillboard.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBillboard.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBillboard(ReadOnlyBillboard billboard) throws IOException {
        saveBillboard(billboard, getBillboardFilePath());
    }

    /**
     * Similar to {@link #saveBillboard(ReadOnlyBillboard)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBillboard(ReadOnlyBillboard billboard, Path filePath) throws IOException {
        requireNonNull(billboard);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBillboard(billboard), filePath);
    }

}
