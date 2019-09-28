package seedu.jarvis.commons.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Finds and reads Json files located in resources/modinfo.
 */
public class CourseUtil {
    /**
     * Regex that removes all whitespace not found within quotes.
     * Adapted from https://stackoverflow.com/a/9584469.
     */
    private static String REMOVE_WHITESPACE_REGEX = "\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    private static String COURSE_FOLDER = "modinfo";

    private static String removeSpacesNotWithinQuotes(String string) {
        return string.replaceAll(REMOVE_WHITESPACE_REGEX, "").trim();
    }

    private static String getFilePath(String... paths) {
        StringBuilder newFilePath = new StringBuilder(COURSE_FOLDER);
        for (String s : paths) {
            newFilePath.append("/").append(s);
        }
        return newFilePath.toString();
    }

    private static File getCourseFile(String courseCode)
            throws IOException {
        String coursePrefix = getCoursePrefix(courseCode);
        String fileName = (courseCode.contains(".json")) ? courseCode : courseCode + ".json";

        // get File object to the desired file
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String filePath = getFilePath(coursePrefix, fileName);
        try {
            return new File(Objects.requireNonNull(classLoader.getResource(filePath)).getFile());
        } catch (NullPointerException e) {
            throw new IOException(fileName + ": file not found");
        }
    }

    /**
     * Obtains course information as a {@code json String}.
     *
     * @param courseCode of the course
     * @return a {@code json String}
     * @throws IOException if the file is not found
     */
    public static String getCourseJsonString(String courseCode)
            throws IOException {
        File file = getCourseFile(courseCode);

        // read File as Stream into StringBuilder
        StringBuilder text = new StringBuilder();
        try (Stream<String> fileStream = Files.lines(file.toPath())) {
            fileStream.forEach(text::append);
        } catch (NullPointerException e) {
            throw new IOException(courseCode + ": file not found");
        }
        return removeSpacesNotWithinQuotes(text.toString());
    }

    /**
     * Returns the course prefix of the given course code. For example:
     * "MA1512" returns "MA".
     * "CS3230" returns "CS".
     *
     * @param courseCode the course code
     * @return a {@code String} containing the course prefix
     */
    public static String getCoursePrefix(String courseCode) {
        StringBuilder prefix = new StringBuilder();
        char[] code = courseCode.toCharArray();
        int i = 0;
        while (i < code.length && Character.isLetter(code[i])) {
            prefix.append(code[i++]);
        }
        return prefix.toString();
    }

    /**
     * Returns a {@code Map<String, String>} containing all fields in the specified
     * course file.
     *
     * @param courseCode the course code
     * @return a {@code Map<String, String>} of all values
     * @throws IOException if the file is not found
     */
    public static Map<String, String> getJsonMap(String courseCode)
            throws IOException {
        String json = getCourseJsonString(courseCode);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        Map<String, String> courseProps = new HashMap<>();
        root.fields().forEachRemaining(entry -> {
            courseProps.put(entry.getKey(), entry.getValue().asText());
        });
        return courseProps;
    }
}
