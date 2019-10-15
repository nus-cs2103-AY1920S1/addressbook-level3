package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.model.planner.Frequency.isValidFrequency;

import org.junit.jupiter.api.Test;

class FrequencyTest {

    @Test
    void isValidFrequency_validInput_true() {
        assertTrue(isValidFrequency("weekly"));
    }

    @Test
    void isValidFrequency_invalidInput_false() {
        assertFalse(isValidFrequency("fortnightly"));
    }
}
