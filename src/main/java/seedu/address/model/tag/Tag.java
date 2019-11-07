package seedu.address.model.tag;

/**
 * Represents a Tag.
 */
public interface Tag {

    String MESSAGE_CONSTRAINTS = "Tag names should not be more than 25 characters long";
    String VALIDATION_REGEX = "^(\\S){1,25}$";

    boolean isDefault();

    boolean isPriority();

    boolean isSameTag(Tag other);

    String getTagName();

    static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    Tag clone();
}
