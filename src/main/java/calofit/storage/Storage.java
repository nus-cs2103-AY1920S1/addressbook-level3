package calofit.storage;

import calofit.commons.exceptions.DataConversionException;
import calofit.model.ReadOnlyUserPrefs;
import calofit.model.UserPrefs;
import calofit.model.dish.ReadOnlyDishDatabase;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends DishDatabaseStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getDishDatabaseFilePath();

    @Override
    Optional<ReadOnlyDishDatabase> readDishDatabase() throws DataConversionException, IOException;

    @Override
    void saveDishDatabase(ReadOnlyDishDatabase dishDatabase) throws IOException;

}
