package seedu.address.model.studyplan;

/**
 * Represents the title of a study plan.
 */
public class Title implements Cloneable {
    private String value;

    public Title(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && value.equals(((Title) other).value)); // state check
    }

    public Title clone() throws CloneNotSupportedException {
        return (Title) super.clone();
    }
}
