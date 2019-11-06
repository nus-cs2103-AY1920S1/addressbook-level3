package seedu.sugarmummy.model.achievements.bmi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;

import org.junit.jupiter.api.Test;

class BmiPlatinumTest {

    @Test
    public void getMaximum_test() {
        assertEquals(25, (new BmiPlatinum().getMaximum()));
    }

    @Test
    public void getMinimum_test() {
        assertEquals(18.5, (new BmiPlatinum().getMinimum()));
    }

    @Test
    public void getDurationValue_test() {
        assertEquals(90, (new BmiPlatinum().getDurationValue()));
    }

    @Test
    public void getDurationUnits_test() {
        assertEquals(DAY, (new BmiPlatinum().getDurationUnits()));
    }

    @Test
    public void copy_test() {
        BmiPlatinum bmiPlatinum = new BmiPlatinum();
        assertNotSame(bmiPlatinum, bmiPlatinum.copy());
    }
}
