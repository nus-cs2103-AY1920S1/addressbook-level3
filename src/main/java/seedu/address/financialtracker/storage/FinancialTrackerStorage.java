package seedu.address.financialtracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.financialtracker.model.FinancialTracker;

public interface FinancialTrackerStorage {
    // Path getFinancialTrackerFilePath();

    Optional<FinancialTracker> readFinancialTracker() throws DataConversionException, IOException;

    void saveFinancialTracker(FinancialTracker calendar) throws IOException;
}
