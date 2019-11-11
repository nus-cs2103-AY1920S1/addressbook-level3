package seedu.sugarmummy.model.achievements.bloodsugar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;

import org.junit.jupiter.api.Test;

class BloodSugarGoldTest {

    @Test
    public void getMaximum_test() {
        assertEquals(7.8, (new BloodSugarGold().getMaximum()));
    }

    @Test
    public void getMinimum_test() {
        assertEquals(4.0, (new BloodSugarGold().getMinimum()));
    }

    @Test
    public void getDurationValue_test() {
        assertEquals(30, (new BloodSugarGold().getDurationValue()));
    }

    @Test
    public void getDurationUnits_test() {
        assertEquals(DAY, (new BloodSugarGold().getDurationUnits()));
    }

    @Test
    public void copy_test() {
        BloodSugarGold bloodSugarGold = new BloodSugarGold();
        assertNotSame(bloodSugarGold, bloodSugarGold.copy());
    }
}
