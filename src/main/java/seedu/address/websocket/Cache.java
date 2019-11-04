package seedu.address.websocket;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ConnectException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import seedu.address.commons.core.AppSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.commons.util.SimpleJsonUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.gmaps.Location;
import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleId;
import seedu.address.model.module.ModuleList;
import seedu.address.model.module.ModuleSummaryList;
import seedu.address.websocket.util.UrlUtil;

/**
 * Cache class handles whether to get external data from storage data or api.
 */
public class Cache {
    private static final String writablePath;
    private static final Logger logger = LogsCenter.getLogger(Cache.class);

    // Static block, not the best but works. Need to do heavy refactoring if want to improve code quality of Cache.
    static {
        final URL url = Cache.class.getResource(CacheFileNames.CACHE_FOLDER_PATH);
        if (url != null && url.getProtocol().equals("jar")) {
            // we're running from a JAR
            writablePath = Paths.get(System.getProperty("java.io.tmpdir"), "timebook").toString();
        } else {
            //we're running from file/IDE
            writablePath = "src/main/resources/";
        }
        logger.info("Storing cached data in " + writablePath);
    }

    private static NusModsApi api = new NusModsApi(AppSettings.DEFAULT_ACAD_YEAR);
    private static Optional<Object> gmapsPlaces = load(CacheFileNames.GMAPS_PLACES_PATH);
    private static Optional<Object> gmapsDistanceMatrix = load(CacheFileNames.GMAPS_DISTANCE_MATRIX_PATH);
    private static Optional<Object> gmapsPlaceDetails = load(CacheFileNames.GMAPS_PLACE_DETAILS_PATH);

    /**
     * Saves JSON object to file in writable directory path.
     *
     * @param obj      obj to save.
     * @param filePath file path to saved JSON file.
     */
    private static void save(Object obj, String filePath) {
        requireNonNull(obj);
        requireNonNull(filePath);

        Path fullPath = Path.of(writablePath, filePath);
        try {
            FileUtil.createIfMissing(fullPath);
            JsonUtil.saveJsonFile(obj, fullPath);
        } catch (IOException e) {
            logger.warning("Failed to save file : " + StringUtil.getDetails(e));
        }
    }

    /**
     * Adds and saves a JSON element to a particular JSON file. Overwrites if key already exists.
     *
     * @param key      key of element to add.
     * @param value    value of element to add.
     * @param filePath file path to JSON file.
     */
    public static void saveToJson(String key, Object value, String filePath) {
        requireNonNull(key);
        requireNonNull(value);
        requireNonNull(filePath);

        Optional<Object> optionalObject = load(filePath);
        JSONObject jsonObject = new JSONObject();
        if (optionalObject.isPresent()) {
            jsonObject = (JSONObject) optionalObject.get();
        }
        jsonObject.put(key, value);
        save(jsonObject, filePath);
    }

    /**
     * Loads JSON file from JAR resources.
     * @param path Relative path (starting with backslash).
     */
    private static Optional<Object> loadFromResources(String path) {
        final InputStream resourceStream = Cache.class.getResourceAsStream(path);
        Object jsonFile;
        Reader reader = new InputStreamReader(resourceStream);

        try {
            JSONParser parser = new JSONParser();
            jsonFile = parser.parse(reader);
        } catch (IOException | org.json.simple.parser.ParseException e) {
            logger.warning("Error reading from json file " + path + ": " + e);
            return Optional.empty();
        }
        return Optional.of(jsonFile);
    }

    /**
     * Loads JSON Object from file in writable directory path.
     *
     * @param filePath file name to load from.
     * @return an Optional containing a Object or empty.
     */
    private static Optional<Object> load(String filePath) {
        requireNonNull(filePath);
        logger.info("Loading: " + filePath);

        Path fullPath = Path.of(writablePath, filePath);
        //attempt to load from tempdir.
        Optional<Object> objOptional = SimpleJsonUtil.readJsonFile(fullPath);
        if (objOptional.isEmpty()) { //if failed, attempt to load from JAR resources.
            objOptional = loadFromResources(filePath);
        }
        return objOptional;
    }

    // ================================== NusMods Methods ==============================================

    /**
     * Loads holidays from cache, if it fails, call api, then save results to cache folder.
     * If api fails too, return empty.
     *
     * @return an Optional containing a Holidays object or empty.
     */
    public static Optional<Holidays> loadHolidays() {
        Optional<Object> objOptional = load(CacheFileNames.HOLIDAYS);
        if (objOptional.isPresent()) {
            return Optional.of(NusModsParser.parseHolidays((JSONArray) objOptional.get()));
        }

        logger.info("Holidays not found in cache, getting from API...");
        Optional<JSONArray> arrOptional = api.getHolidays();
        if (arrOptional.isPresent()) {
            save(arrOptional.get(), CacheFileNames.HOLIDAYS);
            return Optional.of(NusModsParser.parseHolidays(arrOptional.get()));
        }

        logger.warning("Failed to get holidays from API! Adding NUSMods data will not be holiday-aware.");
        return Optional.empty();
    }

    /**
     * Loads holidays from cache, if it fails, call api, then save results to cache folder.
     * If api fails too, return empty.
     *
     * @return an Optional containing a Holidays object or empty.
     */
    public static Optional<AcadCalendar> loadAcadCalendar() {
        Optional<Object> objOptional = load(CacheFileNames.ACADEMIC_CALENDAR);
        if (objOptional.isPresent()) {
            return Optional.of(NusModsParser.parseAcadCalendar((JSONObject) objOptional.get()));
        }

        logger.info("Academic calendar not found in cache, getting from API...");
        Optional<JSONObject> jsonObjOptional = api.getAcademicCalendar();
        if (jsonObjOptional.isPresent()) {
            save(jsonObjOptional.get(), CacheFileNames.ACADEMIC_CALENDAR);
            return Optional.of(NusModsParser.parseAcadCalendar(jsonObjOptional.get()));
        }

        logger.severe("Failed to get academic calendar from API! Will not be able to add mods to schedules.");
        return Optional.empty();
    }

    /**
     * Loads ModuleSummaryList from cache, if it fails, return empty.
     *
     * @return an Optional containing a ModuleSummaryList object or empty.
     */
    public static Optional<ModuleSummaryList> loadModuleSummaryList() {
        Optional<Object> objOptional = load(CacheFileNames.MODULES_SUMMARY);
        ModuleSummaryList moduleSummaryList = new ModuleSummaryList();

        JSONObject modulesSummariesJson = new JSONObject();
        if (objOptional.isPresent()) {
            JSONObject moduleSummariesJson = (JSONObject) objOptional.get();
            JSONArray moduleSummariesSingleYear = (JSONArray) moduleSummariesJson.get(api.getAcadYear().toString());
            try {
                return Optional.of(NusModsParser.parseModuleSummaryList(moduleSummariesSingleYear, api.getAcadYear()));
            } catch (ParseException e) {
                logger.severe("Failed to parse module summaries: " + e.getMessage());
                return Optional.empty();
            }
        }

        logger.info("Module summaries not found in cache, getting from API...");
        Optional<JSONArray> arrOptional = api.getModuleList();
        if (arrOptional.isPresent()) {
            modulesSummariesJson.put(api.getAcadYear(), arrOptional.get());
            save(modulesSummariesJson, CacheFileNames.MODULES_SUMMARY);
            try {
                return Optional.of(NusModsParser.parseModuleSummaryList(arrOptional.get(), api.getAcadYear()));
            } catch (ParseException e) {
                logger.severe("Failed to parse module summaries: " + e.getMessage());
                return Optional.empty();
            }
        }

        logger.severe("Failed to module summaries from API!");
        return Optional.empty();
    }

    /**
     * Loads modulelist from cache, if it fails, return empty.
     *
     * @return an Optional containing a ModuleList object or empty.
     */
    public static Optional<ModuleList> loadModuleList() {
        Optional<Object> objOptional = load(CacheFileNames.MODULES);
        ModuleList moduleList = new ModuleList();

        if (objOptional.isPresent()) { // found cached file
            JSONObject modulesJson = (JSONObject) objOptional.get();
            for (Object key : modulesJson.keySet()) {
                JSONObject moduleJson = (JSONObject) modulesJson.get(key);
                try {
                    Module module = NusModsParser.parseModule(moduleJson);
                    moduleList.addModule(module);
                } catch (ParseException e) {
                    logger.severe("Failed to parse module: " + e.getMessage());
                }
            }
            return Optional.of(moduleList);
        }

        logger.warning("No modules in cache. Will be calling from API for each module.");
        return Optional.empty();
    }

    /**
     * Loads a module from cache, if it fails, call api, then save results to cache folder.
     * If api fails too, return empty.
     *
     * @return an Optional containing a Module object or empty.
     */
    public static Optional<Module> loadModule(ModuleId moduleId) {
        Optional<Object> objOptional = load(CacheFileNames.MODULES);

        JSONObject modulesJson = new JSONObject();
        if (objOptional.isPresent()) { // found cached file
            modulesJson = (JSONObject) objOptional.get();
            if (modulesJson.containsKey(moduleId.toString())) { // found moduleId in cached file
                JSONObject moduleJson = (JSONObject) modulesJson.get(moduleId.toString());
                try {
                    return Optional.of(NusModsParser.parseModule(moduleJson));
                } catch (ParseException e) {
                    logger.severe("Failed to parse module from cache: " + e.getMessage());
                }
            }
        }

        logger.info("Module " + moduleId + " not found in cache, getting from API...");
        Optional<JSONObject> jsonObjFromApiOptional = api.getModule(moduleId.getModuleCode());
        if (jsonObjFromApiOptional.isPresent()) { // found module from API
            try {
                Module module = NusModsParser.parseModule(jsonObjFromApiOptional.get());
                modulesJson.put(module.getModuleId(), jsonObjFromApiOptional.get());
                save(modulesJson, CacheFileNames.MODULES);
                return Optional.of(module);
            } catch (ParseException e) {
                logger.severe("Failed to parse module: " + e.getMessage());
            }
        }

        logger.severe("Failed to get module from API! Unable to add mod to schedules.");
        return Optional.empty();
    }

    /**
     * Loads venues from cache. If it fails, call api, then save results to cache folder.
     * If api fails too, return empty.
     *
     * @return a JSONArray object containing venues data.
     */
    public static JSONArray loadVenues() {
        Optional<Object> optionalObject = load(CacheFileNames.VENUES_FULL_PATH);
        JSONArray venues = new JSONArray();
        if (optionalObject.isPresent()) {
            venues = (JSONArray) optionalObject.get();
        }


        if (!venues.isEmpty()) {
            return venues;
        } else {
            logger.info("Venues not found in cache, getting from API...");
            venues = api.getVenues("/1").orElse(new JSONArray());
            save(venues, CacheFileNames.VENUES_FULL_PATH);
        }

        return venues;
    }

    // ================================== Google Maps Methods ==============================================

    /**
     * This method is used to load the info of the place by Google Maps from the cache or Google Maps API
     *
     * @param locationName
     * @return
     */
    public static JSONObject loadPlaces(String locationName) {
        String fullUrl = UrlUtil.generateGmapsPlacesUrl(locationName);
        String sanitizedUrl = UrlUtil.sanitizeApiKey(fullUrl);
        JSONObject placesJson = new JSONObject();

        if (gmapsPlaces.isPresent()) {
            placesJson = (JSONObject) gmapsPlaces.get();
        }

        JSONObject result = new JSONObject();
        if (placesJson.get(sanitizedUrl) != null) {
            result = (JSONObject) placesJson.get(sanitizedUrl);
        } else {
            try {
                checkGmapsKey(fullUrl);
                logger.info("Getting location: " + locationName + " data from Google Maps API");
                result = GmapsApi.getLocation(locationName);
                saveToJson(sanitizedUrl, result, CacheFileNames.GMAPS_PLACES_PATH);
            } catch (ConnectException e) {
                logger.info(e.getMessage());
                logger.severe("Failed to get info for " + locationName + " from caching and API");
            }
        }
        return result;
    }

    /**
     * This method is used to load the info of the place by Google Maps from the cache or Google Maps API
     *
     * @param locationsRow
     * @param locationsColumn
     * @return
     */
    public static JSONObject loadDistanceMatrix(ArrayList<Location> locationsRow, ArrayList<Location> locationsColumn) {
        String fullUrl = UrlUtil.generateGmapsDistanceMatrixUrl(locationsRow, locationsColumn);
        String sanitizedUrl = UrlUtil.sanitizeApiKey(fullUrl);
        JSONObject distanceMatrixJson = new JSONObject();

        if (gmapsDistanceMatrix.isPresent()) {
            distanceMatrixJson = (JSONObject) gmapsDistanceMatrix.get();
        }

        JSONObject result = new JSONObject();
        if (distanceMatrixJson.get(sanitizedUrl) != null) {
            result = (JSONObject) distanceMatrixJson.get(sanitizedUrl);
        } else {
            try {
                logger.info("Getting row: " + locationsRow + " column " + locationsColumn
                        + " data from Google Maps API");
                checkGmapsKey(fullUrl);
                result = GmapsApi.getDistanceMatrix(locationsRow, locationsColumn);
                saveToJson(sanitizedUrl, result, CacheFileNames.GMAPS_DISTANCE_MATRIX_PATH);
            } catch (ConnectException e) {
                logger.info(e.getMessage());
                logger.severe("Failed to get info for row: " + locationsRow + " column: " + locationsColumn
                        + " from caching and API");
            }
        }
        return result;
    }

    /**
     * This method is used to load the info of the place by Google Maps from the cache or Google Maps API
     *
     * @param placeId
     * @return
     */
    public static JSONObject loadPlaceDetails(String placeId) {
        String fullUrl = UrlUtil.generateGmapsPlaceDetailsUrl(placeId);
        String sanitizedUrl = UrlUtil.sanitizeApiKey(fullUrl);
        JSONObject placesJson = new JSONObject();
        if (gmapsPlaceDetails.isPresent()) {
            placesJson = (JSONObject) gmapsPlaceDetails.get();
        }

        JSONObject result = new JSONObject();
        if (placesJson.get(sanitizedUrl) != null) {
            result = (JSONObject) placesJson.get(sanitizedUrl);
        } else {
            try {
                checkGmapsKey(fullUrl);
                logger.info("Getting placeID: " + placeId + " data from Google Maps API");
                result = GmapsApi.getPlaceDetails(placeId);
                saveToJson(sanitizedUrl, result, CacheFileNames.GMAPS_PLACE_DETAILS_PATH);
            } catch (ConnectException e) {
                logger.info(e.getMessage());
                logger.severe("Failed to get info for " + placeId + " from caching and API");
            }
        }
        return result;
    }

    private static void checkGmapsKey(String url) throws ConnectException {
        if (url.split("key=").length == 1) {
            throw new ConnectException("Enter API key to make API call");
        }
    }

    /**
     * This method is used to get the file path for the image
     *
     * @param validLocation the location name with prefix NUS_
     * @return the path of the image
     */
    public static String loadImagePath(String validLocation) {
        return CacheFileNames.GMAPS_IMAGE_DIR + validLocation + ".png";
    }

    /**
     * This method return the image write path
     * @param validLocation
     * @return
     */
    public static String writeImagePath(String validLocation) {
        return writablePath + CacheFileNames.GMAPS_IMAGE_DIR + validLocation + ".png";
    }

    /**
     * Load the image from the resources dir
     * @param validLocation
     * @return
     */
    public static BufferedImage loadImage(String validLocation) {
        String path = loadImagePath(validLocation);
        final InputStream resourceStream = Cache.class.getResourceAsStream(path);
        BufferedImage img = null;
        try {
            img = ImageIO.read(resourceStream);
            logger.info("Successfully loaded image for " + validLocation);
        } catch (IOException e) {
            logger.warning("Error reading from image file " + path + ": " + e);
        }
        return img;
    }
}
