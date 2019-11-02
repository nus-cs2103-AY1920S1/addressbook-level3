package seedu.revision.model.quiz;

import java.util.function.Predicate;

import seedu.revision.model.answerable.Answerable;

/** ArcadeMode class which has increasing difficulty each level and ends any time a user gets a question wrong. **/
public class ArcadeMode extends Mode {
    /**
     * Constructs an {@code ArcadeMode}.
     */
    public ArcadeMode() {
        super("arcade");
        this.time = 20;
        this.combinedPredicate = NormalMode.NORMAL_MODE_PREDICATE;
    }

    @Override
    public Mode withCombinedPredicate(Predicate<Answerable> combinedPredicate) {
        return this;
    }

    @Override
    public Mode withTime(int time) {
        return this;
    }

    @Override
    public Mode build() {
        return this;
    }

    public int getTime(int nextLevel) {
        switch(nextLevel) {
        case 2:
            return 15;
        case 3:
            return 10;
        default:
            return this.time;
        }
    }
}

