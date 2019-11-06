package seedu.jarvis.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.history.HistoryManager;

/**
 * Storage component for {@code HistoryManager}.
 */
public interface HistoryManagerStorageComponent {

    Path getHistoryManagerFilePath();

    Optional<HistoryManager> readHistoryManager() throws DataConversionException, IOException;

    Optional<HistoryManager> readHistoryManager(Path filePath) throws DataConversionException, IOException;

    void saveHistoryManager(HistoryManager historyManager) throws IOException;

    void saveHistoryManager(HistoryManager historyManager, Path filePath) throws IOException;
}
