package seedu.address.model.exercise;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.UniqueElement;
import seedu.address.model.attempt.Attempt;
import seedu.address.model.attempt.exceptions.MaximumAttemptsReachedException;
import seedu.address.model.participation.Participation;

/**
 * Represents an Exercise category in a {@link seedu.address.model.competition.Competition}
 */
public class Exercise extends UniqueElement {
    private static final int MAXIMUM_ATTEMPTS = 3;

    private final Lift lift;
    private ObservableList<Attempt> threeAttempts = FXCollections.observableArrayList();

    public Exercise(Lift lift) {
        this.lift = lift;
    }

    public Lift getLift() {
        return lift;
    }

    /**
     * Creates a new attempt for an athlete's lift.
     * @param athlete the participation object that references an athletes participation in the competition.
     * @param weight weight of the attempt the athlete is going for
     */
    public void newAttempt(Participation athlete, int weight) throws MaximumAttemptsReachedException {
        int newAttemptNo = threeAttempts.size() + 1;

        if (newAttemptNo <= MAXIMUM_ATTEMPTS) {
            Attempt newAttempt = new Attempt(athlete, newAttemptNo, weight);
            threeAttempts.add(newAttempt);
        } else {
            throw new MaximumAttemptsReachedException();
        }
    }

    /**
     * Retrieves the Attempt made for this lift.
     * @param attemptNo the attempt number that is to be retrieved
     * @return the Attempt object representing the attempt made for this exercise
     */
    public Attempt getAttempt(int attemptNo) {
        return threeAttempts.get(attemptNo - 1);
    }

    /**
     * Returns true if both exercises have the same identity and data fields.
     */
    public boolean isSameElement(UniqueElement otherElement) {

        if (!(otherElement instanceof Exercise)) {
            return false;
        }

        return this.equals((Exercise) otherElement);
    }

    /**
     * Returns true if both exercises have the same identity and data fields.
     * This defines a stronger notion of equality between two exercises.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Exercise)) {
            return false;
        }

        Exercise otherExercise = (Exercise) other;
        return otherExercise.getLift().equals(getLift());
    }
}
