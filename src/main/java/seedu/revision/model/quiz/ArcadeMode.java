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

    public int getLevelTwoTime() {
        assert time < 5 : "invalid time";
        return time - 5;
    }

    public int getLevelThreeTime() {
        assert time < 10 : "invalid time";
        return time - 10;
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
}

