package seedu.exercise.model.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.VALID_BASKETBALL_STRING_WITH_CUSTOM_PROPERTY;
import static seedu.exercise.testutil.CommonTestData.VALID_CALORIES_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_CALORIES_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_DATE;
import static seedu.exercise.testutil.CommonTestData.VALID_DATE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_FULL_NAME_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_NAME_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_NAME_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_REMARK;
import static seedu.exercise.testutil.CommonTestData.VALID_QUANTITY_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_QUANTITY_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_UNIT_AEROBICS;
import static seedu.exercise.testutil.typicalutil.TypicalCustomProperties.RATING_MAP;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.AEROBICS;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.BASKETBALL;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.WALK;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.exercise.model.property.Calories;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.property.Quantity;
import seedu.exercise.model.property.Unit;
import seedu.exercise.testutil.builder.ExerciseBuilder;

public class ExerciseTest {

    private Name name = new Name(VALID_NAME_AEROBICS);
    private Date date = new Date(VALID_DATE);
    private Quantity quantity = new Quantity(VALID_QUANTITY_AEROBICS);
    private Unit unit = new Unit(VALID_UNIT_AEROBICS);
    private Calories calories = new Calories(VALID_CALORIES_AEROBICS);
    private Set<Muscle> muscles = Set.of(new Muscle(VALID_MUSCLE_AEROBICS));
    private Exercise exerciseWithCustomProperty = new ExerciseBuilder(AEROBICS)
        .withCustomProperties(RATING_MAP).build();

    @Test
    public void constructor_withNull_throwsException() {

        assertThrows(NullPointerException.class, () -> new Exercise(null, date, calories, quantity, unit,
            muscles, RATING_MAP));
        assertThrows(NullPointerException.class, () -> new Exercise(name, null, calories, quantity, unit,
            muscles, RATING_MAP));
        assertThrows(NullPointerException.class, () -> new Exercise(name, date, null, quantity, unit,
            muscles, RATING_MAP));
        assertThrows(NullPointerException.class, () -> new Exercise(name, date, calories, null, unit,
            muscles, RATING_MAP));
        assertThrows(NullPointerException.class, () -> new Exercise(name, date, calories, quantity, null,
            muscles, RATING_MAP));
        assertThrows(NullPointerException.class, () -> new Exercise(name, date, calories, quantity, unit,
            null, RATING_MAP));
        assertThrows(NullPointerException.class, () -> new Exercise(name, date, calories, quantity, unit,
            muscles, null));
    }

    @Test
    public void getName() {
        assertEquals(name, AEROBICS.getName());
    }

    @Test
    public void getCalories() {
        assertEquals(calories, AEROBICS.getCalories());
    }

    @Test
    public void getQuantity() {
        assertEquals(quantity, AEROBICS.getQuantity());
    }

    @Test
    public void getUnit() {
        assertEquals(unit, AEROBICS.getUnit());
    }

    @Test
    public void getMuscles() {
        assertEquals(muscles, AEROBICS.getMuscles());
    }

    @Test
    public void getCustomPropertiesMap() {

        assertEquals(RATING_MAP, exerciseWithCustomProperty.getCustomPropertiesMap());
    }

    @Test
    public void getMuscles_modifySet_throwsUnsupportedOperationException() {
        Exercise exercise = new ExerciseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> exercise.getMuscles().remove(0));
    }

    @Test
    public void getCustomPropertiesMap_modifyMap_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> exerciseWithCustomProperty
            .getCustomPropertiesMap().remove(0));
    }

    @Test
    public void getObservableCustomPropertiesList() {
        ObservableList<String> expectedList = FXCollections.observableList(List.of("Rating: 1"));
        assertEquals(expectedList, exerciseWithCustomProperty.getObservableCustomPropertiesList());
    }

    @Test
    public void isSameExercise() {
        // same object -> returns true
        assertTrue(WALK.isSameResource(WALK));

        // null -> returns false
        assertFalse(WALK.isSameResource(null));

        // different date and calories -> returns false
        Exercise editedWalk = new ExerciseBuilder(WALK).withDate(VALID_DATE_BASKETBALL)
            .withCalories(VALID_CALORIES_BASKETBALL).build();
        assertFalse(WALK.isSameResource(editedWalk));

        // different name -> returns false
        editedWalk = new ExerciseBuilder(WALK).withName(VALID_NAME_BASKETBALL).build();
        assertFalse(WALK.isSameResource(editedWalk));

        // same name, same calories, different attributes -> returns true
        editedWalk = new ExerciseBuilder(WALK).withCalories(VALID_CALORIES_BASKETBALL)
            .withQuantity(VALID_QUANTITY_BASKETBALL).withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertTrue(WALK.isSameResource(editedWalk));
    }

    @Test
    public void equalsAndHashCode_variousScenarios_success() {
        // same values -> returns true/same hashcode
        Exercise walkCopy = new ExerciseBuilder(WALK).build();
        assertTrue(WALK.equals(walkCopy));
        assertEquals(WALK.hashCode(), walkCopy.hashCode());

        // same object -> returns true/same hashcode
        assertTrue(WALK.equals(WALK));
        assertEquals(WALK.hashCode(), WALK.hashCode());

        // null -> returns false
        assertFalse(WALK.equals(null));

        // different type -> returns false
        assertFalse(WALK.equals(5));

        // different Exercise -> returns false/different hashcode
        assertFalse(WALK.equals(BASKETBALL));
        assertNotEquals(WALK.hashCode(), BASKETBALL.hashCode());

        // different name -> returns false/different hashcode
        Exercise editedWalk = new ExerciseBuilder(WALK).withName(VALID_NAME_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different date -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withDate(VALID_DATE_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different calories -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withCalories(VALID_CALORIES_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different quantity -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different quantity -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withQuantity(VALID_QUANTITY_BASKETBALL).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different tags -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());

        // different custom properties -> returns false/different hashcode
        editedWalk = new ExerciseBuilder(WALK).withCustomProperties(RATING_MAP).build();
        assertFalse(WALK.equals(editedWalk));
        assertNotEquals(WALK.hashCode(), editedWalk.hashCode());
    }

    @Test
    public void toString_nonEmptyCustomProperties_returnsCorrectString() {
        Map<String, String> testCustomProperty = new TreeMap<>();
        testCustomProperty.put(VALID_FULL_NAME_REMARK, VALID_PREFIX_NAME_REMARK);
        Exercise typicalExercise = BASKETBALL;
        Exercise testWithCustomProperty = new Exercise(typicalExercise.getName(), typicalExercise.getDate(),
            typicalExercise.getCalories(), typicalExercise.getQuantity(), typicalExercise.getUnit(),
            typicalExercise.getMuscles(), testCustomProperty);

        assertEquals(VALID_BASKETBALL_STRING_WITH_CUSTOM_PROPERTY, testWithCustomProperty.toString());
    }
}
