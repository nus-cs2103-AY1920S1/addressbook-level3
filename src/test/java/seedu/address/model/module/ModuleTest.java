package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS3244;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ST2334;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModule.CS3244;
import static seedu.address.testutil.TypicalModule.ST2334;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Color;
import seedu.address.model.tag.UserTag;
import seedu.address.testutil.ModuleBuilder;

/**
 * A test class for {@code Module}.
 */
public class ModuleTest {
    @Test
    public void equals() throws CloneNotSupportedException {
        // same values -> returns true
        Module cs3244Copy = new ModuleBuilder(CS3244).build();
        assertTrue(CS3244.equals(cs3244Copy));

        // same object -> returns true
        assertTrue(CS3244.equals(CS3244));

        // null -> returns false
        assertFalse(CS3244.equals(null));

        // different type -> returns false
        assertFalse(CS3244.equals(5));

        // different module -> returns true
        assertFalse(CS3244.equals(ST2334));

        // different name -> returns true
        Module editedCS3244 = new ModuleBuilder(CS3244).withName(VALID_NAME_ST2334).build();
        assertTrue(CS3244.equals(editedCS3244));
        Module skeletalCS3244 = new Module(new ModuleCode(VALID_MODULE_CODE_CS3244));
        assertNotSame(skeletalCS3244, cs3244Copy);

        Module cs3244Clone = cs3244Copy.clone();
        assertEquals(cs3244Clone, cs3244Copy);
        assertEquals(cs3244Clone.getPrereqString(), cs3244Copy.getPrereqString());
        assertEquals(cs3244Clone.getTags(), cs3244Copy.getTags());
        assertEquals(cs3244Clone.getColor(), cs3244Copy.getColor());
        assertEquals(cs3244Clone.getModuleCode(), cs3244Copy.getModuleCode());
        assertEquals(cs3244Clone.getMcCount(), cs3244Copy.getMcCount());
        assertNotSame(cs3244Copy, cs3244Clone);

        // hashCode
        assertEquals(cs3244Clone.hashCode(), CS3244.hashCode());
    }

    @Test
    public void toString_valid_success() {
        assertEquals("Machine Learning Module code: CS3244 "
                + "MCs: 4 Prereqs satisfied: false Tags: [Cool][AI][ML]", CS3244.toString());
        assertEquals("Probability and Statistics Module code: ST2334 "
                + "MCs: 4 Prereqs satisfied: false Tags: [Stats]", ST2334.toString());
    }

    @Test
    public void constructor_null_throwsException() {
        assertThrows(NullPointerException.class, () -> new Module(null));
        assertThrows(NullPointerException.class, () -> new Module(null, null, null));
        assertThrows(NullPointerException.class, () ->
            new Module(null, null, 4, null, null, null));
    }

    @Test
    public void getters_setters() {
        Module cs3244Copy = new ModuleBuilder(CS3244).build();
        assertEquals(Color.RED, cs3244Copy.getColor());
        assertEquals(4, cs3244Copy.getMcCount());
        assertEquals(new Name("Machine Learning"), cs3244Copy.getName());
        assertEquals("CS3244", cs3244Copy.getModuleCode().toString());
        assertFalse(cs3244Copy.getPrereqsSatisfied());
        // assertEquals("MA1101R and MA1521 and ST2334 and (CS2010 or CS2020 or CS2040)",
        //        CS3244.getPrereqString());
        cs3244Copy.setName(new Name(VALID_NAME_ST2334));
        cs3244Copy.setColor(Color.AQUAMARINE);
        cs3244Copy.setMcCount(5);
        cs3244Copy.setPrereqTree(ParserUtil.parsePrereqTree(""));
        cs3244Copy.addTag(new UserTag("Cool"));
        assertEquals(5, cs3244Copy.getMcCount());
        assertEquals(VALID_NAME_ST2334, cs3244Copy.getName().toString());
        assertEquals(Color.AQUAMARINE, cs3244Copy.getColor());
        assertTrue(cs3244Copy.hasTag(new UserTag("Cool")));
        cs3244Copy.deleteUserTag(new UserTag("Cool"));
        assertFalse(cs3244Copy.hasTag(new UserTag("Cool")));
    }
}
