package seedu.jarvis.commons.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    private static String getFilePath(String... paths) {
        StringBuilder newFilePath = new StringBuilder(COURSE_FOLDER);
        for (String s : paths) {
            newFilePath.append("/").append(s);
        }
        return newFilePath.toString();
    }

    public static String getCourseJsonString(String coursePrefix, String fileName)
            throws IOException {
        // get File object to the desired file
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String filePath = getFilePath(coursePrefix, fileName);
        File file = new File(classLoader.getResource(filePath).getFile());

        // read File as Stream into StringBuilder
        StringBuilder text = new StringBuilder();
        try (Stream<String> fileStream = Files.lines(file.toPath())) {
            fileStream.forEach(text::append);
        }
        return removeSpacesNotWithinQuotes(text.toString());
    }

    public static String removeSpacesNotWithinQuotes(String string) {
        return string.replaceAll(REMOVE_WHITESPACE_REGEX, "");
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
}
