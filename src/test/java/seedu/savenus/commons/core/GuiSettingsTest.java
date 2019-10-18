package seedu.savenus.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GuiSettingsTest {
    private GuiSettings guiSettings;

    @BeforeEach
    public void setUp() {
        guiSettings = new GuiSettings();
    }

    @Test
    public void coordinates_test() {
        assertEquals(guiSettings.getWindowHeight(), 800);
        assertEquals(guiSettings.getWindowWidth(), 1100);
    }

    @Test
    public void equals() {
        assertEquals(guiSettings, new GuiSettings());
        assertNotEquals(guiSettings, new Object());
    }
}
