package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.HealthRecords;
import seedu.address.model.ReadOnlyHealthRecords;
import seedu.address.model.ReadOnlyUserProfile;
import seedu.address.model.ReadOnlyWorkoutPlanner;
import seedu.address.model.UserProfile;
import seedu.address.model.WorkoutPlanner;
import seedu.address.model.common.Name;
import seedu.address.model.details.Distance;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.ExerciseWeight;
import seedu.address.model.details.Repetitions;
import seedu.address.model.details.Sets;
import seedu.address.model.details.unit.DistanceUnit;
import seedu.address.model.details.unit.WeightUnit;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseName;
import seedu.address.model.exercise.Intensity;
import seedu.address.model.exercise.MuscleType;
import seedu.address.model.exercise.MusclesTrained;
import seedu.address.model.medical.MedicalHistory;
import seedu.address.model.person.BloodType;
import seedu.address.model.person.DoB;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Height;
import seedu.address.model.person.Person;
import seedu.address.model.records.Record;
import seedu.address.model.records.Timestamp;
import seedu.address.model.records.Type;
import seedu.address.model.records.Value;

/**
 * Contains utility methods for populating {@code DukeCooks} with sample data.
 */
public class SampleDataUtil {

    public static Exercise[] getSampleExercises() {
        return new Exercise[]{
            new Exercise(new ExerciseName("Pushups"),
                        getMuscleTrained("Chest", "Biceps", "Back", "Shoulders"),
                        Intensity.HIGH,
                        getDetails(null, null, null, null, 30, 3)),
            new Exercise(new ExerciseName("Sprint Sets"),
                        getMuscleTrained("Cardiovascular", "Thighs", "Calves"),
                        Intensity.HIGH,
                        getDetails(null, null, (float) 400, DistanceUnit.METER, null, 8)),
            new Exercise(new ExerciseName("Planks"),
                    getMuscleTrained("Abs", "Shoulders"), Intensity.MEDIUM,
                    getDetails(null, null, null, null, 30, 3)),
            new Exercise(new ExerciseName("Bicep Curl"),
                    getMuscleTrained("Biceps"), Intensity.LOW,
                    getDetails((float) 10.0, WeightUnit.KILOGRAM, null, null, 12, 4)),
            new Exercise(new ExerciseName("Jumping Jacks"),
                    getMuscleTrained("Cardiovascular"), Intensity.MEDIUM,
                    getDetails(null, null, null, null, 100, null)),
            new Exercise(new ExerciseName("Weighted Sprints"),
                    getMuscleTrained("Cardiovascular", "Thighs", "Hamstrings", "Calves"),
                    Intensity.HIGH,
                    getDetails((float) 500, WeightUnit.GRAM, (float) 400, DistanceUnit.METER, null, 8))
        };
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
        ExerciseWeight weightDetail = weight == null ? null : new ExerciseWeight(weight, weightUnit);
        Distance distanceDetail = distance == null ? null : new Distance(distance, distanceUnit);
        Repetitions repsDetail = reps == null ? null : new Repetitions(reps);
        Sets setsDetail = sets == null ? null : new Sets(sets);

        addIfNotNull(details, weightDetail);
        addIfNotNull(details, distanceDetail);
        addIfNotNull(details, repsDetail);
        addIfNotNull(details, setsDetail);
        return details;
    }

    /**
     * Checks if {@code detail} is null and adds to {@code details}
     * if it isn't.
     *
     * @param details A set containing all the ExerciseDetails of an exercise
     * @param detail  A detail that is possibly null value
     */

    public static void addIfNotNull(Set<ExerciseDetail> details, ExerciseDetail detail) {
        if (detail != null) {
            details.add(detail);
        }
    }

    //=========== Sample Person ==================================================================================

    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"),
                new DoB("25/03/1997"),
                new Gender("male"),
                new BloodType("A+"),
                new seedu.address.model.person.Weight("70", "13/10/2019 1230"),
                new Height("180", "13/10/2019 1230"),
                getMedicalHistorySet("friends"))
        };
    }

    public static ReadOnlyWorkoutPlanner getSampleWorkoutPlanner() {
        WorkoutPlanner workoutPlanner = new WorkoutPlanner();
        for (Exercise sampleExercise : getSampleExercises()) {
            workoutPlanner.addExercise(sampleExercise);

        }
        return workoutPlanner;
    }

    public static ReadOnlyUserProfile getSampleUserProfile() {
        UserProfile sampleDc = new UserProfile();
        for (Person samplePerson : getSamplePersons()) {
            sampleDc.addPerson(samplePerson);
        }
        return sampleDc;
    }

    public static Set<MedicalHistory> getMedicalHistorySet (String...strings) {
        return Arrays.stream(strings)
           .map(MedicalHistory::new)
           .collect(Collectors.toSet());
    }

    //=========== Sample Record ==================================================================================

    public static Record[] getSampleRecords () {
        return new Record[]{
            new Record(
            new Type("bloodpressure"),
            new Value("90"),
            new Timestamp("14/10/2019 01:10"))
        };
    }

    public static ReadOnlyHealthRecords getSampleHealthRecords () {
        HealthRecords sampleDc = new HealthRecords();
        for (Record sampleRecord : getSampleRecords()) {
            sampleDc.addRecord(sampleRecord);
        }
        return sampleDc;
    }
}
