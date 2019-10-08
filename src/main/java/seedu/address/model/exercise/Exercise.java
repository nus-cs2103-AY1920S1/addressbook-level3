package seedu.address.model.exercise;

/**
 * Represents an Exercise category in a {@link seedu.address.model.competition.Competition}
 */
public class Exercise {
    private final Type type;

    public Exercise(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
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
        return otherExercise.getType().equals(getType());
    }
}
