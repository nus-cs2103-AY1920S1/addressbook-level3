package seedu.jarvis.model.cca.ccaprogress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.jarvis.model.cca.exceptions.CcaProgressAtMinException;

public class CcaCurrentProgressTest {

    private CcaCurrentProgress ccaCurrentProgress;

    @BeforeEach
    public void setUp() {
        ccaCurrentProgress = new CcaCurrentProgress();
    }

    @Test
    public void setCcaCurrentProgress_validInput_setCorrectly() {
        ccaCurrentProgress.setCurrentProgress(1);
        assertEquals(ccaCurrentProgress.getCurrentProgress(), 1);
    }

    @Test
    public void setCcaCurrentProgress_invalidInput_setCorrectly() {
        assertThrows(IllegalArgumentException.class, () -> ccaCurrentProgress.setCurrentProgress(-1));
    }

    @Test
    public void increaseCcaCurrentProgress_validInput_increasedCorrectly() {
        ccaCurrentProgress.setCurrentProgress(1);
        ccaCurrentProgress.increaseProgress();
        assertEquals(ccaCurrentProgress.getCurrentProgress(), 2);
    }

    @Test
    public void decreaseCcaCurrentProgress_validInput_decreasedCorrectly() {
        ccaCurrentProgress.setCurrentProgress(2);
        ccaCurrentProgress.decreaseProgress();
        assertEquals(ccaCurrentProgress.getCurrentProgress(), 1);
    }

    @Test
    public void decreaseCurrentProgress_invalidInput_throwsCcaProgressAtZeroException() {
        ccaCurrentProgress.setCurrentProgress(0);
        assertThrows(CcaProgressAtMinException.class, () -> ccaCurrentProgress.decreaseProgress());
    }
}
