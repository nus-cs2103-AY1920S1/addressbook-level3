package seedu.tarence.logic.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import seedu.tarence.MainApp;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

/**
 * Handles parsing of NUSMods urls.
 */
public class NusModsParser {
    private static final String LESSONS_JSON_URL = "/nusmods/lessons.json";
    private static Map<String, Map<String, Map<String, Map<String, Tutorial>>>> lessonMap = NusModsParser.load();

    /**
     * Loads lesson json data into a Map(moduleCode > semester > lessonType > classNo).
     */
    public static Map<String, Map<String, Map<String, Map<String, Tutorial>>>> load() {
        Map<String, Map<String, Map<String, Map<String, Tutorial>>>> lessonMap = new HashMap<>();
        try (
            InputStream stream = MainApp.class.getResourceAsStream(LESSONS_JSON_URL);
            JsonReader reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
        ) {
            Gson gson = new GsonBuilder().create();

            reader.beginArray();
            while (reader.hasNext()) {
                ModObject modObject = gson.fromJson(reader, ModObject.class);

                if (lessonMap.containsKey(modObject.moduleCode)) {
                    System.out.println("Duplicate " + modObject.moduleCode);
                }
                lessonMap.put(modObject.moduleCode, loadModObject(modObject));
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (JsonSyntaxException e) {
            System.out.println(e);
        }
        return lessonMap;
    }

    /**
     * Loads ModObject data into a Map(semester > lessonType > classNo).
     */
    private static Map<String, Map<String, Map<String, Tutorial>>> loadModObject(ModObject modObject) {
        Map<String, Map<String, Map<String, Tutorial>>> modMap = new HashMap<>();
        for (SemesterObject semesterObject : modObject.semesters) {
            modMap.put(semesterObject.semester, new HashMap<>());
            for (LessonObject lesson : semesterObject.timetable) {
                String lessonTypeCode = lesson.lessonType.substring(0, 3).toUpperCase();
                if (!modMap.get(semesterObject.semester).containsKey(lessonTypeCode)) {
                    modMap.get(semesterObject.semester).put(lessonTypeCode, new HashMap<>());
                }
                try {
                    Tutorial tutorial = lesson.toTutorial(modObject.moduleCode);
                    modMap.get(semesterObject.semester).get(lessonTypeCode).put(lesson.classNo,
                        tutorial);
                } catch (ParseException | DateTimeParseException | IllegalArgumentException e) {
                    System.out.println(modObject.moduleCode);
                    System.out.println(lesson.lessonType + " " + lesson.classNo);
                    System.out.println(e);
                }
            }
        }
        return modMap;
    }

    /**
     * Parses the NUSMods url to return a List of Tutorials. Throws NullPointerException error
     * if the specified ModCode and ClassNo combination in the url can't be found in the lessonMap.
     */
    public static List<Tutorial> urlToTutorials(String url) throws ParseException {
        Integer semesterIndex = "https://nusmods.com/timetable/sem-".length();
        String semester = url.trim().charAt(semesterIndex) + "";
        if (!Arrays.asList("1", "2", "3", "4").contains(semester)) {
            throw new ParseException("Unable to find semester");
        }
        List<Tutorial> tutorials = new ArrayList<>();
        String queryString = url.split("share\\?")[1];
        String[] modulesQuery = queryString.split("&");
        for (String moduleQuery : modulesQuery) {
            try {
                String module = moduleQuery.split("=")[0];
                String[] lessonsQuery = moduleQuery.split("=")[1].split(",");
                for (String lessonQuery : lessonsQuery) {
                    String lessonType = lessonQuery.split(":")[0];
                    if (lessonType.equals("LEC")) {
                        continue;
                    }
                    String classNo = lessonQuery.split(":")[1];
                    try {
                        tutorials.add(lessonMap.get(module).get(semester).get(lessonType).get(classNo));
                    } catch (NullPointerException e) {
                        System.out.println(lessonQuery + " could not be found.");
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println(e);
            }
        }
        return tutorials;
    }

    /**
     * Intermediate object parsed from JSON. Contains SemesterObjects.
     */
    class ModObject {
        final List<SemesterObject> semesters;
        final String moduleCode;

        ModObject(List<SemesterObject> semesters, String moduleCode) {
            this.moduleCode = moduleCode;
            this.semesters = semesters;
        }
    }

    /**
     * Intermediate object parsed from JSON. Contains LessonObjects.
     */
    class SemesterObject {
        final List<LessonObject> timetable;
        final String semester;

        SemesterObject(List<LessonObject> timetable, String semester) {
            this.semester = semester;
            this.timetable = timetable;
        }
    }

    /**
     * Intermediate object parsed from JSON. To be converted into Tutorial.
     */
    class LessonObject {
        final String classNo;
        final String startTime;
        final String endTime;
        final List<String> weeks;
        final String day;
        final String lessonType;

        LessonObject(String classNo, String startTime, String endTime, List<String> weeks,
                String day, String lessonType) {
            this.classNo = classNo;
            this.startTime = startTime;
            this.endTime = endTime;
            this.weeks = weeks;
            this.day = day;
            this.lessonType = lessonType;
        }

        /**
         * Returns a Tutorial based on its data fields.
         */
        Tutorial toTutorial(String moduleCode) throws ParseException {
            ModCode modCode = new ModCode(moduleCode);
            TutName tutName = new TutName(lessonType + " " + classNo);
            DayOfWeek day = ParserUtil.parseDayOfWeek(this.day);
            LocalTime startTime = ParserUtil.parseLocalTime(this.startTime);
            LocalTime endTime = ParserUtil.parseLocalTime(this.endTime);
            Set<Week> weeks = null;
            try {
                weeks = new TreeSet<Week>(this.weeks.stream()
                    .<Week>map(w -> new Week(Integer.parseInt(w)))
                    .collect(Collectors.toList()));
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
            Duration duration = Duration.between(startTime, endTime);
            List<Student> students = new ArrayList<>();
            return new Tutorial(tutName, day, startTime, weeks, duration, students, modCode);
        }
    }
}
