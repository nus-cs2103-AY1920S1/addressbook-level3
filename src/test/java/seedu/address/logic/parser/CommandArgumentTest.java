package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandArgumentTest {
    private static final String PREFIX_STRING = "p/";
    private static final Prefix VALID_PREFIX = new Prefix(PREFIX_STRING);
    private static final int VALID_START_POSITION = 0;
    private static final String VALID_VALUE = "a value";
    private static final String VALID_COMMAND_ARGUMENT_STRING_REPRESENTATION = PREFIX_STRING + VALID_VALUE;
    private static final CommandArgument VALID_COMMAND_ARGUMENT = new CommandArgument(
            VALID_PREFIX, VALID_START_POSITION, VALID_VALUE);

    @Test
    void constructor_nullPrefix_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new CommandArgument(null, VALID_START_POSITION, VALID_VALUE);
        });
    }

    @Test
    void constructor_nullValue_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new CommandArgument(VALID_PREFIX, VALID_START_POSITION, null);
        });
    }

    @Test
    void constructor_blankValue_success() {
        final String blankValue = "";
        Assertions.assertDoesNotThrow(() -> {
            new CommandArgument(VALID_PREFIX, VALID_START_POSITION, blankValue);
        });
    }

    @Test
    void getValue_validInstance_success() {
        assertEquals(VALID_VALUE, VALID_COMMAND_ARGUMENT.getValue());
    }

    @Test
    void copyWithNewValue_null_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            VALID_COMMAND_ARGUMENT.copyWithNewValue(null);
        });
    }

    @Test
    void copyWithNewValue_newValue_newInstanceWithOriginalUntouched() {
        final String newValue = "here's another value";
        final CommandArgument newInstance = VALID_COMMAND_ARGUMENT.copyWithNewValue(newValue);

        assertEquals(VALID_VALUE, VALID_COMMAND_ARGUMENT.getValue());
        assertEquals(newValue, newInstance.getValue());
        assertNotEquals(VALID_COMMAND_ARGUMENT, newInstance);
    }

    @Test
    void equals_sameInstance_success() {
        assertTrue(VALID_COMMAND_ARGUMENT.equals(VALID_COMMAND_ARGUMENT));
    }

    @Test
    void equals_sameValues_success() {
        final CommandArgument otherInstance = new CommandArgument(VALID_PREFIX, VALID_START_POSITION, VALID_VALUE);
        assertTrue(VALID_COMMAND_ARGUMENT.equals(otherInstance));
    }

    @Test
    void equals_sameValuesDifferentPrefixReference_success() {
        final Prefix prefix = new Prefix(PREFIX_STRING);
        final CommandArgument otherInstance = new CommandArgument(prefix, VALID_START_POSITION, VALID_VALUE);
        assertTrue(VALID_COMMAND_ARGUMENT.equals(otherInstance));
    }

    @Test
    void equals_sameValuesDifferentValueReference_success() {
        final String value = String.copyValueOf(VALID_VALUE.toCharArray());
        final CommandArgument otherInstance = new CommandArgument(VALID_PREFIX, VALID_START_POSITION, value);
        assertTrue(VALID_COMMAND_ARGUMENT.equals(otherInstance));
    }

    @Test
    void equals_nonCommandArgument_fail() {
        final Object otherObject = new Object();
        assertFalse(VALID_COMMAND_ARGUMENT.equals(otherObject));
    }

    @Test
    void equals_differentPrefix_fail() {
        final Prefix prefix = new Prefix("not-" + PREFIX_STRING);
        final CommandArgument otherInstance = new CommandArgument(prefix, VALID_START_POSITION, VALID_VALUE);
        assertFalse(VALID_COMMAND_ARGUMENT.equals(otherInstance));
    }

    @Test
    void equals_differentStartPosition_fail() {
        final CommandArgument otherInstance = new CommandArgument(VALID_PREFIX, 10, VALID_VALUE);
        assertFalse(VALID_COMMAND_ARGUMENT.equals(otherInstance));
    }

    @Test
    void equals_differentValue_fail() {
        final String otherValue = "here's an other value";
        final CommandArgument otherInstance = new CommandArgument(VALID_PREFIX, VALID_START_POSITION, otherValue);
        assertFalse(VALID_COMMAND_ARGUMENT.equals(otherInstance));
    }

    @Test
    void testToString() {
        assertEquals(VALID_COMMAND_ARGUMENT_STRING_REPRESENTATION, VALID_COMMAND_ARGUMENT.toString());
    }
}
