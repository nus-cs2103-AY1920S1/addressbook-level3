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
import seedu.address.model.module.Semester;
import seedu.address.model.module.SemesterNo;
import seedu.address.model.module.StartTime;
import seedu.address.model.module.Title;
import seedu.address.model.module.Venue;
import seedu.address.model.module.Weeks;

/**
 * Parse data from NusModsApi
 */
public class NusModApiParser {
    /**
     * Parse a Module from JSONObject.
     * @param obj JSONObject to parse from.
     * @return parsed Module.
     */
    public static Module parseModule(JSONObject obj) {
        requireNonNull(obj);
        //TODO: better checks to remove invalid/missing data
        ModuleCode moduleCode = obj.containsKey("moduleCode")
                ? new ModuleCode(obj.get("moduleCode").toString())
                : new ModuleCode("");

        Title title = obj.containsKey("title")
                ? new Title(obj.get("title").toString())
                : new Title("");

        Description description = obj.containsKey("description")
                ? new Description(obj.get("description").toString())
                : new Description("");

        AcadYear acadYear = obj.containsKey("acadYear")
                ? new AcadYear(obj.get("acadYear").toString())
                : new AcadYear("");

        List<Semester> semesterData = new ArrayList<>();
        if (obj.containsKey("semesterData")) {
            JSONArray jsonSemesterData = (JSONArray) obj.get("semesterData");
            for (int i = 0; i < jsonSemesterData.size(); i++) {
                semesterData.add(parseSemester((JSONObject) jsonSemesterData.get(i)));
            }
        }

        return new Module(moduleCode, title, description, acadYear, semesterData);
    }

    /**
     * Parse a Semester from JSONObject.
     * @param obj JSONObject to parse from.
     * @return parsed Semester.
     */
    public static Semester parseSemester(JSONObject obj) {
        requireNonNull(obj);
        SemesterNo semesterNo = obj.containsKey("semester")
                ? new SemesterNo(obj.get("semester").toString())
                : new SemesterNo("");

        ArrayList<Lesson> timetable = new ArrayList<>();
        if (obj.containsKey("timetable")) {
            JSONArray arr = (JSONArray) obj.get("timetable");
            for (int i = 0; i < arr.size(); i++) {
                timetable.add(parseLesson((JSONObject) arr.get(i)));
            }
        }

        String examDate = obj.containsKey("examDate") ? obj.get("examDate").toString() : "";
        String examDuration = obj.containsKey("examDuration") ? obj.get("examDuration").toString() : "";
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
        LessonNo lessonNo = obj.containsKey("classNo")
                ? new LessonNo(obj.get("classNo").toString())
                : new LessonNo("");

        StartTime startTime = obj.containsKey("startTime")
                ? new StartTime(obj.get("startTime").toString())
                : new StartTime("");

        EndTime endTime = obj.containsKey("endTime")
                ? new EndTime(obj.get("endTime").toString())
                : new EndTime("");

        Weeks weeks = obj.containsKey("weeks")
                ? parseWeeks(obj.get("weeks"))
                : Weeks.emptyWeeks();

        LessonType lessonType = obj.containsKey("lessonType")
                ? new LessonType(obj.get("lessonType").toString())
                : new LessonType("");

        Day day = obj.containsKey("day")
                ? new Day(obj.get("day").toString())
                : new Day("");

        Venue venue = obj.containsKey("venue")
                ? new Venue(obj.get("venue").toString())
                : new Venue("");

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
        int type; //TODO: convert to enum

        // weekNumbers only format
        if (obj.toString().startsWith("[")) {
            type = 1;
            JSONArray ja = (JSONArray) obj;
            for (int i = 0; i < ja.size(); i++) {
                weekNumbers.add(Integer.parseInt(ja.get(i).toString()));
            }
        } else {
            assert true : obj.toString().startsWith("{");
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
            } else { // start, end and weekInterval format
                assert true : jo.containsKey("weekInterval");
                type = 3;
                weekInterval = Integer.parseInt(jo.get("weekInterval").toString());
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
