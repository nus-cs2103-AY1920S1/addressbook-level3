package seedu.address.model.note;

/**
 * Content of the note
 */
public class Content {

    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    private String content;

    public Content(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }

    public static boolean isValidContent(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Content // instanceof handles nulls
                && content.equals(((Content) other).content)); // state check
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
