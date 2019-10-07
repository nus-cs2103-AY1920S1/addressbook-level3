package seedu.address.websocket;

import java.util.logging.Logger;

import org.json.simple.JSONObject;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.ModuleCode;
import seedu.address.websocket.util.ApiQuery;
import seedu.address.websocket.util.ParserUtil;
import seedu.address.websocket.util.QueryResult;

/**
 * NUSMods API websocket.
 * Refer to https://api.nusmods.com/v2/#/ for more detailed information.
 */
public class NusModApi {
    private static final String BASE_URL = "https://api.nusmods.com/v2";
    private static final String BASE_URL_2 = "http://api.nusmods.com/v2";

    private static final String SLASH = "/";
    private static final String JSON_EXTENSION = ".json";
    private static final String ACAD_YEAR = "/2019-2020";
    private static final String MODULES = "/modules";
    private static final String MODULE_LIST = "/moduleList";
    private static final String MODULE_INFO = "/moduleInfo";
    private static final String SEMESTERS = "/semesters";
    private static final String VENUES = "/venues";
    private static final String VENUE_INFO = "/venueInformation";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public NusModApi() {

    }

    /**
     * Returns a JSONObject of summaries of all modules in ACAD_YEAR.
     *
     * @return JSONObject containing module code, title and available semesters for all modules in a year.
     */
    public JSONObject getModuleList() {
        ApiQuery query = new ApiQuery(BASE_URL + ACAD_YEAR + MODULE_LIST + JSON_EXTENSION);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONObject of detailed information about all modules in ACAD_YEAR.
     *
     * @return JSONObject containing all module info except timetable and prereq tree for all modules in a year.
     */
    public JSONObject getModuleInfo() {
        ApiQuery query = new ApiQuery(BASE_URL + ACAD_YEAR + MODULE_INFO + JSON_EXTENSION);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONObject of all info about moduleCode in ACAD_YEAR.
     *
     * @param moduleCode code of the specific module.
     * @return JSONObject containing all info available for a specific module including prerequisite tree and timetable.
     */
    public JSONObject getModule(ModuleCode moduleCode) {
        ApiQuery query = new ApiQuery(BASE_URL + ACAD_YEAR + MODULES + SLASH + moduleCode + JSON_EXTENSION);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONObject of summaries of all venues for the current semester.
     *
     * @param semester current semester: 1 -> sem 1
     *                 2 -> sem 2
     *                 3 -> special term 1
     *                 4 -> special term 2
     * @return JSONObject representing the venues
     */
    public JSONObject getVenues(String semester) {
        ApiQuery query = new ApiQuery(BASE_URL + ACAD_YEAR + SEMESTERS + semester + VENUES + JSON_EXTENSION);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONObject of detailed information of all venues.
     *
     * @param semester current semester: 1 -> sem 1
     *                 2 -> sem 2
     *                 3 -> special term 1
     *                 4 -> special term 2
     * @return JSONObject representing the venues
     */
    public JSONObject getVenueInformation(String semester) {
        ApiQuery query = new ApiQuery(BASE_URL + ACAD_YEAR + SEMESTERS + semester + VENUE_INFO + JSON_EXTENSION);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }
}
