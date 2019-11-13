package seedu.planner.storage.accommodation;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.commons.util.FileUtil;
import seedu.planner.commons.util.JsonUtil;
import seedu.planner.model.ReadOnlyAccommodation;
//@@author OneArmyj
/**
 * A class to access Accommodation data stored as a json file on the hard disk.
 */
public class JsonAccommodationStorage implements AccommodationStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAccommodationStorage.class);

    private Path filePath;

    public JsonAccommodationStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAccommodationFilePath() {
        return filePath;
    }

    //@@author ernestyyh
    public void setAccommodationFilePath(Path accommodationFilePath) {
        requireNonNull(accommodationFilePath);
        filePath = accommodationFilePath;
    }

    //@@author OneArmyj
    @Override
    public Optional<ReadOnlyAccommodation> readAccommodation() throws DataConversionException {
        return readAccommodation(filePath);
    }

    /**
     * Similar to {@link #readAccommodation}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAccommodation> readAccommodation(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAccommodation> jsonAccommodation = JsonUtil.readJsonFile(
                filePath, JsonSerializableAccommodation.class);
        if (!jsonAccommodation.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAccommodation.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAccommodation(ReadOnlyAccommodation accommodation) throws IOException {
        saveAccommodation(accommodation, filePath);
    }

    /**
     * Similar to {@link #saveAccommodation(ReadOnlyAccommodation)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAccommodation(ReadOnlyAccommodation accommodation, Path filePath) throws IOException {
        requireNonNull(accommodation);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAccommodation(accommodation), filePath);
    }

}
