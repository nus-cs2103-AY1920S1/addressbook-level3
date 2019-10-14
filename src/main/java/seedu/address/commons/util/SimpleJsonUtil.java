package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import org.json.simple.JSONAware;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import seedu.address.commons.core.LogsCenter;

/**
 * Read and write JSON file with simpleJson
 */
public class SimpleJsonUtil {

    private static final Logger logger = LogsCenter.getLogger(SimpleJsonUtil.class);

    /**
     * Returns the Json object from the given file or {@code Optional.empty()} object if the file is not found.
     * If any values are missing from the file, default values will be used, as long as the file is a valid json file.
     * @param filePath cannot be null.
     * @param classOfObjectToDeserialize class of object to deserialize.
     */
    public static <T> Optional<T> readJsonFile(Path filePath, Class<T> classOfObjectToDeserialize) {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Json file " + filePath + " not found");
            return Optional.empty();
        }

        T jsonFile;

        try {
            JSONParser parser = new JSONParser();
            jsonFile = (T) parser.parse(FileUtil.readFromFile(filePath));
        } catch (IOException | ParseException e) {
            logger.warning("Error reading from json file " + filePath + ": " + e);
            return Optional.empty();
        }

        return Optional.of(jsonFile);
    }

    /**
     * Saves the Json object to the specified file.
     * Overwrites existing file if it exists, creates a new file if it doesn't.
     * @param jsonFile cannot be null
     * @param filePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    public static <T> void saveJsonFile(JSONAware jsonFile, Path filePath) throws IOException {
        requireNonNull(filePath);
        requireNonNull(jsonFile);

        FileUtil.writeToFile(filePath, jsonFile.toJSONString());
    }
}
