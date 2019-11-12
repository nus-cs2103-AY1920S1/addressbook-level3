package seedu.eatme.model.eatery;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.commons.util.AppUtil.checkArgument;

import java.util.HashMap;

/**
 * Represents an Eatery's tag in the EatMe application.
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS =
            "Tags should only contain alphabets and it should not be blank";
    /*
     * The first character of the tag must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "(^[A-Za-z]+[A-Za-z_-]+[A-Za-z]$|^[A-Za-z]+)";

    private static int tagId = -1;
    private static HashMap<String, Tag> tags = new HashMap<String, Tag>();

    private int id;
    private String name;

    /**
     * Constructs a {@code Tag}.
     *
     * @param name A valid tag name.
     */
    public Tag(String name) {
        requireNonNull(name);
        checkArgument(isValidTag(name), MESSAGE_CONSTRAINTS);
        tagId = tagId + 1;
        this.name = name;
        this.id = tagId;
    }

    /**
     * Returns a new Tag object if Tag with the same name doesn't exist,
     * else it returns the existing Tag object.
     */
    public static Tag create(String name) {
        Tag t = tags.get(name);

        if (t == null) {
            Tag newTag = new Tag(name);
            tags.put(newTag.getName(), newTag);
            return newTag;

        } else {
            return t;
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    /**
     * Returns true if the a given string is a valid tag.
     */
    public static boolean isValidTag(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.format("[%s]", name);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Tag)
                && name.equals(((Tag) other).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
