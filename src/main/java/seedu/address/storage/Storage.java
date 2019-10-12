package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.CardBook;
import seedu.address.model.PasswordBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyFileBook;
import seedu.address.model.ReadOnlyNoteBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */

public interface Storage extends AddressBookStorage, FileBookStorage, CardBookStorage,
        NoteBookStorage, PasswordBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    String getStoragePassword();

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    Path getFileBookFilePath();

    @Override
    Optional<ReadOnlyFileBook> readFileBook() throws DataConversionException, IOException;

    @Override
    void saveFileBook(ReadOnlyFileBook fileBook) throws IOException;

    @Override
    Path getCardBookFilePath();

    @Override
    Optional<CardBook> readCardBook() throws DataConversionException, IOException;

    @Override
    void saveCardBook(CardBook cardBook) throws IOException;

    Path getNoteBookFilePath();

    @Override
    Optional<ReadOnlyNoteBook> readNoteBook() throws DataConversionException, IOException;

    @Override
    void saveNoteBook(ReadOnlyNoteBook noteBook) throws IOException;

    Path getPasswordBookFilePath();

    @Override
    Optional<PasswordBook> readPasswordBook() throws DataConversionException, IOException;

    @Override
    void savePasswordBook(PasswordBook passwordBook) throws IOException;

}
