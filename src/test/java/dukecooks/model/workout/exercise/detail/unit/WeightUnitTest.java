package dukecooks.model.workout.exercise.detail.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import dukecooks.model.workout.exercise.details.unit.WeightUnit;

public class WeightUnitTest {

    @Test
    public void toJsonTest() {
        String expected = "GRAM";
        String error = "g";
        assertEquals(WeightUnit.GRAM.toJson(), expected);
        assertNotEquals(WeightUnit.GRAM.toJson(), error);
    }

    @Test
    public void toStringTest() {
        String expected = "g";
        String error = "GRAM";
        assertEquals(WeightUnit.GRAM.toString(), expected);
        assertNotEquals(WeightUnit.GRAM.toString(), error);
    }
}
