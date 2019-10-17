package seedu.module.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.type.TypeReference;

import seedu.module.commons.core.LogsCenter;
import seedu.module.commons.util.JsonUtil;
import seedu.module.model.module.ArchivedModule;
import seedu.module.model.module.ArchivedModuleList;

/**
 * A static class to read Archived Modules from a Json file.
 */
public class JsonArchivedModuleList {
    private static final Logger logger = LogsCenter.getLogger(JsonModuleBookStorage.class);

    /**
     * Returns an ArchivedModuleList read from the Json file corresponding to the
     * {@code resourceFileName}.
     *
     * @param resourceFileName the String representation of the filePath of the resource
     * file. The file must be in the ClassLoader's resource folder.
     */
    public static ArchivedModuleList readArchivedModules(String resourceFileName) {
        List<JsonAdaptedArchivedModule> jsonArchivedModules;
        ArchivedModuleList archivedModules = new ArchivedModuleList();

        logger.fine("Attempting to read archived modules.");

        // @@author akosicki
        // Code Snippet below is used to read a resource file as a String
        // Reused from https://stackoverflow.com/a/18897411 with minor modifications
        InputStream jsonFileInputStream = JsonArchivedModuleList.class.getClassLoader()
            .getResourceAsStream(resourceFileName);

        requireNonNull(jsonFileInputStream);

        Scanner sc = new Scanner(jsonFileInputStream);
        String jsonString = sc.useDelimiter("\\A").next();
        sc.close();

        // @@author

        try {
            jsonArchivedModules = JsonUtil.fromJsonString(jsonString,
                new TypeReference<List<JsonAdaptedArchivedModule>>(){});
        } catch (IOException e) {
            logger.warning("Failed to fetch data of archived modules. Error: " + e);
            logger.warning("Returning an empty ArchivedModuleList.");
            return archivedModules;
        }

        for (JsonAdaptedArchivedModule jsonAdaptedArchivedModule : jsonArchivedModules) {
            ArchivedModule module = jsonAdaptedArchivedModule.toModelType();

            archivedModules.add(module);
        }

        return archivedModules;
    }
}
