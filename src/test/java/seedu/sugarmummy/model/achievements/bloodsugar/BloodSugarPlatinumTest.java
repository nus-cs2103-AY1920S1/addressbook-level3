package seedu.sugarmummy.model.achievements.bloodsugar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;

import org.junit.jupiter.api.Test;

class BloodSugarPlatinumTest {

    @Test
    public void getMaximum_test() {
        assertEquals(7.8, (new BloodSugarPlatinum().getMaximum()));
    }

    @Test
    public void getMinimum_test() {
        assertEquals(4.0, (new BloodSugarPlatinum().getMinimum()));
    }

    @Test
    public void getDurationValue_test() {
        assertEquals(90, (new BloodSugarPlatinum().getDurationValue()));
    }

    @Test
    public void getDurationUnits_test() {
        assertEquals(DAY, (new BloodSugarPlatinum().getDurationUnits()));
    }

    @Test
    public void copy_test() {
        BloodSugarPlatinum bloodSugarPlatinum = new BloodSugarPlatinum();
        assertNotSame(bloodSugarPlatinum, bloodSugarPlatinum.copy());
    }
}
