package seedu.revision.model.quiz;

import java.util.function.Predicate;

import seedu.revision.model.answerable.Answerable;

public class CustomMode extends Mode {

    public CustomMode() {
        super("custom");
        this.time = NormalMode.NORMAL_MODE_TIME;
        this.combinedPredicate = NormalMode.NORMAL_MODE_PREDICATE;
    }

    public CustomMode(String value, int time, Predicate<Answerable> combinedPredicate){
        super(value);
        this.time = time;
        this.combinedPredicate = combinedPredicate;
    }

    @Override
    public Mode withCombinedPredicate(Predicate<Answerable> combinedPredicate) {
        this.combinedPredicate = combinedPredicate;
        return this;
    }

    @Override
    public Mode withTime(int time) {
        this.time = time;
        return this;
    }

    @Override
    public Mode build() {
        return new CustomMode(value, time, combinedPredicate);
    }
}
