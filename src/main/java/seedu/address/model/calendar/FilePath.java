package seedu.address.model.calendar;

public class FilePath {
    public static final String MESSAGE_CONSTRAINTS =
            "File paths should <<INCLUDE rules for File Paths>>";
    String filePath;

    public FilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public static boolean isValidFilePath(String filePath) {
        String trimmed = filePath.trim();
        return filePath!= null
                && !filePath.isEmpty();
    }
}
