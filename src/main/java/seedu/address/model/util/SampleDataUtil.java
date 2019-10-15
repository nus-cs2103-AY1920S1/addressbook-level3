package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.DukeCooks;
import seedu.address.model.ReadOnlyDukeCooks;
import seedu.address.model.details.*;
import seedu.address.model.details.unit.DistanceUnit;
import seedu.address.model.details.unit.WeightUnit;
import seedu.address.model.exercise.*;

/**
 * Contains utility methods for populating {@code DukeCooks} with sample data.
 */
public class SampleDataUtil {
    public static Exercise[] getSamplePersons() {
        return new Exercise[] {
            new Exercise(new Name("Pushups"),
                getMuscleTrained("Chest", "Biceps", "Back", "Shoulders"),
                Intensity.HIGH,
                getDetails(null, null, null, null, 30, 3)  ),
            new Exercise(new Name("Sprint Sets"),
                getMuscleTrained("Cardiovascular", "Thighs", "Calves"),
                Intensity.HIGH,
                getDetails(null, null, (float) 400, DistanceUnit.METER, null, 8)),
            new Exercise(new Name("Planks"),
                getMuscleTrained("Abs", "Shoulders"), Intensity.MEDIUM,
                getDetails(null, null, null, null, 30, 3)),
            new Exercise(new Name("Bicep Curl"),
                getMuscleTrained("Biceps"), Intensity.LOW,
                getDetails((float) 10.0, WeightUnit.KILOGRAM, null, null, 12, 4)),
            new Exercise(new Name("Jumping Jacks"),
                getMuscleTrained("Cardiovascular"), Intensity.MEDIUM,
                getDetails(null, null, null, null, 100, null)),
            new Exercise(new Name("Weighted Sprints"),
                getMuscleTrained("Cardiovascular", "Thighs", "Hamstrings", "Calves"),
                Intensity.HIGH, getDetails((float) 500, WeightUnit.GRAM, (float) 400, DistanceUnit.METER, null, 8))
        };
    }

    public static ReadOnlyDukeCooks getSampleDukeCooks() {
        DukeCooks sampleDc = new DukeCooks();
        for (Exercise sampleExercise : getSamplePersons()) {
            sampleDc.addPerson(sampleExercise);
        }
        return sampleDc;
    }

    /**
     * Returns a MuscleTrained containing the primary muscles and list of secondary muscles given.
     */
    public static MusclesTrained getMuscleTrained(String primary, String... secondary) {
        MuscleType primaryMuscle = new MuscleType(primary);
        ArrayList<MuscleType> secondaryMuscles = new ArrayList<>();
        secondaryMuscles.addAll(Arrays.stream(secondary).map(MuscleType::new).collect(Collectors.toList()));
        return new MusclesTrained(primaryMuscle, secondaryMuscles);
    }

    public static Set<ExerciseDetail> getDetails(Float weight, WeightUnit weightUnit, Float distance,
                                                 DistanceUnit distanceUnit, Integer reps, Integer sets) {
        Set<ExerciseDetail> details = new HashSet<>();
        Weight weightDetail = weight == null ? null : new Weight(weight, weightUnit);
        Distance distanceDetail = distance == null ? null : new Distance(distance, distanceUnit);
        Repetitions repsDetail = reps == null ? null : new Repetitions(reps);
        Sets setsDetail = sets == null ? null : new Sets(sets);

        addIfNotNull(details, weightDetail);
        addIfNotNull(details, distanceDetail);
        addIfNotNull(details, repsDetail);
        addIfNotNull(details, setsDetail);
        return details;
    }

    public static void addIfNotNull(Set<ExerciseDetail> details, ExerciseDetail detail){
        if (detail != null) {
            details.add(detail);
        }
    }

}
