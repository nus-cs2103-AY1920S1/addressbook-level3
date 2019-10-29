package seedu.exercise.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    private static final Config defaultConfig = new Config();
    private static final Config otherConfig = new Config();


    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Preference file Location : preferences.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod() {
        assertNotNull(defaultConfig);

        //Same object
        assertTrue(defaultConfig.equals(defaultConfig));

        //null value
        assertFalse(defaultConfig.equals(null));

        //Different kind of object
        assertFalse(defaultConfig.equals(""));

        //instance variables same
        assertTrue(defaultConfig.equals(otherConfig));
    }

    @Test
    public void hashCode_differentObjectSameInstanceVariables_sameHashReturned() {
        assertEquals(defaultConfig.hashCode(), otherConfig.hashCode());
    }

}
