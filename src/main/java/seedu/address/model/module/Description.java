package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Description of the module
 */
public class Description {
    private String description;

    public Description(String description) {
        requireNonNull(description);
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Description)) {
            return false;
        }
        Description d = (Description) other;
        if (d == this) {
            return true;
        } else if (d.description.equals(this.description)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
