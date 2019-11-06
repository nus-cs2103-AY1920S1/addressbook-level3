package thrift.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Preference file Location : preferences.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);

        // Same object -> true
        assertTrue(defaultConfig.equals(defaultConfig));

        // Compare to null -> false
        assertFalse(defaultConfig.equals(null));

        // Compare to different type -> false
        assertFalse(defaultConfig.equals("Config"));

        // Same values -> true
        Config defaultConfigCopy = new Config();
        assertTrue(defaultConfig.equals(defaultConfigCopy));
    }

}
