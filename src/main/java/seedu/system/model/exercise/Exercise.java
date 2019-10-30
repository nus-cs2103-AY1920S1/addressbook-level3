package seedu.system.model.exercise;


/**
 * Represents an Exercise category in a {@link seedu.system.model.competition.Competition}
 */
public class Exercise {
    private static final int MAXIMUM_ATTEMPTS = 3;

    private final Lift lift;
    private final int noOfAttempts = MAXIMUM_ATTEMPTS;

    public Exercise(Lift lift) {
        this.lift = lift;
    }

    public Lift getLift() {
        return lift;
    }

    public int getNoOfAttempts() {
        return noOfAttempts;
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
        return otherExercise.getLift().equals(getLift()) && otherExercise.getNoOfAttempts() == getNoOfAttempts();
    }
}
