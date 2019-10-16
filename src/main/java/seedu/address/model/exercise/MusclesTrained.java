package seedu.address.model.exercise;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

/**
 * Represents all the Muscles Trained in an Exercise of Workout Planner
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class MusclesTrained {

    private final MuscleType primaryMuscle;
    private final ArrayList<MuscleType> secondaryMuscles;

    public MusclesTrained(MuscleType primaryMuscle, ArrayList<MuscleType> secondaryMuscles) {
        requireAllNonNull(primaryMuscle, secondaryMuscles);
        this.primaryMuscle = primaryMuscle;
        this.secondaryMuscles = secondaryMuscles;
    }

    public MuscleType getPrimaryMuscle() {
        return primaryMuscle;
    }

    public ArrayList<MuscleType> getSecondaryMuscles() {
        return secondaryMuscles;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" [Primary Muscle: ")
                .append(getPrimaryMuscle());
        if (!secondaryMuscles.isEmpty()) {
            builder.append(" Secondary Muscles: ");
            for (MuscleType secondaryMuscle : secondaryMuscles) {
                builder.append(secondaryMuscle)
                        .append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
