package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyEvents;

public class JsonEventStorage implements EventStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEventStorage.class);

    private Path filePath;

    public JsonEventStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEventFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEvents> readEvents() throws DataConversionException {
        return readEvents(filePath);
    }

    /**
     * Similar to {@link #readEvents()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyEvents> readEvents(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        return Optional.empty();
    }

    @Override
    public void saveEvents(ReadOnlyEvents events) throws IOException {

    }

    @Override
    public void saveEvents(ReadOnlyEvents events, Path filePath) throws IOException {

    }
}
