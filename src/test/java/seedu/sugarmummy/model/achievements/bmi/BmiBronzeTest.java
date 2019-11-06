package seedu.sugarmummy.model.achievements.bmi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.sugarmummy.model.achievements.DurationUnit.DAY;

import org.junit.jupiter.api.Test;

class BmiBronzeTest {

    @Test
    public void getMaximum_test() {
        assertEquals(25, (new BmiBronze().getMaximum()));
    }

    @Test
    public void getMinimum_test() {
        assertEquals(18.5, (new BmiBronze().getMinimum()));
    }

    @Test
    public void getDurationValue_test() {
        assertEquals(3, (new BmiBronze().getDurationValue()));
    }

    @Test
    public void getDurationUnits_test() {
        assertEquals(DAY, (new BmiBronze().getDurationUnits()));
    }

    @Test
    public void copy_test() {
        BmiBronze bmiBronze = new BmiBronze();
        assertNotSame(bmiBronze, bmiBronze.copy());
    }
}
