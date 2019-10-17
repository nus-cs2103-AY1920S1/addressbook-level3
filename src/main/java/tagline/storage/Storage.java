package tagline.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tagline.commons.exceptions.DataConversionException;
import tagline.model.ReadOnlyUserPrefs;
import tagline.model.UserPrefs;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.note.ReadOnlyNoteBook;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, NoteBookStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Path getNoteBookFilePath();

    @Override
    Optional<ReadOnlyNoteBook> readNoteBook() throws DataConversionException, IOException;

    @Override
    void saveNoteBook(ReadOnlyNoteBook noteBook) throws IOException;
}
