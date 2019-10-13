package seedu.address.model.details;

import static java.util.Objects.requireNonNull;

public class Sets<Integer> extends ExerciseDetail {

    public Sets(int sets){
        requireNonNull(sets);
        super.magnitude = sets;
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[Number of sets: ")
                .append(getMagnitude())
                .append("]");
        return builder.toString();
    }
}
