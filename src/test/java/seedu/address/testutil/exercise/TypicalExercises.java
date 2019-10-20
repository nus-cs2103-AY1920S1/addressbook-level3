package seedu.address.testutil.exercise;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_ABS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_CHEST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PUSHUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SITUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPS_SIXTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SETS_FIVE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.exercise.WorkoutPlanner;
import seedu.address.model.exercise.components.Exercise;
import seedu.address.model.exercise.components.Intensity;
import seedu.address.model.exercise.details.unit.WeightUnit;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalExercises {

    public static final Exercise ABS_ROLLOUT = new ExerciseBuilder().withName("Abs Rollout")
            .withMusclesTrained("Abs").withIntensity(Intensity.MEDIUM)
            .withDetails(null, null, null, null, 20, 4).build();
    public static final Exercise BURPEES = new ExerciseBuilder().withName("Burpees")
            .withMusclesTrained("Cardiovascular", "Arms", "Chest", "Thighs").withIntensity(Intensity.HIGH)
            .withDetails(null, null, null, null, 30, 5).build();
    public static final Exercise CURTSY_LUNGE = new ExerciseBuilder().withName("Curtsy Lunge")
            .withMusclesTrained("Thighs", "Calves", "Hamstring").withIntensity(Intensity.MEDIUM)
            .withDetails((float) 10.0, WeightUnit.POUND, null, null, 15, 4).build();
    public static final Exercise DEADLIFT = new ExerciseBuilder().withName("Deadlift")
            .withMusclesTrained("Lower Back", "Hamstring", "Upper Back").withIntensity(Intensity.MEDIUM)
            .withDetails((float) 100.0, WeightUnit.KILOGRAM, null, null, 5, 5).build();
    public static final Exercise EXPLOSIVE_PUSHUP = new ExerciseBuilder().withName("Explosive Pushup")
            .withMusclesTrained("Chest", "Triceps", "Cardiovascular").withIntensity(Intensity.HIGH)
            .build();
    public static final Exercise FLYE = new ExerciseBuilder().withName("Dumbbell Flye")
            .withMusclesTrained("Chest", "Shoulders").withIntensity(Intensity.MEDIUM)
            .build();
    public static final Exercise GOBLET_SQUATS = new ExerciseBuilder().withName("Goblet Squats")
            .withMusclesTrained("Thighs", "Calves", "Hamstrings").withIntensity(Intensity.LOW)
            .build();

    // Manually added
    public static final Exercise HOON = new ExerciseBuilder().withName("Hoon Meier")
            .build();
    public static final Exercise IDA = new ExerciseBuilder().withName("Ida Mueller")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Exercise PUSHUP = new ExerciseBuilder().withName(VALID_NAME_PUSHUP)
            .withMusclesTrained(VALID_MUSCLE_CHEST)
            .withIntensity(Intensity.HIGH)
            .withDetails(null, null, null, null,
                    VALID_REPS_SIXTY, null).build();
    public static final Exercise SITUP = new ExerciseBuilder().withName(VALID_NAME_SITUP)
            .withMusclesTrained(VALID_MUSCLE_ABS)
            .withIntensity(Intensity.MEDIUM)
            .withDetails(null, null, null, null,
                    null, VALID_SETS_FIVE).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalExercises() {} // prevents instantiation

    /**
     * Returns an {@code DukeCooks} with all the typical persons.
     */
    public static WorkoutPlanner getTypicalWorkoutPlanner() {
        WorkoutPlanner ab = new WorkoutPlanner();
        for (Exercise exercise : getTypicalExercises()) {
            ab.addExercise(exercise);
        }
        return ab;
    }

    public static List<Exercise> getTypicalExercises() {
        return new ArrayList<>(Arrays.asList(ABS_ROLLOUT, BURPEES, CURTSY_LUNGE, DEADLIFT,
                EXPLOSIVE_PUSHUP, FLYE, GOBLET_SQUATS));
    }
}
