package seedu.address.model.attempt;

import seedu.address.model.attempt.exceptions.AttemptHasBeenAttemptedException;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;

/**
 * Represents a {@link seedu.address.model.person.Person}'s attempt in an {@link Exercise}.
 * Guarantees: immutable;
 */
public class Attempt {
    private final Participation participation;
    private final int attemptNo;
    private final int weight;

    private boolean hasAttempted;
    private boolean isSuccess;

    public Attempt(Participation participation, int attemptNo, int weight) {
        this.participation = participation;
        this.attemptNo = attemptNo;
        this.weight = weight;
        this.hasAttempted = false;
    }

    /**
     * This method records the success of an athlete's attempt after his lift.
     * @param isSuccess true if the athlete succeeds his lift, false otherwise
     */
    public void lifted(boolean isSuccess) throws AttemptHasBeenAttemptedException {
        if (hasAttempted) {
            throw new AttemptHasBeenAttemptedException();
        }

        this.isSuccess = isSuccess;
        this.hasAttempted = true;
    }

    public Person getPerson() {
        return participation.getPerson();
    }

    public int getAttemptWeight() {
        return weight;
    }
}
