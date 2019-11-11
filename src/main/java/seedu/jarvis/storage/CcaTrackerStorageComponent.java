package seedu.jarvis.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.cca.CcaTracker;

/**
 * Storage component for {@code CcaTracker}.
 */
public interface CcaTrackerStorageComponent {

    Path getCcaTrackerFilePath();

    Optional<CcaTracker> readCcaTracker() throws DataConversionException, IOException;

    Optional<CcaTracker> readCcaTracker(Path filePath) throws DataConversionException, IOException;

    void saveCcaTracker(CcaTracker ccaTracker) throws IOException;

    void saveCcaTracker(CcaTracker ccaTracker, Path filePath) throws IOException;
}
