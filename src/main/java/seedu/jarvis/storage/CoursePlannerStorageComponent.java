package seedu.jarvis.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.course.CoursePlanner;

/**
 * Storage component for {@code CoursePlanner}.
 */
public interface CoursePlannerStorageComponent {

    Path getCoursePlannerFilePath();

    Optional<CoursePlanner> readCoursePlanner() throws DataConversionException, IOException;

    Optional<CoursePlanner> readCoursePlanner(Path filePath) throws DataConversionException, IOException;

    void saveCoursePlanner(CoursePlanner coursePlanner) throws IOException;

    void saveCoursePlanner(CoursePlanner coursePlanner, Path filePath) throws IOException;
}
