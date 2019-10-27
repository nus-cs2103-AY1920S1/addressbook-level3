package seedu.jarvis.model.course;

import java.util.Objects;

import seedu.jarvis.commons.util.StringUtil;

/**
 * Represents text output of the Course Planner caused by Course Planner commands.
 */
public class CourseDisplayText {
    private String value = "";

    public String get() {
        return value;
    }

    public String get(int lineCharacterLimit) {
        return StringUtil.asLimitedCharactersPerLine(get(), lineCharacterLimit);
    }

    public void setValue(String toSet) {
        value = toSet;
    }

    public void setValue(Course course) {
        value = course.toDisplayableString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CourseDisplayText that = (CourseDisplayText) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
