package seedu.sugarmummy.model.achievements.bmi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;

import org.junit.jupiter.api.Test;

class BmiSilverTest {

    @Test
    public void getMaximum_test() {
        assertEquals(25, (new BmiSilver().getMaximum()));
    }

    @Test
    public void getMinimum_test() {
        assertEquals(18.5, (new BmiSilver().getMinimum()));
    }

    @Test
    public void getDurationValue_test() {
        assertEquals(14, (new BmiSilver().getDurationValue()));
    }

    @Test
    public void getDurationUnits_test() {
        assertEquals(DAY, (new BmiSilver().getDurationUnits()));
    }

    @Test
    public void copy_test() {
        BmiSilver bmiSilver = new BmiSilver();
        assertNotSame(bmiSilver, bmiSilver.copy());
    }
}
