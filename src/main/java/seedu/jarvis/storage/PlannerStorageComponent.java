package seedu.jarvis.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.planner.Planner;

/**
 * Storage component for {@code Planner}.
 */
public interface PlannerStorageComponent {

    Path getPlannerFilePath();

    Optional<Planner> readPlanner() throws DataConversionException, IOException;

    Optional<Planner> readPlanner(Path filePath) throws DataConversionException, IOException;

    void savePlanner(Planner planner) throws IOException;

    void savePlanner(Planner planner, Path filePath) throws IOException;
}
