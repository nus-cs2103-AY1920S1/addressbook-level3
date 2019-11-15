package dukecooks.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {

    @Test
    public void test_getWindowWidth() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        assertEquals(guiSettings.getWindowWidth(), 1);
    }

    @Test
    public void test_getWindowHeight() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        assertEquals(guiSettings.getWindowHeight(), 2);
    }

    @Test
    public void test_getWindowCoordinates() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        assertEquals(guiSettings.getWindowCoordinates(), new Point(3, 4));
    }

    @Test
    public void test_hashCode() {
        GuiSettings firstGuiSettings = new GuiSettings();
        GuiSettings secondGuiSettings = new GuiSettings();

        assertEquals(firstGuiSettings.hashCode(), secondGuiSettings.hashCode());
    }

    @Test
    public void test_equals() {
        GuiSettings firstGuiSettings = new GuiSettings();
        GuiSettings secondGuiSettings = new GuiSettings();
        GuiSettings thirdGuiSettings = new GuiSettings(1, 2, 3, 4);

        // same object
        assertTrue(firstGuiSettings.equals(firstGuiSettings));

        // different object, same fields
        assertTrue(firstGuiSettings.equals(secondGuiSettings));

        // different objects and fields
        assertFalse(firstGuiSettings.equals(null));
        assertFalse(firstGuiSettings.equals(thirdGuiSettings));

    }
}
