package seedu.jarvis.model.course;

import java.util.Objects;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import seedu.jarvis.commons.util.StringUtil;

/**
 * Represents text output of the Course Planner caused by Course Planner commands.
 */
public class CourseDisplayText {
    private SimpleStringProperty value = new SimpleStringProperty("");

    /**
     * Returns an {@code ObservableStringValue} containing the contents of this
     * object.
     *
     * @return an {@code ObservableStringValue}
     */
    public ObservableStringValue get() {
        return value;
    }

    /**
     * Returns this object's contents as a {@code String}.
     *
     * @return a {@code String}
     */
    public String getAsString() {
        return value.get();
    }

    /**
     * Sets the value of the {@code String} held by this object, cut down by words
     * to the number of characters set in {@code limit} per line.
     *
     * @param toSet {@code String} to set
     * @param limit of characters per line
     */
    public void setValueWithCharLimit(String toSet, int limit) {
        value.setValue(StringUtil.asLimitedCharactersPerLine(toSet, limit));
    }

    public void setValue(String toSet) {
        value.setValue(toSet);
    }

    public void setValue(Course course) {
        value.setValue(course.toDisplayableString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CourseDisplayText that = (CourseDisplayText) o;
        String thisString = value.get();
        String thatString = that.value.get();
        return thisString.equals(thatString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
