package seedu.jarvis.model.planner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.jarvis.model.planner.Priority.isValidPriority;

class PriorityTest {

    @Test
    void isValidPriority_validInput_true() {
        assertTrue(isValidPriority("high"));
    }

    @Test
    void isValidPriority_invalidInput_false() {
        assertFalse(isValidPriority("highest"));
    }
}