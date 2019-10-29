package seedu.address.websocket;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import seedu.address.commons.core.AppSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.commons.util.SimpleJsonUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
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
    private static final Logger logger = LogsCenter.getLogger(Cache.class);
    private static NusModsApi api = new NusModsApi(AppSettings.DEFAULT_ACAD_YEAR);
    private static Optional<Object> gmapsPlaces = load(CacheFileNames.GMAPS_PLACES_PATH);
    private static Optional<Object> gmapsDistanceMatrix = load(CacheFileNames.GMAPS_DISTANCE_MATRIX_PATH);
    /**
     * Save json to file in cache folder.
     * @param obj obj to save
     * @param pathName file name to save the obj as
     */
    private static void save(Object obj, String pathName) {
        requireNonNull(obj);
        requireNonNull(pathName);

        Path fullPath = Path.of(pathName);
        try {
            FileUtil.createIfMissing(fullPath);
            JsonUtil.saveJsonFile(obj, fullPath);
        } catch (IOException e) {
            logger.warning("Failed to save file : " + StringUtil.getDetails(e));
        }
    }

    /**
     * This method is used to save all the API response to a particular JSON file. Only for JSON
     * Object
     * @param key
     * @param value
     * @param filePath
     */
    public static void saveToJson(String key, Object value , String filePath) {
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
     * Load json from file in cache folder
     * @param pathName file name to load from
     * @return an Optional containing a JSONObject or empty.
     */
    private static Optional<Object> load(String pathName) {
        requireNonNull(pathName);

        Path fullPath = Path.of(pathName);
        return SimpleJsonUtil.readJsonFile(fullPath);
    }

    /**
     * Load holidays from cache, if failed, call api, then save results to cache folder.
     * If api fails too, return empty.
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
     * Load holidays from cache, if failed, call api, then save results to cache folder.
     * If api fails too, return empty.
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
     * Load ModuleSummaryList from cache, if failed, return empty.
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
     * Load modulelist from cache, if failed, return empty.
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
     * Load a module from cache, if failed, call api, then save results to cache folder.
     * If api fails too, return empty.
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
     * Load a module from cache, if failed, call api, then save results to cache folder.
     * If api fails too, return empty.
     * @return an Optional containing a Module object or empty.
     */
    public static JSONArray loadVenues() {
        JSONArray venues = (JSONArray) load(CacheFileNames.VENUES_FULL_PATH).get();

        if (venues != null) {
            return venues;
        } else {
            logger.info("Module not found in cache, getting from API...");
            venues = api.getVenues("/1").orElse(new JSONArray());
            save(venues, CacheFileNames.VENUES_FULL_PATH);
        }

        return venues;
    }

    /**
     * This method is used to load the info of the place by Google Maps from the cache or Google Maps API
     * @param locationName
     * @return
     */
    public static JSONObject loadPlaces(String locationName) {
        String fullUrl = UrlUtil.generateGmapsPlacesUrl(locationName);
        String sanitizedUrl = UrlUtil.sanitizeApiKey(fullUrl);
        JSONObject placesJson = (JSONObject) gmapsPlaces.get();
        JSONObject result = new JSONObject();
        if (placesJson.get(sanitizedUrl) != null) {
            result = (JSONObject) placesJson.get(sanitizedUrl);
        } else {
            try {
                result = GmapsApi.getLocation(locationName);
                saveToJson(sanitizedUrl, result, CacheFileNames.GMAPS_PLACES_PATH);
            } catch (ConnectException e) {
                logger.severe("Failed to get info for " + locationName + " from caching and API");
            }
        }
        return result;
    }

    /**
     * This method is used to load the info of the place by Google Maps from the cache or Google Maps API
     * @param locationsRow
     * @param locationsColumn
     * @return
     */
    public static JSONObject loadDistanceMatrix(ArrayList<String> locationsRow, ArrayList<String> locationsColumn) {
        String fullUrl = UrlUtil.generateGmapsDistanceMatrixUrl(locationsRow, locationsColumn);
        String sanitizedUrl = UrlUtil.sanitizeApiKey(fullUrl);
        JSONObject distanceMatrixJson = (JSONObject) gmapsDistanceMatrix.get();
        JSONObject result = new JSONObject();;
        if (distanceMatrixJson.get(sanitizedUrl) != null) {
            result = (JSONObject) distanceMatrixJson.get(sanitizedUrl);
        } else {
            try {
                result = GmapsApi.getDistanceMatrix(locationsRow, locationsColumn);
                saveToJson(sanitizedUrl, result, CacheFileNames.GMAPS_DISTANCE_MATRIX_PATH);
            } catch (ConnectException e) {
                logger.severe("Failed to get info for row: " + locationsRow + " column: " + locationsColumn
                        + " from caching and API");
            }
        }
        return result;
    }
}
