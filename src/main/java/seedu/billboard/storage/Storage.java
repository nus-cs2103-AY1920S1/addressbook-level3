package seedu.billboard.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.billboard.commons.exceptions.DataConversionException;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.ReadOnlyUserPrefs;
import seedu.billboard.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getBillboardFilePath();

    @Override
    Optional<ReadOnlyBillboard> readBillboard() throws DataConversionException, IOException;

    @Override
    void saveBillboard(ReadOnlyBillboard addressBook) throws IOException;

}
