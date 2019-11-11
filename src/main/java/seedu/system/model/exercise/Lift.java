package seedu.system.model.exercise;

/**
 * Represents an {@link Exercise}'s type of lift.
 * Guarantees: immutable
 */
public enum Lift {
    BENCH("bench"),
    DEADLIFT("deadlift"),
    SQUAT("squat");

    public static final String MESSAGE_CONSTRAINTS =
        "Lift name should be provided as either "
            + BENCH.toString() + " or "
            + DEADLIFT.toString() + " or "
            + SQUAT.toString() + ".";

    private String name;

    private Lift(String name) {
        this.name = name;
    }

    /**
     * Checks {@code gender} corresponds to a  format.
     */
    public static boolean isValidLift(String liftName) {
        return liftName.toLowerCase().equals(BENCH.toString())
            || liftName.toLowerCase().equals(DEADLIFT.toString())
            || liftName.toLowerCase().equals(SQUAT.toString());
    }

    public static Lift getLiftCorrespondingToName(String name) {
        if (name.equals((BENCH.toString()))) {
            return BENCH;
        } else if (name.equals((DEADLIFT.toString()))) {
            return DEADLIFT;
        } else if (name.equals((SQUAT.toString()))) {
            return SQUAT;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
