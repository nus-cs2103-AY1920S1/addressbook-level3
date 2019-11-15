package dukecooks.model.workout.exercise.components;


/**
 * Represents Intensity of an Exercise in the workout planner.
 * Guarantees: immutable.
 */

public enum Intensity {
    HIGH,
    MEDIUM,
    LOW;

    public static final String MESSAGE_CONSTRAINTS =
            "Intensity should only include the integers 1, 2, 3 or strings low, medium and high"
                    + " and should not be blank";

    public static final String VALIDATION_REGEX = "(1|2|3|low|medium|high)";

    public static boolean isValidIntensity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns Intensity in int form.
     */
    public int toInt() {
        switch(this) {

        case HIGH:
            return 3;

        case MEDIUM:
            return 2;

        default:
            return 1;
        }
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
