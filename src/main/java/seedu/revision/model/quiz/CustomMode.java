package seedu.revision.model.quiz;

import java.util.function.Predicate;

import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.predicates.CategoryPredicate;
import seedu.revision.model.answerable.predicates.DifficultyPredicate;

/**
 * CustomMode which can be adapted according to user input.
 */
public class CustomMode extends Mode {
    private int time;
    private DifficultyPredicate difficultyPredicate;
    private CategoryPredicate categoryPredicate;

    public CustomMode(String value) {
        super(value);
        this.time = NormalMode.NORMAL_MODE_TIME;
        this.combinedPredicate = NormalMode.NORMAL_MODE_PREDICATE;
    }

    /**
     * Initialises a CustomMode with the time and predicate provided.
     * @param value value of the custom mode is "custom" by default.
     * @param time amount of time per level provided by the user.
     * @param combinedPredicate predicate provided by the user used to filter the quiz questions.
     */
    public CustomMode(String value, int time, Predicate<Answerable> combinedPredicate) {
        super(value);
        this.time = time;
        this.combinedPredicate = combinedPredicate;
    }

    /**
     * Sets the time for the custom mode.
     * @param time time that will be set.
     * @return {@CustomMode} object with its time set.
     */
    public CustomMode withTime(int time) {
        this.time = time;
        return this;
    }

    /**
     * Sets the combinedPredicate for the custom mode.
     * @param combinedPredicate combinedPredicate that will be set.
     * @return {@CustomMode} object with its combinedPredicate set.
     */
    public CustomMode withCategoryPredicate(Predicate<Answerable> combinedPredicate) {
        this.combinedPredicate = combinedPredicate;
        return this;
    }

    /**
     * Initialises the {@CustomMode} with the user chosen timer and predicate.
     * @return {@CustomMode} with the customised time and predicate.
     */
    public CustomMode build() {
        return new CustomMode(value, time, combinedPredicate);
    }
}
