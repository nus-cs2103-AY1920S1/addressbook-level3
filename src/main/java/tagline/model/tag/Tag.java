//@@author stevenwjy
package tagline.model.tag;

import tagline.model.Model;

/**
 * Represents a tag in tagline.
 */
public abstract class Tag {
    /**
     * Constructs a {@code Tag} for data from storage.
     */
    public Tag() {
    }

    /**
     * Returns true if this tag is considered as a valid tag in the given model.
     */
    public abstract boolean isValidInModel(Model model);

    /**
     * Returns true if {@code other} has the same data and ID as this object.
     * This defines a stronger notion of equality between two tags.
     */
    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract String toString();
}
