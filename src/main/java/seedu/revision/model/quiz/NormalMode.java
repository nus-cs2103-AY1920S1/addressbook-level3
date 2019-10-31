package seedu.revision.model.quiz;

import static seedu.revision.model.Model.PREDICATE_SHOW_ALL_ANSWERABLE;

import java.util.function.Predicate;

import seedu.revision.model.answerable.Answerable;


public class NormalMode extends Mode {
    public static final int NORMAL_MODE_TIME = 10;
    public static final Predicate<Answerable> NORMAL_MODE_PREDICATE =  PREDICATE_SHOW_ALL_ANSWERABLE;

    /**
     * Constructs a {@code NormalMode}.
     */
    public NormalMode() {
        super("normal");
        this.time = NORMAL_MODE_TIME;
        this.combinedPredicate = NORMAL_MODE_PREDICATE;
    }

    @Override
    public Mode withCombinedPredicate(Predicate<Answerable> combinedPredicate) {
        return new NormalMode();
    }

    @Override
    public Mode withTime(int time) {
        return new NormalMode();
    }

    @Override
    public Mode build() {
        return new NormalMode();
    }
}

