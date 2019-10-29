package seedu.address.model.module;

import java.util.Objects;

/**
 * Title of the module
 */
public class Title {
    private String title;

    public Title(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Title)) {
            return false;
        }
        Title t = (Title) other;
        if (t == this) {
            return true;
        } else if (t.title.equals(this.title)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
