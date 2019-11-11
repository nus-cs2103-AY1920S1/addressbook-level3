package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {

    private static final double CUSTOM_WIDTH = 10.0;
    private static final double CUSTOM_HEIGHT = 20.0;
    private static final int CUSTOM_X = 1;
    private static final int CUSTOM_Y = 2;
    private static final boolean DARK_THEME_ON = true;

    @Test
    public void constructor_defaultValues() {
        GuiSettings guiSettings = new GuiSettings();
        assertThrows(AssertionError.class, () -> guiSettings.getWindowHeight());
        assertThrows(AssertionError.class, () -> guiSettings.getWindowWidth());
        assertFalse(guiSettings.isDarkTheme());
        assertTrue(guiSettings.isDefault());
    }

    @Test
    public void constructor_overloaded() {
        GuiSettings guiSettings = new GuiSettings(CUSTOM_WIDTH, CUSTOM_HEIGHT, CUSTOM_X, CUSTOM_Y, DARK_THEME_ON);

        assertEquals(guiSettings.getWindowWidth(), CUSTOM_WIDTH);
        assertEquals(guiSettings.getWindowHeight(), CUSTOM_HEIGHT);
        assertEquals(guiSettings.getWindowCoordinates().getX(), CUSTOM_X);
        assertEquals(guiSettings.getWindowCoordinates().getY(), CUSTOM_Y);
        assertTrue(guiSettings.isDarkTheme());
        assertFalse(guiSettings.isDefault());
    }

    @Test
    public void equals() {
        GuiSettings guiSettings1 = new GuiSettings(CUSTOM_WIDTH, CUSTOM_HEIGHT, CUSTOM_X, CUSTOM_Y, DARK_THEME_ON);
        GuiSettings guiSettings2 = new GuiSettings(CUSTOM_WIDTH, CUSTOM_HEIGHT, CUSTOM_X, CUSTOM_Y, DARK_THEME_ON);
        GuiSettings guiSettings3 = new GuiSettings();

        assertTrue(guiSettings1.equals(guiSettings2));
        assertFalse(guiSettings1.equals(guiSettings3));
    }

    @Test
    public void hashCode_sameGuiSettingsSameHashCode() {
        GuiSettings guiSettings1 = new GuiSettings(CUSTOM_WIDTH, CUSTOM_HEIGHT, CUSTOM_X, CUSTOM_Y, DARK_THEME_ON);
        GuiSettings guiSettings2 = new GuiSettings(CUSTOM_WIDTH, CUSTOM_HEIGHT, CUSTOM_X, CUSTOM_Y, DARK_THEME_ON);
        GuiSettings guiSettings3 = new GuiSettings();

        assertEquals(guiSettings1.hashCode(), guiSettings2.hashCode());
        assertNotEquals(guiSettings1.hashCode(), guiSettings3.hashCode());
    }
}
