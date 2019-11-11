package seedu.jarvis.storage.planner;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.commons.util.FileUtil;
import seedu.jarvis.commons.util.JsonUtil;
import seedu.jarvis.model.planner.Planner;

/**
 * A class to access {@code Planner} data stored as a text file on the hard disk.
 */
public class JsonPlannerStorage implements PlannerStorage {

    private Path filepath;

    public JsonPlannerStorage(Path filepath) {
        this.filepath = filepath;
    }

    /**
     * Gets the file path of the data file for {@code Planner}.
     *
     * @return File path of the data file for {@code Planner}.
     */
    @Override
    public Path getPlannerFilePath() {
        return filepath;
    }

    /**
     * Returns {@code Planner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return {@code Planner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<Planner> readPlanner() throws DataConversionException, IOException {
        return readPlanner(filepath);
    }

    /**
     * Returns {@code Planner} data.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @param filePath {@code Path} to read {@code Planner} data.
     * @return {@code Planner} data, or {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    @Override
    public Optional<Planner> readPlanner(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);
        Optional<JsonSerializablePlanner> jsonPlanner = JsonUtil.readJsonFile(filePath, JsonSerializablePlanner.class);
        if (jsonPlanner.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPlanner.get().toModelType());
        } catch (IllegalValueException ive) {
            throw new DataConversionException(ive);
        }
    }

    /**
     * Saves the given {@link Planner} to the storage.
     *
     * @param planner {@code Planner} to be saved, which cannot be null.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void savePlanner(Planner planner) throws IOException {
        savePlanner(planner, filepath);
    }

    /**
     * Saves the given {@link Planner} to the storage.
     *
     * @param planner  {@code Planner} to be saved, which cannot be null.
     * @param filePath {@code Path} to read {@code Planner} data.
     * @throws IOException If there was any problem writing to the file.
     */
    @Override
    public void savePlanner(Planner planner, Path filePath) throws IOException {
        requireAllNonNull(planner, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePlanner(planner), filePath);
    }
}
