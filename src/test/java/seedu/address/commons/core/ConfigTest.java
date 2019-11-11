package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void setLogLevel_success() {
        Config config = new Config();
        config.setLogLevel(Level.INFO);
        assertEquals(config.getLogLevel(), Level.INFO);
        config.setLogLevel(Level.WARNING);
        assertEquals(config.getLogLevel(), Level.WARNING);
    }

    @Test
    public void setUserPrefsFilePath_success() {
        Config config = new Config();
        assertEquals(config.getUserPrefsFilePath(), Paths.get("preferences.json"));
        config.setUserPrefsFilePath(Paths.get("preferences.json"));
        assertEquals(config.getUserPrefsFilePath(), Paths.get("preferences.json"));
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);

        Config defaultConfig2 = new Config();
        assertEquals(defaultConfig, defaultConfig);
        assertEquals(defaultConfig, defaultConfig2);
    }

    @Test
    public void hashCode_sameObjectSameHasCode() {
        Config config1 = new Config();
        config1.setUserPrefsFilePath(Paths.get("preferences.json"));
        config1.setLogLevel(Level.INFO);

        Config config2 = new Config();
        config2.setUserPrefsFilePath(Paths.get("preferences.json"));
        config2.setLogLevel(Level.INFO);

        assertEquals(config1, config2);
    }


}
