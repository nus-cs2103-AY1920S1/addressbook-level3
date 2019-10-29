package seedu.tarence.logic.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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
    private static Map<String, Map<String, Map<String, Tutorial>>> lessonMap = NusModsParser.load();

    /**
     * Loads lesson json data into a Map(moduleCode > lessonType > classNo).
     */
    public static Map<String, Map<String, Map<String, Tutorial>>> load() {
        Map<String, Map<String, Map<String, Tutorial>>> lessonMap = new HashMap<>();
        try (
            InputStream stream = MainApp.class.getResourceAsStream(LESSONS_JSON_URL);
            JsonReader reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
        ) {
            Gson gson = new GsonBuilder().create();

            reader.beginArray();
            while (reader.hasNext()) {
                ModObject mod = gson.fromJson(reader, ModObject.class);

                if (!lessonMap.containsKey(mod.moduleCode)) {
                    lessonMap.put(mod.moduleCode, new HashMap<>());
                }
                for (LessonObject lesson : mod.lessons) {
                    String lessonTypeCode = lesson.lessonType.substring(0, 3).toUpperCase();
                    if (!lessonMap.get(mod.moduleCode).containsKey(lessonTypeCode)) {
                        lessonMap.get(mod.moduleCode).put(lessonTypeCode, new HashMap<>());
                    }
                    try {
                        Tutorial tutorial = lesson.toTutorial(mod.moduleCode);
                        lessonMap.get(mod.moduleCode).get(lessonTypeCode).put(lesson.classNo,
                            tutorial);
                    } catch (ParseException | DateTimeParseException | IllegalArgumentException e) {
                        System.out.println(mod.moduleCode);
                        System.out.println(e);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (JsonSyntaxException e) {
            System.out.println(e);
        }
        return lessonMap;
    }

    /**
     * Parses the NUSMods url to return a List of Tutorials. Throws NullPointerException error
     * if the specified ModCode and ClassNo combination in the url can't be found in the lessonMap.
     */
    public static List<Tutorial> urlToTutorials(String url) {
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
                        tutorials.add(lessonMap.get(module).get(lessonType).get(classNo));
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
     * Intermediate object parsed from JSON. Contains LessonObjects.
     */
    class ModObject {
        final List<LessonObject> lessons;
        final String moduleCode;

        ModObject(List<LessonObject> lessons, String moduleCode) {
            this.moduleCode = moduleCode;
            this.lessons = lessons;
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
    // CHECKSTYLE.ON: ParameterName
    // CHECKSTYLE.ON: MemberName
}
