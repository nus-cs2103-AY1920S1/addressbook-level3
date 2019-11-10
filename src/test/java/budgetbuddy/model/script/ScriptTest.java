package budgetbuddy.model.script;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import budgetbuddy.model.attributes.Description;
import budgetbuddy.testutil.scriptutil.ScriptUtil;

class ScriptTest {

    @Test
    void getters() {
        ScriptName name = new ScriptName("name");
        Description desc = new Description("desc");
        String code = "code";
        Script s = new Script(name, desc, code);

        assertEquals(name, s.getName());
        assertEquals(desc, s.getDescription());
        assertEquals(code, s.getCode());
    }

    @Test
    void testEquals() {
        String name = "name";
        String desc = "desc";
        String code = "code";
        Script s = ScriptUtil.makeScript(name, desc, code);

        // identical -> true
        assertEquals(s, s);

        // same content -> true
        assertEquals(ScriptUtil.makeScript(name, desc, code), s);

        // different name -> false
        assertNotEquals(ScriptUtil.makeScript("differentname", desc, code), s);

        // different desc -> false
        assertNotEquals(ScriptUtil.makeScript(name, "different desc", code), s);

        // different code -> false
        assertNotEquals(ScriptUtil.makeScript(name, desc, "different code"), s);

        // null -> false
        assertNotEquals(s, null);

        // not ScriptManager -> false
        assertNotEquals(s, "Hello!");
    }

    @Test
    void testHashCode() {
        String name = "name";
        String desc = "desc";
        String code = "code";
        Script s = ScriptUtil.makeScript(name, desc, code);

        // same content -> same hashCode
        assertEquals(ScriptUtil.makeScript(name, desc, code).hashCode(),
                s.hashCode());

        // different name -> different hashCode
        assertNotEquals(ScriptUtil.makeScript("differentname", desc, code).hashCode(), s.hashCode());

        // different desc -> different hashCode
        assertNotEquals(ScriptUtil.makeScript(name, "different desc", code).hashCode(), s.hashCode());

        // different code -> different hashCode
        assertNotEquals(ScriptUtil.makeScript(name, desc, "different code").hashCode(), s.hashCode());
    }
}
