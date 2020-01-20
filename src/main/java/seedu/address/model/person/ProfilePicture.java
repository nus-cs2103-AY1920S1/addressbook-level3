package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class ProfilePicture {
    public static final String MESSAGE_CONSTRAINTS =
            "File path should not contain special characters, and should not be blank";

    public static final String VALIDATION_REGEX = "([A-Z|a-z]:\\\\[^*|\"<>?\\n]*)|(\\\\\\\\.*?\\\\.*)";

    public final String value;

    public ProfilePicture(String filePath) {
        requireNonNull(filePath);
        //checkArgument(isValidFilePath(filePath), MESSAGE_CONSTRAINTS);
        this.value = filePath;
    }

    public static boolean isValidFilePath(String test) {
        return true;
        //return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ProfilePicture
                && value.equals(((ProfilePicture) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
