package seedu.address.websocket;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Day;
import seedu.address.model.module.Description;
import seedu.address.model.module.EndTime;
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
import seedu.address.model.module.StartTime;
import seedu.address.model.module.Title;
import seedu.address.model.module.Venue;
import seedu.address.model.module.Weeks;

/**
 * Parse data from NusModsApi
 */
public class NusModsParser {
    //TODO: checks to throw parseException if missing compulsory keys
    //      checks to throw invalidValueException if value is invalid
    /**
     * Parse a Module from JSONObject.
     * @param obj JSONObject to parse from.
     * @return parsed Module.
     */
    public static Module parseModule(JSONObject obj) {
        requireNonNull(obj);

        AcadYear acadYear = new AcadYear(obj.getOrDefault("acadYear", "").toString());
        ModuleCode moduleCode = new ModuleCode(obj.getOrDefault("moduleCode", "").toString());
        ModuleId moduleId = new ModuleId(acadYear, moduleCode);

        Title title = new Title(obj.getOrDefault("title", "").toString());
        Description description = new Description(obj.getOrDefault("description", "").toString());

        List<Semester> semesterData = new ArrayList<>();
        if (obj.containsKey("semesterData")) {
            JSONArray jsonSemesterData = (JSONArray) obj.get("semesterData");
            for (int i = 0; i < jsonSemesterData.size(); i++) {
                semesterData.add(parseSemester((JSONObject) jsonSemesterData.get(i)));
            }
        }

        return new Module(moduleId, title, description, semesterData);
    }

    /**
     * Parse a ModuleSummary from JSONArray.
     * @param arr JSONArray to parse from.
     * @return parsed ModuleSummary.
     */
    public static ModuleSummaryList parseModuleSummaryList(JSONArray arr, AcadYear defaultAcadYear) {
        requireNonNull(arr);

        List<ModuleSummary> moduleSummaries = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            JSONObject obj = (JSONObject) arr.get(i);
            AcadYear acadYear = new AcadYear(obj.getOrDefault("acadYear", defaultAcadYear).toString());
            ModuleCode moduleCode = new ModuleCode(obj.getOrDefault("moduleCode", "").toString());
            ModuleId moduleId = new ModuleId(acadYear, moduleCode);

            Title title = new Title(obj.getOrDefault("title", "").toString());

            List<Integer> semesters = new ArrayList<>();
            if (obj.containsKey("semesters")) {
                JSONArray jsonSemesters = (JSONArray) obj.get("semesters");
                for (int j = 0; j < jsonSemesters.size(); j++) {
                    semesters.add(Integer.parseInt(jsonSemesters.get(j).toString()));
                }
            }

            moduleSummaries.add(new ModuleSummary(moduleId, title, semesters));
        }

        return new ModuleSummaryList(moduleSummaries);
    }

    /**
     * Parse a Semester from JSONObject.
     * @param obj JSONObject to parse from.
     * @return parsed Semester.
     */
    public static Semester parseSemester(JSONObject obj) {
        requireNonNull(obj);

        SemesterNo semesterNo = new SemesterNo(obj.getOrDefault("semester", "").toString());

        ArrayList<Lesson> timetable = new ArrayList<>();
        if (obj.containsKey("timetable")) {
            JSONArray arr = (JSONArray) obj.get("timetable");
            for (int i = 0; i < arr.size(); i++) {
                timetable.add(parseLesson((JSONObject) arr.get(i)));
            }
        }

        String examDate = obj.getOrDefault("examDate", "").toString();
        String examDuration = obj.getOrDefault("examDuration", "").toString();
        Exam exam = new Exam(examDate, examDuration);

        return new Semester(semesterNo, timetable, exam);
    }

    /**
     * Parse a Lesson from JSONObject.
     * @param obj JSONObject to parse from.
     * @return parsed Lesson.
     */
    public static Lesson parseLesson(JSONObject obj) {
        requireNonNull(obj);
        LessonNo lessonNo = new LessonNo(obj.getOrDefault("classNo", "").toString());
        StartTime startTime = new StartTime(obj.getOrDefault("startTime", "").toString());
        EndTime endTime = new EndTime(obj.getOrDefault("endTime", "").toString());

        Weeks weeks = obj.containsKey("weeks")
                ? parseWeeks(obj.get("weeks"))
                : Weeks.emptyWeeks();

        LessonType lessonType = new LessonType(obj.getOrDefault("lessonType", "").toString());
        Day day = new Day(obj.getOrDefault("day", "").toString());
        Venue venue = new Venue(obj.getOrDefault("venue", "").toString());

        return new Lesson(lessonNo, startTime, endTime, weeks, lessonType, day, venue);
    }

    /**
     * Parse Weeks from Object, assumed to be either a JSONObject or JSONArray.
     * @param obj Object to parse from.
     * @return parsed Weeks.
     */
    public static Weeks parseWeeks(Object obj) {
        requireNonNull(obj);
        List<Integer> weekNumbers = new ArrayList<>();
        String startDateString = "";
        String endDateString = "";
        int weekInterval = -1;
        int type;

        // weekNumbers only format
        if (obj.toString().startsWith("[")) {
            type = 1;
            JSONArray ja = (JSONArray) obj;
            for (int i = 0; i < ja.size(); i++) {
                weekNumbers.add(Integer.parseInt(ja.get(i).toString()));
            }
        } else {
            assert obj.toString().startsWith("{");
            JSONObject jo = (JSONObject) obj;
            startDateString = jo.get("start").toString();
            endDateString = jo.get("end").toString();

            // start, end and weekNumbers format
            if (jo.containsKey("weeks")) {
                type = 2;
                JSONArray ja = (JSONArray) jo.get("weeks");
                for (int i = 0; i < ja.size(); i++) {
                    weekNumbers.add(Integer.parseInt(ja.get(i).toString()));
                }
            } else { // start, end and weekInterval/default format
                type = 3;
                weekInterval = 1;
                if (jo.containsKey("weekInterval")) {
                    weekInterval = Integer.parseInt(jo.get("weekInterval").toString());
                }
            }
        }

        return new Weeks(weekNumbers, startDateString, endDateString, weekInterval, type);
    }

    /**
     * Parse JSONObject to AcadCalendar
     * @param obj JSONObject to parse from
     * @return parsed AcadCalendar
     */
    public static AcadCalendar parseAcadCalendar(JSONObject obj) {
        requireNonNull(obj);

        Map<String, String> startDates = new LinkedHashMap<>();

        for (Object acadYearObj : obj.keySet()) {
            AcadYear acadYear = new AcadYear(acadYearObj.toString());
            JSONObject semesterNoStartDateObj = (JSONObject) obj.get(acadYearObj.toString());

            for (Object semesterNoObj : semesterNoStartDateObj.keySet()) {
                JSONObject startDateObj = (JSONObject) semesterNoStartDateObj.get(semesterNoObj.toString());
                String acadYearSemNoString = acadYearObj.toString() + " Sem " + semesterNoObj.toString();

                JSONArray arr = (JSONArray) startDateObj.get("start");
                String startDate = arr.get(0) + "-" + arr.get(1) + "-" + arr.get(2);
                startDates.put(acadYearSemNoString, startDate);
            }
        }

        return new AcadCalendar(startDates);
    }

    /**
     * Parse Holidays from a JSONArray.
     * @param arr JSONArray to parse from.
     * @return parsed Holidays object.
     */
    public static Holidays parseHolidays(JSONArray arr) {
        requireNonNull(arr);

        List<String> holidayDates = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            holidayDates.add(arr.get(i).toString());
        }

        return new Holidays(holidayDates);
    }
}
