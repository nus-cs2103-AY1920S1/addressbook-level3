package seedu.sugarmummy.model.achievements.bloodsugar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;

import org.junit.jupiter.api.Test;

class BloodSugarDiamondTest {

    @Test
    public void getMaximum_test() {
        assertEquals(7.8, (new BloodSugarDiamond().getMaximum()));
    }

    @Test
    public void getMinimum_test() {
        assertEquals(4.0, (new BloodSugarDiamond().getMinimum()));
    }

    @Test
    public void getDurationValue_test() {
        assertEquals(180, (new BloodSugarDiamond().getDurationValue()));
    }

    @Test
    public void getDurationUnits_test() {
        assertEquals(DAY, (new BloodSugarDiamond().getDurationUnits()));
    }

    @Test
    public void copy_test() {
        BloodSugarDiamond bloodSugarDiamond = new BloodSugarDiamond();
        assertNotSame(bloodSugarDiamond, bloodSugarDiamond.copy());
    }
}
