package seedu.jarvis.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;
import seedu.jarvis.model.userprefs.UserPrefs;

/**
 * Storage component for {@code UserPrefs}.
 */
public interface UserPrefsStorageComponent {

    Path getUserPrefsFilePath();

    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;
}
