package seedu.pluswork.model.calendar;

/**
 * Util class to create, validate and obtain calendar file path.
 */
public class FilePath {
    public static final String MESSAGE_CONSTRAINTS =
            "File paths should not be blank";

    //Consider adding VALIDATION_REGEX for avoiding whitespace and special characters

    private final String filePath;

    public FilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public static boolean isValidFilePath(String filePath) {
        String trimmed = filePath.trim();
        return trimmed != null
                && !trimmed.isEmpty();
    }
}
