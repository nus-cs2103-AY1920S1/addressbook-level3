package tagline.model.tag;

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
     * Returns true if {@code other} has the same data and ID as this object.
     * This defines a stronger notion of equality between two tags.
     */
    @Override
    public abstract boolean equals(Object other);

    @Override
    public abstract String toString();
}
