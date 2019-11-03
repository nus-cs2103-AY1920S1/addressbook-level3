package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class PriorityTest {

    @Test
    public void getPriority_validPriority_success() throws Exception {
        assertTrue(Priority.getPriority("high") == Priority.HIGH);
        assertTrue(Priority.getPriority(" high ") == Priority.HIGH);
        assertTrue(Priority.getPriority("HIGH") == Priority.HIGH);
        assertTrue(Priority.getPriority(" medium") == Priority.MEDIUM);
        assertTrue(Priority.getPriority("MEdiUm") == Priority.MEDIUM);
        assertTrue(Priority.getPriority("low ") == Priority.LOW);
        assertTrue(Priority.getPriority("Low") == Priority.LOW);
        assertTrue(Priority.getPriority("unmarked") == Priority.UNMARKED);
        assertTrue(Priority.getPriority("unmArked") == Priority.UNMARKED);
    }

    @Test
    public void getPriority_invalidPriority_throwsParseException() {
        assertThrows(ParseException.class, Priority.INVALID_PRIORITY, () ->
            Priority.getPriority(""));
        assertThrows(ParseException.class, Priority.INVALID_PRIORITY, () ->
            Priority.getPriority("hi gh"));
        assertThrows(ParseException.class, Priority.INVALID_PRIORITY, () ->
            Priority.getPriority("l0w"));
        assertThrows(ParseException.class, Priority.INVALID_PRIORITY, () ->
            Priority.getPriority("232"));
        assertThrows(AssertionError.class, () -> Priority.getPriority(null));
    }
}
