package budgetbuddy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import budgetbuddy.testutil.scriptutil.TypicalScriptNames;
import budgetbuddy.testutil.scriptutil.TypicalScripts;

class ScriptLibraryManagerTest {
    /**
     * Tests adding, getting, updating and removing scripts.
     */
    @Test
    void testAddGetUpdateRemove() {
        ScriptLibraryManager m = new ScriptLibraryManager();

        assertNull(m.getScript(TypicalScriptNames.A));

        // remove script
        assertFalse(m.removeScript(TypicalScriptNames.A));

        // add script
        assertFalse(m.addScript(TypicalScripts.A));

        // get script
        assertEquals(m.getScript(TypicalScriptNames.A), TypicalScripts.A);

        // update script
        assertTrue(m.addScript(TypicalScripts.CHANGED_A));
        assertEquals(m.getScript(TypicalScriptNames.A), TypicalScripts.CHANGED_A);

        // remove script
        assertTrue(m.removeScript(TypicalScriptNames.A));
        assertFalse(m.removeScript(TypicalScriptNames.A));

        // add many scripts
        assertFalse(m.addScript(TypicalScripts.A));
        assertFalse(m.addScript(TypicalScripts.B));
        assertFalse(m.addScript(TypicalScripts.C));
        assertFalse(m.addScript(TypicalScripts.LONG_NAME));
        assertFalse(m.addScript(TypicalScripts.UPPER_LONG_NAME));

        assertEquals(m.getScriptList(), Arrays.asList(TypicalScripts.A, TypicalScripts.B, TypicalScripts.C,
                TypicalScripts.LONG_NAME, TypicalScripts.UPPER_LONG_NAME));

        // check all lookups are correct
        assertEquals(m.getScript(TypicalScriptNames.A), TypicalScripts.A);
        assertEquals(m.getScript(TypicalScriptNames.B), TypicalScripts.B);
        assertEquals(m.getScript(TypicalScriptNames.C), TypicalScripts.C);
        assertEquals(m.getScript(TypicalScriptNames.LONG_NAME), TypicalScripts.LONG_NAME);
        assertEquals(m.getScript(TypicalScriptNames.UPPER_LONG_NAME), TypicalScripts.UPPER_LONG_NAME);

        // remove a script
        assertTrue(m.removeScript(TypicalScriptNames.B));

        // check correct scripts still returned
        assertEquals(m.getScript(TypicalScriptNames.A), TypicalScripts.A);
        assertEquals(m.getScript(TypicalScriptNames.C), TypicalScripts.C);
        assertEquals(m.getScript(TypicalScriptNames.LONG_NAME), TypicalScripts.LONG_NAME);
        assertEquals(m.getScript(TypicalScriptNames.UPPER_LONG_NAME), TypicalScripts.UPPER_LONG_NAME);

        assertEquals(m.getScriptList(), Arrays.asList(TypicalScripts.A, TypicalScripts.C,
                TypicalScripts.LONG_NAME, TypicalScripts.UPPER_LONG_NAME));

        // update a script
        assertTrue(m.addScript(TypicalScripts.CHANGED_A));

        assertNotEquals(m.getScriptList(), Arrays.asList(TypicalScripts.A, TypicalScripts.C,
                TypicalScripts.LONG_NAME, TypicalScripts.UPPER_LONG_NAME));
        assertEquals(m.getScriptList(), Arrays.asList(TypicalScripts.CHANGED_A, TypicalScripts.C,
                TypicalScripts.LONG_NAME, TypicalScripts.UPPER_LONG_NAME));

        // check correct scripts still returned
        assertEquals(m.getScript(TypicalScriptNames.A), TypicalScripts.CHANGED_A);
        assertEquals(m.getScript(TypicalScriptNames.C), TypicalScripts.C);
        assertEquals(m.getScript(TypicalScriptNames.LONG_NAME), TypicalScripts.LONG_NAME);
        assertEquals(m.getScript(TypicalScriptNames.UPPER_LONG_NAME), TypicalScripts.UPPER_LONG_NAME);
    }

    @Test
    void testEquals() {
        ScriptLibraryManager m1 = prepareManager();
        ScriptLibraryManager m2 = prepareManager();

        assertEquals(m1, m2);

        // update one
        assertTrue(m1.addScript(TypicalScripts.CHANGED_A));

        // should not be equal
        assertNotEquals(m1, m2);

        // update the other
        assertTrue(m2.addScript(TypicalScripts.CHANGED_A));

        // should be equal now
        assertEquals(m1, m2);

        // remove one
        assertTrue(m1.removeScript(TypicalScriptNames.A));

        // should not be equal
        assertNotEquals(m1, m2);

        // remove the other
        assertTrue(m2.removeScript(TypicalScriptNames.A));

        // should be equal now
        assertEquals(m1, m2);

        assertNotEquals(m1, null);
        assertNotEquals(m1, "hello");
    }

    @Test
    void testConstructWithCollection() {
        ScriptLibraryManager m1 = prepareManager();
        ScriptLibraryManager m2 = new ScriptLibraryManager(m1.getScriptList());

        assertEquals(m1, m2);
    }

    @Test
    void testHashCode() {
        ScriptLibraryManager m1 = prepareManager();
        ScriptLibraryManager m2 = prepareManager();

        assertEquals(m1.hashCode(), m2.hashCode());

        // update one
        assertTrue(m1.addScript(TypicalScripts.CHANGED_A));

        // should not be equal
        assertNotEquals(m1.hashCode(), m2.hashCode());

        // update the other
        assertTrue(m2.addScript(TypicalScripts.CHANGED_A));

        // should be equal now
        assertEquals(m1.hashCode(), m2.hashCode());

        // remove one
        assertTrue(m1.removeScript(TypicalScriptNames.A));

        // should not be equal
        assertNotEquals(m1.hashCode(), m2.hashCode());

        // remove the other
        assertTrue(m2.removeScript(TypicalScriptNames.A));

        // should be equal now
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    /**
     * Prepares a {@link ScriptLibraryManager} with some scripts.
     */
    private static ScriptLibraryManager prepareManager() {
        ScriptLibraryManager m1 = new ScriptLibraryManager();
        m1.addScript(TypicalScripts.A);
        m1.addScript(TypicalScripts.B);
        m1.addScript(TypicalScripts.C);
        m1.addScript(TypicalScripts.LONG_NAME);
        m1.addScript(TypicalScripts.UPPER_LONG_NAME);
        return m1;
    }
}
