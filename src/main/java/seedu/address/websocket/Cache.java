package seedu.address.websocket;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import java.nio.file.Path;
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
import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleId;
import seedu.address.model.module.ModuleList;

/**
 * Cache class handles whether to get external data from storage data or api.
 */
public class Cache {
    private static final Logger logger = LogsCenter.getLogger(Cache.class);

    private static final String JSON_EXTENSION = ".json";

    private static Path folderPath;
    private static NusModsApi api = new NusModsApi(AppSettings.DEFAULT_ACAD_YEAR);


    public static Path getFolderPath() {
        return folderPath;
    }

    public static void setFolderPath(Path folderPath) {
        Cache.folderPath = folderPath;
    }

    /**
     * Save json to file in cache folder.
     * @param obj obj to save
     * @param fileName file name to save the obj as
     */
    private static void save(Object obj, String fileName) {
        requireNonNull(obj);
        requireNonNull(fileName);

        Path fullPath = folderPath.resolve(fileName + JSON_EXTENSION);
        try {
            FileUtil.createIfMissing(fullPath);
            JsonUtil.saveJsonFile(obj, fullPath);
        } catch (IOException e) {
            logger.warning("Failed to save file : " + StringUtil.getDetails(e));
        }
    }

    /**
     * Load json from file in cache folder
     * @param fileName file name to load from
     * @return an Optional containing a JSONObject or empty.
     */
    private static Optional<Object> load(String fileName) {
        requireNonNull(fileName);

        Path fullPath = folderPath.resolve(fileName + JSON_EXTENSION);
        Optional<Object> objOptional = SimpleJsonUtil.readJsonFile(fullPath);
        return objOptional;
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
     * Load modulelist from cache, if failed, return empty.
     * @return an Optional containing a Module object or empty.
     */
    public static Optional<ModuleList> loadModuleList() {
        Optional<Object> objOptional = load(CacheFileNames.MODULES);
        ModuleList moduleList = new ModuleList();

        if (objOptional.isPresent()) { // found cached file
            JSONObject modulesJson = (JSONObject) objOptional.get();
            for (Object key : modulesJson.keySet()) {
                JSONObject moduleJson = (JSONObject) modulesJson.get(key);
                Module module = NusModsParser.parseModule(moduleJson);
                moduleList.addModule(module);
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
            if (modulesJson.containsKey(moduleId)) { // found moduleId in cached file
                JSONObject moduleJson = (JSONObject) modulesJson.get(moduleId);
                return Optional.of(NusModsParser.parseModule(moduleJson));
            }
        }

        logger.info("Module not found in cache, getting from API...");
        Optional<JSONObject> jsonObjFromApiOptional = api.getModule(moduleId.getModuleCode());
        if (jsonObjFromApiOptional.isPresent()) { // found module from API
            Module module = NusModsParser.parseModule(jsonObjFromApiOptional.get());
            modulesJson.put(module.getModuleId(), jsonObjFromApiOptional.get());
            save(modulesJson, CacheFileNames.MODULES);
            return Optional.of(module);
        }

        logger.severe("Failed to get module from API! Unable to add mod to schedules.");
        return Optional.empty();
    }
}
