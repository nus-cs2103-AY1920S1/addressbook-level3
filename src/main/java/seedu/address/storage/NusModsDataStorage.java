package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.NusModsData;

/**
 * Represents a storage for NusModsData.
 */
public interface NusModsDataStorage {

    Path getNusModsDataFilePath();

    Optional<NusModsData> readNusModsData() throws DataConversionException, IOException;

    Optional<NusModsData> readNusModsData(Path filePath) throws DataConversionException, IOException;

    void saveNusModsData(NusModsData nusModsData) throws IOException;

    void saveNusModsData(NusModsData nusModsData, Path filePath) throws IOException;
}
