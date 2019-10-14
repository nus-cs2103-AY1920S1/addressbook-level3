package seedu.jarvis.model.planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.jarvis.model.planner.Frequency.isValidFrequency;

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