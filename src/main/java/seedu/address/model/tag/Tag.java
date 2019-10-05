package seedu.address.model.tag;

import java.util.HashSet;

import seedu.address.model.module.Module;

/**
 * Represents a Tag.
 */
public interface Tag {

    public boolean isDefault();

    public boolean canBeRenamed();

    public boolean isSameTag(Tag other);

    public String getTagName();

}
