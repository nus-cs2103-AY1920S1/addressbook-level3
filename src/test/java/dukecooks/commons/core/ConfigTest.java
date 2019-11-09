package dukecooks.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Preference file Location : preferences.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void test_getLogLevel() {
        Config configTest = new Config();
        configTest.setLogLevel(Level.INFO);
        assertEquals(configTest.getLogLevel(), Level.INFO);
    }

    @Test
    public void test_getUserPrefsFilePath() {
        Config configTest = new Config();
        configTest.setUserPrefsFilePath(Paths.get("preferences.json"));
        assertEquals(configTest.getUserPrefsFilePath(), Paths.get("preferences.json"));
        assertNotEquals(configTest.getUserPrefsFilePath(), Paths.get("someRandomJson.json"));
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));
        assertFalse(defaultConfig.equals(null));
    }

    @Test
    public void testHashCode() {
        Config firstDefaultConfig = new Config();
        Config secondDefaultConfig = new Config();

        assertEquals(firstDefaultConfig.hashCode(), secondDefaultConfig.hashCode());
    }


}
