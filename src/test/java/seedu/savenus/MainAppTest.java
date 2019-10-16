package seedu.savenus;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.savenus.commons.core.Version;

public class MainAppTest {

    private MainApp mainApp = new MainApp();

    @Test
    public void correctVersion() {
        assertTrue(new Version(0, 6, 0, true).equals(MainApp.VERSION));
    }

    @Test
    public void testInit_successfulInit() {
        assertThrows(NullPointerException.class, () -> mainApp.init());
    }

    @Test
    public void testStop() {
        assertThrows(NullPointerException.class, () -> mainApp.stop());
    }
}
