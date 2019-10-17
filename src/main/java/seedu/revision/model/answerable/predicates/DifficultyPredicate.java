package seedu.revision.model.answerable.predicates;

import java.util.function.Predicate;

import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;


public class DifficultyPredicate implements Predicate<Answerable> {
    private final Difficulty level;

    public DifficultyPredicate(Difficulty level) {
        this.level = level;
    }

    @Override
    public boolean test(Answerable answerable) {
        return level.equals(answerable.getDifficulty());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DifficultyPredicate // instanceof handles nulls
                && level.equals(((DifficultyPredicate) other).level)); // state check
    }

}

