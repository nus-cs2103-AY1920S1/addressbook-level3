package seedu.address.financialtracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.financialtracker.model.FinancialTracker;

/**
 * Represents a storage for {@link FinancialTracker}
 */
public interface FinancialTrackerStorage {

    /**
     * Returns the file path of the data file
     */
    Path getFinancialTrackerFilePath();

    Optional<FinancialTracker> readFinancialTracker() throws DataConversionException, IOException;

    void saveFinancialTracker(FinancialTracker financialTracker) throws IOException;
}
