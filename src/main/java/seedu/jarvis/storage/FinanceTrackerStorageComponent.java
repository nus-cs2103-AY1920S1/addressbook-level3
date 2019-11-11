package seedu.jarvis.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.finance.FinanceTracker;

/**
 * Storage component for {@code FinanceTracker}.
 */
public interface FinanceTrackerStorageComponent {

    Path getFinanceTrackerFilePath();

    Optional<FinanceTracker> readFinanceTracker() throws DataConversionException, IOException;

    Optional<FinanceTracker> readFinanceTracker(Path filePath) throws DataConversionException, IOException;

    void saveFinanceTracker(FinanceTracker financeTracker) throws IOException;

    void saveFinanceTracker(FinanceTracker financeTracker, Path filePath) throws IOException;
}
