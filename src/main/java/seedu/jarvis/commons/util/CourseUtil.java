package seedu.jarvis.commons.util;

import static java.util.Objects.isNull;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.jarvis.commons.exceptions.CourseNotFoundException;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CourseCode;
import seedu.jarvis.model.course.CourseCredit;
import seedu.jarvis.model.course.Description;
import seedu.jarvis.model.course.Faculty;
import seedu.jarvis.model.course.FulfillRequirements;
import seedu.jarvis.model.course.Preclusion;
import seedu.jarvis.model.course.PrereqTree;
import seedu.jarvis.model.course.Title;

/**
 * Finds and reads Json files located in resources/modinfo.
 */
public class CourseUtil {
    public static final String MESSAGE_COURSE_NOT_FOUND =
        "These course(s) could not be found: %1$s";

    /**
     * Regex that removes all whitespace not found within quotes.
     * Adapted from https://stackoverflow.com/a/9584469.
     */
    private static final String REMOVE_WHITESPACE_REGEX = "\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    private static final String COURSE_FOLDER = "/modinfo";

    /**
     * Returns the course prefix of the given course code. For example:
     * "MA1512" returns "MA".
     * "CS3230" returns "CS".
     *
     * @param courseCode the course code
     * @return a {@code String} containing the course prefix
     */
    private static String getCoursePrefix(String courseCode) {
        StringBuilder prefix = new StringBuilder();
        char[] code = courseCode.toCharArray();
        int i = 0;
        while (i < code.length && Character.isLetter(code[i])) {
            prefix.append(code[i++]);
        }
        return prefix.toString();
    }

    /**
     * Removes all spaces in a given {@code String} that are not within quotes.
     *
     * @param string to check
     * @return a new {@code String}
     */
    private static String removeSpacesNotWithinQuotes(String string) {
        return string.replaceAll(REMOVE_WHITESPACE_REGEX, "").trim();
    }

    /**
     * Returns the file path to the relevant json file.
     * @param paths subdirectories and filename
     * @return a {@code String} containing the file path
     */
    private static String getFilePath(String... paths) {
        return Arrays.stream(paths).reduce(COURSE_FOLDER, (p1, p2) -> p1 + "/" + p2);
    }

    /**
     * Returns a {@code String} object pointing to the relevant course file.
     *
     * @param courseCode of the course
     * @return a {@code String}
     */
    private static String getCourseJsonString(String courseCode) throws CourseNotFoundException {
        String upperCasedCourseCode = courseCode.toUpperCase();
        String coursePrefix = getCoursePrefix(courseCode).toUpperCase();
        String intermediateFilePath = getFilePath(coursePrefix, upperCasedCourseCode);
        String finalFilePath = intermediateFilePath.contains(".json")
            ? intermediateFilePath
            : intermediateFilePath + ".json";

        InputStream is = CourseUtil.class.getResourceAsStream(finalFilePath);

        if (isNull(is)) {
            throw new CourseNotFoundException();
        }
        String ret = new BufferedReader(new InputStreamReader(is))
            .lines()
            .collect(Collectors.joining("\n"));
        return removeSpacesNotWithinQuotes(ret);
    }

    /**
     * Returns a {@code Map<String, String>} containing all fields in the specified
     * course file.
     *
     * @param courseCode the course code
     * @return a {@code Map<String, String>} of all values
     */
    private static Map<String, String> getCourseMap(String courseCode)
            throws CourseNotFoundException {
        String json = getCourseJsonString(courseCode);
        JsonNode root;
        try {
            root = new ObjectMapper().readTree(json);
            return getCourseProps(root);
        } catch (IOException e) {
            throw new CourseNotFoundException();
        }
    }

    /**
     * Returns a {@code Map} containing the course properties of {@code root}.
     *
     * @param root a JsonNode containing course data
     * @return a {@code Map} containing course data
     */
    private static Map<String, String> getCourseProps(JsonNode root) {
        Map<String, String> courseProps = new HashMap<>();

        root.fields().forEachRemaining(entry -> {
            if (entry.getValue().isObject() || entry.getValue().isArray()) {
                courseProps.put(entry.getKey(), entry.getValue().toString());
            } else {
                courseProps.put(entry.getKey(), entry.getValue().asText());
            }
        });

        return courseProps;
    }

    /**
     * Returns an {@code Optional<Course>} object containing the information
     * in the course file, and {@code Optional.empty} if the file could not be found.
     *
     * @param code of the course
     * @return a {@code Course} object containing information of the course.
     */
    public static Optional<Course> getCourse(String code) {
        AtomicReference<CourseCode> courseCode = new AtomicReference<>();
        AtomicReference<Title> title = new AtomicReference<>();
        AtomicReference<CourseCredit> courseCredit = new AtomicReference<>();
        AtomicReference<Description> description = new AtomicReference<>();
        AtomicReference<Faculty> faculty = new AtomicReference<>();
        AtomicReference<Preclusion> preclusion = new AtomicReference<>();
        AtomicReference<FulfillRequirements> fulfillRequirements = new AtomicReference<>();
        AtomicReference<PrereqTree> prereqTree = new AtomicReference<>();

        Map<String, String> courseInformation;
        try {
            courseInformation = getCourseMap(code);
        } catch (CourseNotFoundException e) {
            return Optional.empty();
        }
        Map<String, Consumer<String>> mapper = Map.of(
            "courseCode", (prop) -> courseCode.set(new CourseCode(prop)),
            "title", (prop) -> title.set(new Title(prop)),
            "courseCredit", (prop) -> courseCredit.set(new CourseCredit(prop)),
            "description", (prop) -> description.set(new Description(prop)),
            "faculty", (prop) -> faculty.set(new Faculty(prop)),
            "preclusion", (prop) -> preclusion.set(new Preclusion(prop)),
            "fulfillRequirements", (prop) -> fulfillRequirements.set(new FulfillRequirements(prop)),
            "prereqTree", (prop) -> prereqTree.set(new PrereqTree(prop))
        );

        courseInformation.forEach((k, v) -> {
            Consumer<String> setter = mapper.get(k);
            if (setter != null) {
                setter.accept(v);
            }
        });

        return Optional.of(new Course(
            title.get(), faculty.get(), description.get(), courseCode.get(), courseCredit.get(),
            prereqTree.get(), preclusion.get(), fulfillRequirements.get()
        ));
    }

    /**
     * Returns true if the given course exists.
     *
     * @param courseCode of the course
     * @return true if the course exists
     */
    public static boolean courseExists(String courseCode) {
        return getCourse(courseCode).isPresent();
    }

}
