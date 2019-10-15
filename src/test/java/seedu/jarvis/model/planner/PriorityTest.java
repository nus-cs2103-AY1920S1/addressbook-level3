package seedu.jarvis.model.planner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.model.planner.Priority.isValidPriority;

import org.junit.jupiter.api.Test;

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
