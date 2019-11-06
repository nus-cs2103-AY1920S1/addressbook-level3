package seedu.sugarmummy.model.achievements.bmi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;

import org.junit.jupiter.api.Test;

class BmiDiamondTest {

    @Test
    public void getMaximum_test() {
        assertEquals(25, (new BmiDiamond().getMaximum()));
    }

    @Test
    public void getMinimum_test() {
        assertEquals(18.5, (new BmiDiamond().getMinimum()));
    }

    @Test
    public void getDurationValue_test() {
        assertEquals(180, (new BmiDiamond().getDurationValue()));
    }

    @Test
    public void getDurationUnits_test() {
        assertEquals(DAY, (new BmiDiamond().getDurationUnits()));
    }

    @Test
    public void copy_test() {
        BmiDiamond bmiDiamond = new BmiDiamond();
        assertNotSame(bmiDiamond, bmiDiamond.copy());
    }
}
