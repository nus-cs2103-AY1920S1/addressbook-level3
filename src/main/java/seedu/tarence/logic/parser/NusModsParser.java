package seedu.tarence.logic.parser;

import java.io.FileInputStream;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

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
    private static final String LESSONS_JSON_URL = "./src/main/resources/lessonData/lessons.json";
    private static Map<String, Map<String, Tutorial>> lessonMap = NusModsParser.load();

    /**
     * Loads lesson json data into a Map.
     */
    public static Map<String, Map<String, Tutorial>> load() {
        Map<String, Map<String, Tutorial>> lessonMap = new HashMap<>();
        try (
            InputStream stream = new FileInputStream(LESSONS_JSON_URL);
            JsonReader reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
        ) {
            Gson gson = new GsonBuilder().create();

            reader.beginArray();
            while (reader.hasNext()) {
                LessonObject lesson = gson.fromJson(reader, LessonObject.class);
                if (!lessonMap.containsKey(lesson.ModuleCode)) {
                    lessonMap.put(lesson.ModuleCode, new HashMap<>());
                }
                try {
                    lessonMap.get(lesson.ModuleCode).put(lesson.ClassNo, lesson.toTutorial());
                } catch (ParseException | DateTimeParseException e) {
                    System.out.println(e);
                }
            }
        } catch (IOException e) {
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
                        tutorials.add(lessonMap.get(module).get(classNo));
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

    // CHECKSTYLE.OFF: ParameterName
    // CHECKSTYLE.OFF: MemberName
    /**
     * Intermediate object parsed from JSON. To be converted into Tutorial.
     */
    class LessonObject {
        final String ClassNo;
        final String DayText;
        final String EndTime;
        final String LessonType;
        final String ModuleCode;
        final String StartTime;
        final String WeekText;

        LessonObject(String ClassNo, String DayText, String EndTime,
                String LessonType, String ModuleCode, String StartTime, String WeekText) {
            this.ClassNo = ClassNo;
            this.DayText = DayText;
            this.EndTime = EndTime;
            this.LessonType = LessonType;
            this.ModuleCode = ModuleCode;
            this.StartTime = StartTime;
            this.WeekText = WeekText;
        }

        /**
         * Returns a Tutorial based on its data fields.
         */
        Tutorial toTutorial() throws ParseException {
            ModCode modCode = new ModCode(ModuleCode);
            TutName tutName = new TutName(LessonType + " " + ClassNo);
            DayOfWeek day = ParserUtil.parseDayOfWeek(DayText);
            LocalTime startTime = ParserUtil.parseLocalTime(StartTime);
            LocalTime endTime = ParserUtil.parseLocalTime(EndTime);
            Set<Week> weeks = ParserUtil.parseWeeks(WeekText);
            Duration duration = Duration.between(startTime, endTime);
            List<Student> students = new ArrayList<>();
            return new Tutorial(tutName, day, startTime, weeks, duration, students, modCode);
        }
    }
    // CHECKSTYLE.ON: ParameterName
    // CHECKSTYLE.ON: MemberName
}
