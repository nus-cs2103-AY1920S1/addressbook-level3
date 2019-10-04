package seedu.exercise.testutil;

import static seedu.exercise.logic.commands.CommandTestUtil.VALID_CALORIES_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_CALORIES_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_DATE_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_DATE_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_MUSCLE_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_NAME_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_NAME_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_QUANTITY_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_QUANTITY_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_UNIT_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_UNIT_BASKETBALL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.exercise.Exercise;

/**
 * A utility class containing a list of {@code Exercise} objects to be used in tests.
 */
public class TypicalExercises {

    public static final Exercise WALK = new ExerciseBuilder().withName("Walking")
        .withQuantity("30").withUnit("hours").withCalories("222").withDate("26/09/2019").withMuscles("Legs").build();
    public static final Exercise SWIM = new ExerciseBuilder().withName("Swimming")
        .withQuantity("10").withUnit("laps").withCalories("333").withDate("26/09/2019")
        .withMuscles("Legs", "Core").build();
    public static final Exercise DANCE = new ExerciseBuilder().withName("Dancing")
        .withDate("26/09/2019").withCalories("456").withQuantity("2").withUnit("hours").build();
    public static final Exercise BENCH_PRESS = new ExerciseBuilder().withName("Bench Press").withDate("26/09/2019")
        .withCalories("234").withQuantity("10").withUnit("counts").withMuscles("Biceps").build();
    public static final Exercise SKIP = new ExerciseBuilder().withName("Skipping").withDate("26/09/2019")
        .withCalories("333").withQuantity("160").withUnit("skips").build();
    public static final Exercise SPRINT = new ExerciseBuilder().withName("Sprinting").withDate("26/09/2019")
        .withCalories("345").withQuantity("30").withUnit("sprints").build();
    public static final Exercise SNAP = new ExerciseBuilder().withName("Snapping").withDate("26/09/2019")
        .withCalories("332").withQuantity("800").withUnit("times").build();

    // Manually added
    public static final Exercise CLAP = new ExerciseBuilder().withName("Clapping").withDate("27/09/2019")
        .withCalories("0").withQuantity("11").withUnit("claps").build();
    public static final Exercise SLAP = new ExerciseBuilder().withName("Slapping").withDate("28/09/2019")
        .withCalories("234").withQuantity("2").withUnit("slaps").build();

    // Manually added - Exercise's details found in {@code CommandTestUtil}
    public static final Exercise AEROBICS = new ExerciseBuilder().withName(VALID_NAME_AEROBICS)
        .withDate(VALID_DATE_AEROBICS).withCalories(VALID_CALORIES_AEROBICS)
        .withQuantity(VALID_QUANTITY_AEROBICS).withUnit(VALID_UNIT_AEROBICS)
        .withMuscles(VALID_MUSCLE_BASKETBALL).build();
    public static final Exercise BASKETBALL = new ExerciseBuilder().withName(VALID_NAME_BASKETBALL)
        .withDate(VALID_DATE_BASKETBALL).withCalories(VALID_CALORIES_BASKETBALL)
        .withQuantity(VALID_QUANTITY_BASKETBALL).withUnit(VALID_UNIT_BASKETBALL)
        .withMuscles(VALID_MUSCLE_AEROBICS, VALID_MUSCLE_BASKETBALL)
        .build();


    private TypicalExercises() {
    } // prevents instantiation

    /**
     * Returns an {@code ExerciseBook} with all the typical exercises.
     */
    public static ExerciseBook getTypicalExerciseBook() {
        ExerciseBook eb = new ExerciseBook();
        for (Exercise exercise : getTypicalExercises()) {
            eb.addExercise(exercise);
        }
        return eb;
    }

    public static List<Exercise> getTypicalExercises() {
        return new ArrayList<>(Arrays.asList(WALK, SWIM, DANCE, BENCH_PRESS, SKIP, SPRINT, SNAP));
    }
}
