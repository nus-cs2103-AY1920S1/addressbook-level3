package seedu.ezwatchlist.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Objects;

import org.junit.jupiter.api.Test;

class GuiSettingsTest {
    private GuiSettings guiSettings = new GuiSettings();

    @Test
    void getWindowWidth() {
        assertEquals(guiSettings.getWindowWidth(), 740.0);
    }

    @Test
    void getWindowHeight() {
        assertEquals(guiSettings.getWindowHeight(), 600.0);
    }

    @Test
    void getWindowCoordinates() {
        assertEquals(guiSettings.getWindowCoordinates(), null);
    }

    @Test
    void testEquals() {
        assertFalse(guiSettings.equals(1));
    }

    @Test
    void testHashCode() {
        assertEquals(guiSettings.hashCode(), Objects.hash(guiSettings.getWindowWidth(),
                guiSettings.getWindowHeight(), guiSettings.getWindowCoordinates()));
    }

    @Test
    void testToString() {
    }
}
