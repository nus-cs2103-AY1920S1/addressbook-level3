package budgetbuddy.logic.script;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import budgetbuddy.logic.script.exceptions.ScriptException;
import budgetbuddy.testutil.scriptutil.ScriptUtil;

class ScriptEngineTest {
    @Test
    void testBasic() throws Exception {
        ScriptEngine se = new ScriptEngine();

        assertEquals(1, se.evaluateScript("1"));
        assertEquals(123, se.evaluateScript(ScriptUtil.makeScript("a", "a", "123")));
    }

    @Test
    void testArgv() throws Exception {
        ScriptEngine se = new ScriptEngine();

        assertEquals(123, se.evaluateScript("argv[0]", 123));
        assertEquals("abcdef", se.evaluateScript("argv[0] + argv[1]", "abc", "def"));
    }

    @Test
    void testState() throws Exception {
        ScriptEngine se = new ScriptEngine();

        se.evaluateScript("var a = 123;");
        assertEquals(123, se.evaluateScript("a"));
    }

    @Test
    void testResetEnvironment() throws Exception {
        ScriptEngine se = new ScriptEngine();

        se.evaluateScript("var a = 123;");
        assertEquals(123, se.evaluateScript("a"));
        se.resetEnvironment();
        assertThrows(ScriptException.class, () -> se.evaluateScript("a"));
    }

    @Test
    void testSetVariable() throws Exception {
        ScriptEngine se = new ScriptEngine();

        se.setVariable("a", 123);
        assertEquals(123, se.evaluateScript("a"));
    }

    @Test
    void testInitialisers() throws Exception {
        ScriptEngine se = new ScriptEngine(eng -> eng.setVariable("a", 123),
            eng -> eng.setVariable("b", 456));

        assertEquals(123, se.evaluateScript("a"));
        assertEquals(456, se.evaluateScript("b"));
    }

    @Test
    void testAddInitialisers() throws Exception {
        ScriptEngine se = new ScriptEngine();

        assertThrows(ScriptException.class, () -> se.evaluateScript("a"));
        se.addToEnvironment(eng -> eng.setVariable("a", 123));
        assertEquals(123, se.evaluateScript("a"));
        se.resetEnvironment();
        assertEquals(123, se.evaluateScript("a"));
    }

    @Test
    void testUnwrapExceptions() throws Exception {
        ScriptEngine se = new ScriptEngine();
        final Exception ex = new Exception("Test");
        se.setVariable("throwsException", (ScriptBindingInterfaces.Void) () -> {
            throw ex;
        });
        se.setVariable("takesThreeInts", (ScriptBindingInterfaces.IntIntInt) (i, j, k) -> i + j + k);

        try {
            se.evaluateScript("throwsException()");
            fail("Exception not thrown");
        } catch (final ScriptException e) {
            assertEquals(ex, e.getCause());
        }

        try {
            se.evaluateScript("takesThreeInts(1, 2)");
            fail("Exception not thrown");
        } catch (final ScriptException e) {
            assertEquals(ScriptEngine.INCORRECT_ARITY_MESSAGE, e.getCause().getMessage());
        }
    }
}
