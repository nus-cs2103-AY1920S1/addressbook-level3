package seedu.revision.model.quiz;

import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

import java.util.function.Predicate;

import seedu.revision.model.answerable.Answerable;

/** NormalMode class which sets a default time and initialises the quiz with all questions in the question bank.**/
public class NormalMode extends Mode {
    public static final int NORMAL_MODE_TIME = 30;
    public static final Predicate<Answerable> NORMAL_MODE_PREDICATE = PREDICATE_SHOW_ALL_ANSWERABLE;

    /**
     * Constructs a {@code NormalMode}. Uses defensive programming to immediately provide default values to
     * time and combinedPredicate.
     */
    public NormalMode() {
        super(Modes.NORMAL.toString());
        this.time = NORMAL_MODE_TIME;
        this.combinedPredicate = NORMAL_MODE_PREDICATE;
    }

    public int getTime(int nextLevel) {
        return this.time;
    }
}

