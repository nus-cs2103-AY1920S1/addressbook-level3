package thrift.commons.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {

    @Test
    public void equals() {
        GuiSettings guiSettings = new GuiSettings();

        // Same object -> true
        assertTrue(guiSettings.equals(guiSettings));

        // Different object type -> false
        assertFalse(guiSettings.equals("Gooey"));

        // Compare with null -> false
        assertFalse(guiSettings.equals(null));

        // Same values -> true
        GuiSettings guiSettings2 = new GuiSettings();
        assertTrue(guiSettings.equals(guiSettings2));
    }
}
