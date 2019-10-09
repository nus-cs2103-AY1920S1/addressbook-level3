package seedu.address.websocket;

import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import seedu.address.commons.core.AppSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.ModuleCode;
import seedu.address.websocket.util.ApiQuery;
import seedu.address.websocket.util.ParserUtil;
import seedu.address.websocket.util.QueryResult;

/**
 * NUSMods API websocket
 * Refer to https://api.nusmods.com/v2/#/ for more detailed information
 */
public class NusModsApi {
    //TODO: refactor nulls to use Optional pattern.
    private static final String BASE_URL = "https://api.nusmods.com/v2";
    private static final String ACADEMIC_CALENDAR_URL =
            "https://raw.githubusercontent.com/nusmodifications/nusmods/master/website/src/data/academic-calendar.json";
    private static final String HOLIDAYS_URL =
            "https://raw.githubusercontent.com/nusmodifications/nusmods/master/website/src/data/holidays.json";

    private static final String MODULES = "/modules";
    private static final String SLASH = "/";
    private static final String JSON_EXTENSION = ".json";
    private static final String MODULE_LIST = "/moduleList";
    private static final String MODULE_INFO = "/moduleInfo";
    private static final String SEMESTERS = "/semesters";
    private static final String VENUES = "/venues";
    private static final String VENUE_INFO = "/venueInformation";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private AcadYear acadYear;

    /**
     * Default constructor for NusModsApi object, uses AppSettings.DEFAULT_ACAD_YEAR.
     */
    public NusModsApi() {
        this.acadYear = AppSettings.DEFAULT_ACAD_YEAR;
    }

    /**
     * Constructor for NusModsApi object.
     * @param acadYear academic year, e.g. 2019/2020
     */
    public NusModsApi(AcadYear acadYear) {
        this.acadYear = acadYear;
    }

    /**
     * Returns a JSONArray of summaries of all modules in the academic year.
     *
     * @return JSONArray containing module code, title and available semesters for all modules in a year.
     */
    public JSONArray getModuleList() {
        ApiQuery query = new ApiQuery(BASE_URL + SLASH + acadYear.toStringDashed() + MODULE_LIST + JSON_EXTENSION);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONArray obj = ParserUtil.parseStringToJsonArray(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONArray of detailed information about all modules in the academic year.
     *
     * @return JSONArray containing all module info except timetable and prereq tree for all modules in a year.
     */
    public JSONArray getModuleInfo() {
        ApiQuery query = new ApiQuery(BASE_URL + SLASH + acadYear.toStringDashed() + MODULE_INFO + JSON_EXTENSION);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONArray obj = ParserUtil.parseStringToJsonArray(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONObject of all info about moduleCode in the academic year.
     *
     * @param moduleCode code of the specific module.
     * @return JSONObject containing all info available for a specific module including prerequisite tree and timetable.
     */
    public JSONObject getModule(ModuleCode moduleCode) {
        ApiQuery query = new ApiQuery(BASE_URL + SLASH + acadYear.toStringDashed() + MODULES
                + SLASH + moduleCode + JSON_EXTENSION);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONArray of summaries of all venues for the current semester.
     *
     * @param semester current semester: 1 -> sem 1
     *                 2 -> sem 2
     *                 3 -> special term 1
     *                 4 -> special term 2
     * @return JSONArray representing the venues
     */
    public JSONArray getVenues(String semester) {
        ApiQuery query = new ApiQuery(BASE_URL + SLASH + acadYear.toStringDashed() + SEMESTERS
                + semester + VENUES + JSON_EXTENSION);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONArray obj = ParserUtil.parseStringToJsonArray(queryResult.getResponseResult());
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
        ApiQuery query = new ApiQuery(BASE_URL + SLASH + acadYear.toStringDashed() + SEMESTERS
                + semester + VENUE_INFO + JSON_EXTENSION);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONObject of academic calendar containing semester start dates.
     * @return JSONObject representing academic calendar.
     */
    public JSONObject getAcademicCalendar() {
        ApiQuery query = new ApiQuery(ACADEMIC_CALENDAR_URL);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONObject obj = ParserUtil.parseStringToJsonObject(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }

    /**
     * Returns a JSONArray of dates of public holidays in Singapore, adjusted for observance,
     * i.e. if the holiday falls on a weekend and is observed on next Monday, the next Monday's date is used instead.
     * @return JSONArray representing public holidays in Singapore.
     */
    public JSONArray getHolidays() {
        ApiQuery query = new ApiQuery(HOLIDAYS_URL);
        QueryResult queryResult = query.execute();
        if (queryResult.process(logger)) {
            JSONArray obj = ParserUtil.parseStringToJsonArray(queryResult.getResponseResult());
            return obj;
        } else {
            return null;
        }
    }
}
