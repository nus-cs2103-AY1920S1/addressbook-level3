package seedu.exercise.model.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.exercise.model.property.custom.ParameterType;

public class ParameterTypeTest {

    @Test
    public void isValidParameterType() {
        // null ParameterType -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> ParameterType.isValidParameterType(null));

        // Invalid parameter types -> return false
        assertFalse(ParameterType.isValidParameterType(" ")); // Empty string
        assertFalse(ParameterType.isValidParameterType("Datessas")); // Invalid string with only letters
        assertFalse(ParameterType.isValidParameterType("324")); // String contains numbers
        assertFalse(ParameterType.isValidParameterType("Number 3")); // Mix of valid parameter type with number
        assertFalse(ParameterType.isValidParameterType("Text Me?")); // Contains punctuation

        // Valid parameter types -> return true
        assertTrue(ParameterType.isValidParameterType("Number"));
        assertTrue(ParameterType.isValidParameterType("Date"));
        assertTrue(ParameterType.isValidParameterType("Text"));
    }

    @Test
    public void isValidText() {
        // Invalid text -> return false
        assertFalse(ParameterType.isValidText("3.14159")); // Contains number
        assertFalse(ParameterType.isValidText("Hi!")); // Contains punctuation
        assertFalse(ParameterType.isValidText("This is 30/09/2019")); // Mix of number and punctuation

        // Valid text -> return true
        assertTrue(ParameterType.isValidText("This is a very long sentence which should be accepted"));
        assertTrue(ParameterType.isValidText("a"));
    }

    @Test
    public void isValidNumber() {
        // Invalid number -> return false
        assertFalse(ParameterType.isValidNumber("-3.4")); // No negative numbers allowed
        assertFalse(ParameterType.isValidNumber("30/09/2019")); // Contains punctuation
        assertFalse(ParameterType.isValidNumber("Only alphabets")); // Only letters
        assertFalse(ParameterType.isValidNumber("3.141592654")); // Floating point number not allowed

        // Valid number -> return true
        assertTrue(ParameterType.isValidNumber("3")); // Integers allowed
        assertTrue(ParameterType.isValidNumber("0")); // 0 allowed
        assertTrue(ParameterType.isValidNumber("929481290194910942091")); // Large number
    }

    @Test
    public void toString_test() {
        assertEquals(ParameterType.TEXT.getParameterName(), "Text");
        assertEquals(ParameterType.NUMBER.getParameterName(), "Number");
        assertEquals(ParameterType.DATE.getParameterName(), "Date");
    }
}
