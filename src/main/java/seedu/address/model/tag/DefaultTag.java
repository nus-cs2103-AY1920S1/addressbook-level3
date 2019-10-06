package seedu.address.model.tag;

import java.util.HashSet;

import seedu.address.model.module.Module;

/**
 * Represents a  default Tag.
 */
public class DefaultTag implements Tag {

    private Default defaultType;
    private HashSet<Module> attachedModules = new HashSet<Module>();

    /**
     * Constructs a {@code DefaultTag}.
     * @param defaultType A default tag type in {@code Default}.
     */
    public DefaultTag(Default defaultType) {
        this.defaultType = defaultType;
    }

    /**
     * Checks if the tag is a default tag.
     * @return True.
     */
    public boolean isDefault() {
        return true;
    }

    /**
     * Returns the default type of the current tag.
     * @return The default type.
     */
    public Default getDefaultType() {
        return defaultType;
    }

    /**
     * Checks if the tag can be renamed.
     * @return False as default tags cannot be renamed.
     */
    public boolean canBeRenamed() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DefaultTag // instanceof handles nulls
            && defaultType.equals(((DefaultTag) other).getDefaultType())); // state check
    }

    @Override
    public int hashCode() {
        return defaultType.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + defaultType.getDefaultName() + ']';
    }

}
