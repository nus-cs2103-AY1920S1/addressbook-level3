package dukecooks.model.workout.exercise.detail.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import dukecooks.model.workout.exercise.details.unit.DistanceUnit;

public class DistanceUnitTest {

    @Test
    public void toJsonTest() {
        String expected = "METER";
        String error = "m";
        assertEquals(DistanceUnit.METER.toJson(), expected);
        assertNotEquals(DistanceUnit.METER.toJson(), error);
    }

    @Test
    public void toStringTest() {
        String expected = "m";
        String error = "METER";
        assertEquals(DistanceUnit.METER.toString(), expected);
        assertNotEquals(DistanceUnit.METER.toString(), error);
    }
}
