package seedu.sugarmummy.model.achievements.bloodsugar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;

import org.junit.jupiter.api.Test;

class BloodSugarBronzeTest {

    @Test
    public void getMaximum_test() {
        assertEquals(7.8, (new BloodSugarBronze().getMaximum()));
    }

    @Test
    public void getMinimum_test() {
        assertEquals(4.0, (new BloodSugarBronze().getMinimum()));
    }

    @Test
    public void getDurationValue_test() {
        assertEquals(3, (new BloodSugarBronze().getDurationValue()));
    }

    @Test
    public void getDurationUnits_test() {
        assertEquals(DAY, (new BloodSugarBronze().getDurationUnits()));
    }

    @Test
    public void copy_test() {
        BloodSugarBronze bloodSugarBronze = new BloodSugarBronze();
        assertNotSame(bloodSugarBronze, bloodSugarBronze.copy());
    }
}
