package seedu.address.model.project;

import static java.util.Objects.requireNonNull;

public class Title {

    public final String title;

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_CONSTRAINTS =
            "Title should only contain alphanumeric characters and spaces, and it should not be blank";

    public Title(String title) {
        requireNonNull(title);
        this.title = title;
    }

    /**
     * Returns true if a given string is a valid title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.title;
    }
}
