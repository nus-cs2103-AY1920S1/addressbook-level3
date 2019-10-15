package seedu.address.model.tag;

/**
 * Represents a Tag.
 */
public interface Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public boolean isDefault();

    public boolean isSameTag(Tag other);

    public String getTagName();

    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

}
