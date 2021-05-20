package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

import seedu.address.model.FinSec;

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
        assertTrue(defaultConfig.equals(defaultConfig));
        FinSec finSec = new FinSec();
        assertFalse(defaultConfig.equals(finSec));
    }

    //@@author{lawncegoh}
    @Test
    public void execute_getMethods_success() {
        Config defaultConfig = new Config();
        defaultConfig.setLogLevel(Level.INFO);
        assertEquals(defaultConfig.getLogLevel(), Level.INFO);

        defaultConfig.setUserPrefsFilePath(Paths.get("preferences.json"));
        assertEquals(defaultConfig.getUserPrefsFilePath(), Paths.get("preferences.json"));
    }

    //@@author{lawncegoh}
    @Test
    public void execute_getHashCode_success() {
        Config defaultConfig = new Config();
        assertEquals(defaultConfig.hashCode(), Objects.hash(defaultConfig.getLogLevel(),
                defaultConfig.getUserPrefsFilePath()));
    }

}
