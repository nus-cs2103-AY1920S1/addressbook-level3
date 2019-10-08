package seedu.address.websocket;

import java.nio.file.Path;

//import org.json.simple.JSONObject;

//import seedu.address.commons.util.JsonUtil;

/**
 * Caches API query results into raw JSON files for offline support.
 */
public class ApiCache {
    //TODO: implement ApiCache

    //define fileReader, fileWriter, etc.

    // get Path (FileUtil?)

    // writeToJson (JsonUtil?)

    /**
     * Returns a Object read from a JSON file.
     * @param path
     * @return
     */
    public Object readJson(Path path) {
        return null;
    }

    /**
     * Returns a Object read from a JSON file.
     * @param path
     * @param key
     * @return
     */
    public Object readJson(Path path, String key) {
        return null;
    }
}
