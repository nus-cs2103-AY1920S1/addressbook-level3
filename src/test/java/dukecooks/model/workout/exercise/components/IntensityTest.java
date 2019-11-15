package dukecooks.model.workout.exercise.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class IntensityTest {

    @Test
    public void toIntTest() {
        assertEquals(Intensity.HIGH.toInt(), 3);
    }

    @Test
    public void testIntensityToString() {
        String expected = "high";
        String error = "HIGH";
        assertEquals(Intensity.HIGH.toString(), expected);
        assertNotEquals(Intensity.HIGH.toString(), error);
    }
}
