package budgetbuddy.model.script;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import budgetbuddy.model.attributes.Description;

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
        ScriptName name = new ScriptName("name");
        Description desc = new Description("desc");
        String code = "code";
        Script s = new Script(name, desc, code);

        // same content -> true
        assertEquals(new Script(new ScriptName("name"), new Description("desc"), "code"), s);

        // different name -> false
        assertNotEquals(new Script(new ScriptName("differentname"), desc, code), s);

        // different desc -> false
        assertNotEquals(new Script(name, new Description("different desc"), code), s);

        // different code -> false
        assertNotEquals(new Script(name, desc, "different code"), s);
    }

    @Test
    void testHashCode() {
        ScriptName name = new ScriptName("name");
        Description desc = new Description("desc");
        String code = "code";
        Script s = new Script(name, desc, code);

        // same content -> same hashCode
        assertEquals(new Script(new ScriptName("name"), new Description("desc"), "code").hashCode(),
                s.hashCode());

        // different name -> different hashCode
        assertNotEquals(new Script(new ScriptName("differentname"), desc, code).hashCode(), s.hashCode());

        // different desc -> different hashCode
        assertNotEquals(new Script(name, new Description("different desc"), code).hashCode(), s.hashCode());

        // different code -> different hashCode
        assertNotEquals(new Script(name, desc, "different code").hashCode(), s.hashCode());
    }
}
