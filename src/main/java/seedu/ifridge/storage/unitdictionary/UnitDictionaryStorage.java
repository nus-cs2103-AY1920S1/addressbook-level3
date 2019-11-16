package seedu.ifridge.storage.unitdictionary;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.model.UnitDictionary;

/**
 * Represents a storage for {@link UnitDictionary}.
 */
public interface UnitDictionaryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getUnitDictionaryFilePath();

    /**
     * Returns UnitDictionary data as a {@link UnitDictionary}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<UnitDictionary> readUnitDictionary() throws DataConversionException, IOException;

    /**
     * @see #getUnitDictionaryFilePath()
     */
    Optional<UnitDictionary> readUnitDictionary(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link UnitDictionary} to the storage.
     * @param unitDictionary cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUnitDictionary(UnitDictionary unitDictionary) throws IOException;

    /**
     * @see #saveUnitDictionary (UnitDictionary)
     */
    void saveUnitDictionary(UnitDictionary unitDictionary, Path filePath) throws IOException;

}
