package seedu.sugarmummy.model.achievements.bloodsugar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;

import org.junit.jupiter.api.Test;

class BloodSugarSilverTest {

    @Test
    public void getMaximum_test() {
        assertEquals(7.8, (new BloodSugarSilver().getMaximum()));
    }

    @Test
    public void getMinimum_test() {
        assertEquals(4.0, (new BloodSugarSilver().getMinimum()));
    }

    @Test
    public void getDurationValue_test() {
        assertEquals(14, (new BloodSugarSilver().getDurationValue()));
    }

    @Test
    public void getDurationUnits_test() {
        assertEquals(DAY, (new BloodSugarSilver().getDurationUnits()));
    }

    @Test
    public void copy_test() {
        BloodSugarSilver bloodSugarSilver = new BloodSugarSilver();
        assertNotSame(bloodSugarSilver, bloodSugarSilver.copy());
    }
}
