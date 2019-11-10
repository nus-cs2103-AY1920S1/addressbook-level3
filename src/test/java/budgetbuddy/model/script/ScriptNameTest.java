package budgetbuddy.model.script;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ScriptNameTest {

    @Test
    void isValidName() {
        // empty -> false
        assertFalse(ScriptName.isValidName(""));

        // alphabetical only -> true
        assertTrue(ScriptName.isValidName("abcde"));

        // numeric only -> true
        assertTrue(ScriptName.isValidName("12345"));

        // dashes only -> true
        assertTrue(ScriptName.isValidName("---"));

        // underscores only -> true
        assertTrue(ScriptName.isValidName("___"));

        // all -> true
        assertTrue(ScriptName.isValidName("abc12de-_---"));

        // space -> false
        assertFalse(ScriptName.isValidName("abc12de-_- --"));

        // other things -> false
        assertFalse(ScriptName.isValidName("hello! i am an amazing script name!"));
    }

    @Test
    void testToString() {
        assertEquals(new ScriptName("abcde").toString(), "abcde");
    }

    @Test
    void testEquals() {
        assertEquals(new ScriptName("abcde"), new ScriptName("abcde"));
        assertNotEquals(new ScriptName("abcdef"), new ScriptName("abcde"));
    }

    @Test
    void testHashCode() {
        assertEquals(new ScriptName("abcde").hashCode(), new ScriptName("abcde").hashCode());
        assertNotEquals(new ScriptName("abcdef").hashCode(), new ScriptName("abcde").hashCode());
    }
}
