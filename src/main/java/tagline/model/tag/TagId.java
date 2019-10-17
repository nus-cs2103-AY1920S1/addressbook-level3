package tagline.model.tag;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.AppUtil.checkArgument;

/**
 * Represents a tag id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTagId(String)}
 */
public class TagId {

    public static final String MESSAGE_CONSTRAINTS = "TagId numbers should only contain numbers";

    public static final String VALIDATION_REGEX = "\\d+";

    private static long lastId = 0; // temporary generator for tag id.

    public final Long value;

    /**
     * Constructs a {@code TagId} from {@code Long}.
     *
     * @param tagId A valid tag id.
     */
    public TagId(Long tagId) {
        requireNonNull(tagId);
        value = tagId;
    }

    /**
     * Constructs a {@code TagId} from {@code String}.
     *
     * @param tagId A valid tag id.
     */
    public TagId(String tagId) {
        requireNonNull(tagId);
        checkArgument(isValidTagId(tagId), MESSAGE_CONSTRAINTS);
        value = Long.valueOf(tagId);
    }

    public TagId() {
        value = lastId++;
    }

    /**
     * Returns true if a given string is a valid noteID number.
     */
    public static boolean isValidTagId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TagId // instanceof handles nulls
            && value.equals(((TagId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
