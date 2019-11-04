package seedu.address.websocket;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Description;
import seedu.address.model.module.Exam;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Lesson;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.LessonType;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleId;
import seedu.address.model.module.ModuleSummary;
import seedu.address.model.module.ModuleSummaryList;
import seedu.address.model.module.Semester;
import seedu.address.model.module.SemesterNo;
import seedu.address.model.module.Title;
import seedu.address.model.module.Venue;
import seedu.address.model.module.Weeks;
import seedu.address.model.module.WeeksType;
import seedu.address.model.module.exceptions.LessonTypeNotFoundException;
import seedu.address.model.module.exceptions.SemesterNoNotFoundException;

/**
 * Parser to parse NusMods-related data.
 */
public class NusModsParser {
    public static final int GMT_OFFSET_SINGAPORE = 8;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-M-d");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    /**
     * Checks if the JSONObject has the required keys.
     * @param obj JSONObject to check.
     * @param keys Strings of compulsory keys to check.
     * @throws ParseException if JSONObject is missing any of the given keys.
     */
    private static void requireCompulsoryKeys(JSONObject obj, String... keys) throws ParseException {
        for (String key : keys) {
            if (!obj.containsKey(key)) {
                throw new ParseException("Missing key " + key + " in obj: " + obj.toString());
            }
        }
    }

    /**
     * Parses a Module from JSONObject.
     * @param obj JSONObject to parse from.
     * @return parsed Module.
     * @throws ParseException if missing compulsory keys.
     */
    public static Module parseModule(JSONObject obj) throws ParseException {
        requireNonNull(obj);
        requireCompulsoryKeys(obj, "acadYear", "moduleCode", "title", "semesterData");

        AcadYear acadYear = new AcadYear(obj.get("acadYear").toString());
        ModuleCode moduleCode = new ModuleCode(obj.get("moduleCode").toString());
        ModuleId moduleId = new ModuleId(acadYear, moduleCode);

        Title title = new Title(obj.get("title").toString());
        Description description = new Description(obj.getOrDefault("description", "").toString());

        List<Semester> semesterData = new ArrayList<>();
        JSONArray jsonSemesterData = (JSONArray) obj.get("semesterData");
        for (int i = 0; i < jsonSemesterData.size(); i++) {
            semesterData.add(parseSemester((JSONObject) jsonSemesterData.get(i)));
        }

        return new Module(moduleId, title, description, semesterData);
    }

    /**
     * Parses a List of ModuleSummaries from JSONArray.
     * @param arr JSONArray to parse from.
     * @param defaultAcadYear Default academic year if academic year is missing from a ModuleSummary.
     * @return parsed ModuleSummaryList.
     * @throws ParseException if missing compulsory keys.
     */
    public static ModuleSummaryList parseModuleSummaryList(JSONArray arr, AcadYear defaultAcadYear)
            throws ParseException {
        requireNonNull(arr);

        List<ModuleSummary> moduleSummaries = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);
            requireCompulsoryKeys(obj, "moduleCode", "title", "semesters");
            AcadYear acadYear = new AcadYear(obj.getOrDefault("acadYear", defaultAcadYear).toString());
            ModuleCode moduleCode = new ModuleCode(obj.get("moduleCode").toString());
            ModuleId moduleId = new ModuleId(acadYear, moduleCode);
            Title title = new Title(obj.get("title").toString());

            List<Integer> semesters = new ArrayList<>();
            JSONArray jsonSemesters = (JSONArray) obj.get("semesters");
            for (int j = 0; j < jsonSemesters.size(); j++) {
                semesters.add(Integer.parseInt(jsonSemesters.get(j).toString()));
            }

            moduleSummaries.add(new ModuleSummary(moduleId, title, semesters));
        }

        return new ModuleSummaryList(moduleSummaries);
    }

    /**
     * Parses a Semester from JSONObject.
     * @param obj JSONObject to parse from.
     * @return parsed Semester.
     * @throws ParseException if missing compulsory keys.
     */
    public static Semester parseSemester(JSONObject obj) throws ParseException {
        requireNonNull(obj);
        requireCompulsoryKeys(obj, "semester", "timetable");

        SemesterNo semesterNo;
        try {
            semesterNo = SemesterNo.findSemesterNo(obj.get("semester").toString());
        } catch (SemesterNoNotFoundException e) {
            throw new ParseException("Semester number not found: " + obj.toString());
        }

        ArrayList<Lesson> timetable = new ArrayList<>();
        JSONArray arr = (JSONArray) obj.get("timetable");
        for (int i = 0; i < arr.size(); i++) {
            timetable.add(parseLesson((JSONObject) arr.get(i)));
        }

        if (obj.containsKey("examDate")) {
            // Remove Z at end, which represents UTC in ISO 8601 format.
            String examDateString = obj.get("examDate").toString().replace("Z", "");
            // For now, we're using LocalDateTime (not tz-aware), so just add Singapore's GMT OFFSET.
            LocalDateTime examDate = LocalDateTime.parse(examDateString).plusHours(GMT_OFFSET_SINGAPORE);
            requireCompulsoryKeys(obj, "examDuration"); // has examDate but no examDuration.
            int examDuration = Integer.parseInt(obj.get("examDuration").toString());
            Exam exam = new Exam(examDate, examDuration);
            return new Semester(semesterNo, timetable, exam);
        }

        return new Semester(semesterNo, timetable);
    }

    /**
     * Parses a Lesson from JSONObject.
     * @param obj JSONObject to parse from.
     * @return parsed Lesson.
     * @throws ParseException if missing compulsory keys.
     */
    public static Lesson parseLesson(JSONObject obj) throws ParseException {
        requireNonNull(obj);
        requireCompulsoryKeys(obj, "classNo", "startTime", "endTime", "weeks", "lessonType", "day", "venue");

        LessonNo lessonNo = new LessonNo(obj.get("classNo").toString());
        LocalTime startTime = LocalTime.parse(obj.get("startTime").toString(), TIME_FORMATTER);
        LocalTime endTime = LocalTime.parse(obj.get("endTime").toString(), TIME_FORMATTER);

        Weeks weeks = parseWeeks(obj.get("weeks"));

        LessonType lessonType;
        try {
            lessonType = LessonType.findLessonType(obj.get("lessonType").toString());
        } catch (LessonTypeNotFoundException e) {
            throw new ParseException("Lesson type not found: " + obj.toString());
        }

        DayOfWeek day = getDayOfWeek(obj.get("day").toString());
        Venue venue = new Venue(obj.get("venue").toString());
        try {
            return new Lesson(lessonNo, startTime, endTime, weeks, lessonType, day, venue);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    private static DayOfWeek getDayOfWeek(String dayString) {
        String dayStringTrimmedUppercased = dayString.trim().toUpperCase();
        return DayOfWeek.valueOf(dayStringTrimmedUppercased);
    }

    /**
     * Parses Weeks from Object, assumed to be either a JSONObject or JSONArray.
     * @param obj Object to parse from.
     * @return parsed Weeks.
     * @throws ParseException if missing compulsory keys.
     */
    public static Weeks parseWeeks(Object obj) throws ParseException {
        requireNonNull(obj);
        List<Integer> weekNumbers = new ArrayList<>();
        LocalDate startDate = LocalDate.MIN;
        LocalDate endDate = LocalDate.MAX;
        int weekInterval = -1;
        WeeksType type;

        // weekNumbers only format
        if (obj.toString().startsWith("[")) {
            type = WeeksType.WEEK_NUMBERS;
            JSONArray ja = (JSONArray) obj;
            for (int i = 0; i < ja.size(); i++) {
                weekNumbers.add(Integer.parseInt(ja.get(i).toString()));
            }
        } else {
            assert obj.toString().startsWith("{");
            JSONObject jo = (JSONObject) obj;
            requireCompulsoryKeys(jo, "start", "end");
            startDate = LocalDate.parse(jo.get("start").toString(), DATE_FORMATTER);
            endDate = LocalDate.parse(jo.get("end").toString(), DATE_FORMATTER);

            // start, end and weekNumbers format
            if (jo.containsKey("weeks")) {
                type = WeeksType.START_END_WEEK_NUMBERS;
                JSONArray ja = (JSONArray) jo.get("weeks");
                for (int i = 0; i < ja.size(); i++) {
                    weekNumbers.add(Integer.parseInt(ja.get(i).toString()));
                }
            } else { // start, end and weekInterval format
                type = WeeksType.START_END_WEEK_INTERVAL;
                // Defaults to 1 if missing a specified weekInterval
                weekInterval = Integer.parseInt(jo.getOrDefault("weekInterval", "1").toString());
            }
        }

        try {
            return new Weeks(weekNumbers, startDate, endDate, weekInterval, type);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Parses JSONObject to AcadCalendar. The JSONObject is expected to be correctly formatted.
     * @param obj JSONObject to parse from.
     * @return parsed AcadCalendar.
     */
    public static AcadCalendar parseAcadCalendar(JSONObject obj) {
        requireNonNull(obj);

        Map<Map.Entry<AcadYear, SemesterNo>, LocalDate> startDates = new LinkedHashMap<>();

        for (Object acadYearKey : obj.keySet()) {
            AcadYear acadYear = new AcadYear(acadYearKey.toString());
            JSONObject innerObj = (JSONObject) obj.get(acadYearKey.toString());

            for (Object semesterNoKey : innerObj.keySet()) {
                // From key from AcadYear and SemesterNo
                SemesterNo semesterNo = SemesterNo.findSemesterNo(semesterNoKey.toString());
                Map.Entry<AcadYear, SemesterNo> key = Map.entry(acadYear, semesterNo);
                // Get start date value
                JSONObject startDateObj = (JSONObject) innerObj.get(semesterNoKey.toString());
                JSONArray arr = (JSONArray) startDateObj.get("start");
                String startDateString = arr.get(0) + "-" + arr.get(1) + "-" + arr.get(2);
                LocalDate startDate = LocalDate.parse(startDateString, DATE_FORMATTER);
                // Put to map
                startDates.put(key, startDate);
            }
        }

        return new AcadCalendar(startDates);
    }

    /**
     * Parses Holidays from a JSONArray. The JSONArray is expected to be correctly formatted.
     * @param arr JSONArray to parse from.
     * @return parsed Holidays object.
     */
    public static Holidays parseHolidays(JSONArray arr) {
        requireNonNull(arr);

        List<LocalDate> holidayDates = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            LocalDate holiday = LocalDate.parse(arr.get(i).toString(), DATE_FORMATTER);
            holidayDates.add(holiday);
        }

        return new Holidays(holidayDates);
    }
}
