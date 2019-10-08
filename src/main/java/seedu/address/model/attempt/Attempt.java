package seedu.address.model.attempt;

import seedu.address.model.exercise.Exercise;
import seedu.address.model.participation.Participation;

/**
 * Represents a {@link seedu.address.model.person.Person}'s attempt in an {@link Exercise}.
 * Guarantees: immutable;
 */
public class Attempt {
    private final Exercise exercise;
    private final Participation participation;

    public Attempt(Exercise exercise, Participation participation) {
        this.exercise = exercise;
        this.participation = participation;
    }
}
