package seedu.address.model.Lesson;

import static java.util.Objects.requireNonNull;

public class ClassName {
    private final String className;

    public ClassName(String className) {
        requireNonNull(className);
        this.className = className;
    }

    @Override
    public String toString() {
        return className;
    }

    @Override
    public int hashCode() {
        return className.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassName // instanceof handles nulls
                && className.equals(((ClassName) other).className)); // state check
    }
}
