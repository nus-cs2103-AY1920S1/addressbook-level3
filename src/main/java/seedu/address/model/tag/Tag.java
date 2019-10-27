package seedu.address.model.tag;

/**
 * Represents a Tag.
 */
public interface Tag {

    String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    String VALIDATION_REGEX = "\\p{Alnum}+";

    boolean isDefault();

    boolean isPriority();

    boolean isSameTag(Tag other);

    String getTagName();

    static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

}
