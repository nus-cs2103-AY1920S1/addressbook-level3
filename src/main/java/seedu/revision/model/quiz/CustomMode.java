package seedu.revision.model.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.revision.commons.util.AppUtil.checkArgument;

import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.category.Category;
import seedu.revision.ui.bar.Timer;

/**
 * Represents the mode of a quiz in the Revision Tool.
 * Guarantees: immutable; is valid as declared in {@link #isValidMode(String)}
 */
public class CustomMode extends Mode {

    int timePerQuestion;
    Difficulty difficulty;
    Category category;

    public CustomMode(String value, int timePerQuestion, Difficulty difficulty, Category category) {
        super(value);
        this.timePerQuestion = timePerQuestion;
        this.difficulty = difficulty;
        this.category = category;
    }




    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomMode // instanceof handles nulls
                && value.equals(((CustomMode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
