package tagline.model.note;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable
 */
public class Title {

    public final String titleDescription;

    /**
     * Constructs a {@code Name}.
     *
     * @param title A valid title.
     */
    public Title(String title) {
        requireNonNull(title);
        titleDescription = title;
    }

    @Override
    public String toString() {
        return titleDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title// instanceof handles nulls
                && titleDescription.equals(((Title) other).titleDescription)); // state check
    }

    @Override
    public int hashCode() {
        return titleDescription.hashCode();
    }

}
