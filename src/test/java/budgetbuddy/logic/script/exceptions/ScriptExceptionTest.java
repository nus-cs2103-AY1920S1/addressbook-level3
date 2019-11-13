package budgetbuddy.logic.script.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ScriptExceptionTest {
    @Test
    void test() {
        ScriptException se = new ScriptException("message");
        assertEquals("message", se.getMessage());

        Exception cause = new Exception("cause");
        ScriptException se2 = new ScriptException("message 2", cause);
        assertEquals("message 2", se2.getMessage());
        assertEquals(cause, se2.getCause());
    }
}
