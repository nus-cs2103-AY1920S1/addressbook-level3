package seedu.revision.model.quiz;

import java.util.function.Predicate;

import seedu.revision.model.answerable.Answerable;

/**
 * CustomMode which can be adapted according to user input.
 */
public class CustomMode extends Mode {

    public CustomMode() {
        super(Modes.CUSTOM.toString());
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
     * Sets the combinedPredicate for the custom mode.
     * @param combinedPredicate combinedPredicate that will be set.
     * @return {@Mode} object with its combinedPredicate set.
     */
    public Mode withCombinedPredicate(Predicate<Answerable> combinedPredicate) {
        this.combinedPredicate = combinedPredicate;
        return this;
    }


    /**
     * Sets the time for the custom mode.
     * @param time time that will be set.
     * @return {@Mode} object with its time set.
     */
    public Mode withTime(int time) {
        this.time = time;
        return this;
    }

    /**
     * Builds the CustomMode with the parameters provided.
     * @return CustomMode according to user input.
     */
    public Mode build() {
        return new CustomMode(value, time, combinedPredicate);
    }

    public int getTime(int nextLevel) {
        return this.time;
    }
}
