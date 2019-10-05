package seedu.address.model.tag;

/**
 * Represents a Tag.
 */
public interface Tag {

    public boolean isDefault();

    public boolean canBeRenamed();

    public boolean isSameTag(Tag other);

    public String getTagName();

}
