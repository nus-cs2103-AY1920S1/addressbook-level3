package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.InternalState;

/**
 * A class to access InternalState stored in the hard disk as a json file
 */
public class JsonInternalStateStorage implements InternalStateStorage {

    private Path filePath;

    public JsonInternalStateStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getInternalStateFilePath() {
        return filePath;
    }

    @Override
    public Optional<InternalState> readInternalState() throws DataConversionException {
        return readInternalState(filePath);
    }

    /**
     * Similar to {@link #readInternalState()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<InternalState> readInternalState(Path prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(filePath, InternalState.class);
    }

    @Override
    public void saveInternalState(InternalState state) throws IOException {
        state.updateInternalState();
        JsonUtil.saveJsonFile(state, filePath);
    }

}
