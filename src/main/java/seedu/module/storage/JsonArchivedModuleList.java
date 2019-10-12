package seedu.module.storage;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.type.TypeReference;

import seedu.module.commons.core.LogsCenter;
import seedu.module.commons.exceptions.DataConversionException;
import seedu.module.commons.util.JsonUtil;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;

/**
 * A static class to read Archived Modules from a Json file.
 */
public class JsonArchivedModuleList {
    private static final Logger logger = LogsCenter.getLogger(JsonModuleBookStorage.class);
    private static Path filePath;

    /**
     * Returns an ArchivedModuleList read from the Json file corresponding to the
     * {@code resourceFileName}.
     *
     * @param resourceFileName the String representation of the filePath of the resource
     * file. The file must be in the ClassLoader's resource folder.
     */
    public static ArchivedModuleList readArchivedModules(String resourceFileName) {
        Optional<List<JsonAdaptedArchivedModule>> jsonArchivedModules = Optional.empty();
        ArchivedModuleList archivedModules = new ArchivedModuleList();

        filePath = getresourceFilePath(resourceFileName);
        if (filePath == null) {
            logger.warning("Resource file: " + resourceFileName + " not found.");
            logger.warning("Returning an empty ArchivedModuleList.");
            return archivedModules;
        }

        try {
            jsonArchivedModules = JsonUtil.readJsonFile(filePath,
                new TypeReference<List<JsonAdaptedArchivedModule>>(){});
        } catch (DataConversionException e) {
            logger.warning("Failed to fetch data of archived modules. Error: " + e);
            logger.warning("Returning an empty ArchivedModuleList.");
            return archivedModules;
        }

        // jsonArchivedModules is guaranteed not to be empty due to getresourceFileName
        List<JsonAdaptedArchivedModule> modules = jsonArchivedModules.get();

        for (JsonAdaptedArchivedModule jsonAdaptedArchivedModule : modules) {
            ArchivedModule module = jsonAdaptedArchivedModule.toModelType();

            archivedModules.add(module);
        }

        return archivedModules;
    }

    /**
     * Returns a {@code Path} corresponding to the resource file name, or
     * {@code null} if the resource file does not exist.
     */
    private static Path getresourceFilePath(String resourceFileName) {
        try {
            return Paths.get(JsonArchivedModuleList.class.getClassLoader()
                .getResource(resourceFileName).toURI());
        } catch (URISyntaxException | NullPointerException e) {
            return null;
        }
    }
}
