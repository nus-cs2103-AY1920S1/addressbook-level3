package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
// NOTE: this has been changed to support module planner instead of address book
public interface Storage extends ModulePlannerStorage, UserPrefsStorage, ModulesInfoStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getModulePlannerFilePath();

    @Override
    Optional<ReadOnlyModulePlanner> readModulePlanner(ModulesInfo modulesInfo)
            throws DataConversionException, IOException;

    @Override
    void saveModulePlanner(ReadOnlyModulePlanner modulePlanner) throws IOException;

    @Override
    Optional<ModulesInfo> readModulesInfo() throws DataConversionException, IOException;
}

