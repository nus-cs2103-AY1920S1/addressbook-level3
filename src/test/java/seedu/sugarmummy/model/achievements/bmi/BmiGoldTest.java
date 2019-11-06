package seedu.sugarmummy.model.achievements.bmi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;

import org.junit.jupiter.api.Test;

class BmiGoldTest {

    @Test
    public void getMaximum_test() {
        assertEquals(25, (new BmiGold().getMaximum()));
    }

    @Test
    public void getMinimum_test() {
        assertEquals(18.5, (new BmiGold().getMinimum()));
    }

    @Test
    public void getDurationValue_test() {
        assertEquals(30, (new BmiGold().getDurationValue()));
    }

    @Test
    public void getDurationUnits_test() {
        assertEquals(DAY, (new BmiGold().getDurationUnits()));
    }

    @Test
    public void copy_test() {
        BmiGold bmiGold = new BmiGold();
        assertNotSame(bmiGold, bmiGold.copy());
    }
}
