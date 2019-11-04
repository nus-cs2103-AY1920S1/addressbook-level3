package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends StudyBuddyProStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCheatSheetFilePath();

    @Override
    Path getFlashcardFilePath();

    @Override
    Path getNoteFilePath();

    @Override
    Optional<ReadOnlyStudyBuddyPro> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyStudyBuddyPro addressBook) throws IOException;
}
