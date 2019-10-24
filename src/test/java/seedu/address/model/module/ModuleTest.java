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
        Module CS3244_COPY = new ModuleBuilder(CS3244).build();
        assertTrue(CS3244.equals(CS3244_COPY));

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
        assertNotSame(skeletalCS3244, CS3244_COPY);

        Module CS3244_CLONE = CS3244_COPY.clone();
        assertEquals(CS3244_CLONE, CS3244_COPY);
        assertEquals(CS3244_CLONE.getPrereqString(), CS3244_COPY.getPrereqString());
        assertEquals(CS3244_CLONE.getTags(), CS3244_COPY.getTags());
        assertEquals(CS3244_CLONE.getColor(), CS3244_COPY.getColor());
        assertEquals(CS3244_CLONE.getModuleCode(), CS3244_COPY.getModuleCode());
        assertEquals(CS3244_CLONE.getMcCount(), CS3244_COPY.getMcCount());
        assertNotSame(CS3244_COPY, CS3244_CLONE);

        // hashCode
        assertEquals(CS3244_CLONE.hashCode(), CS3244.hashCode());
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
        assertThrows(NullPointerException.class,
                () -> new Module(null, null, 4, null, null, null));
    }

    @Test
    public void getters_setters() {
        Module CS3244_COPY = new ModuleBuilder(CS3244).build();
        assertEquals(Color.RED, CS3244_COPY.getColor());
        assertEquals(4, CS3244_COPY.getMcCount());
        assertEquals(new Name("Machine Learning"), CS3244_COPY.getName());
        assertEquals("CS3244", CS3244_COPY.getModuleCode().toString());
        assertFalse(CS3244_COPY.getPrereqsSatisfied());
        // assertEquals("MA1101R and MA1521 and ST2334 and (CS2010 or CS2020 or CS2040)",
        //        CS3244.getPrereqString());
        CS3244_COPY.setName(new Name(VALID_NAME_ST2334));
        CS3244_COPY.setColor(Color.AQUAMARINE);
        CS3244_COPY.setMcCount(5);
        CS3244_COPY.setPrereqTree(ParserUtil.parsePrereqTree(""));
        CS3244_COPY.addTag(new UserTag("Cool"));
        assertEquals(5, CS3244_COPY.getMcCount());
        assertEquals(VALID_NAME_ST2334, CS3244_COPY.getName().toString());
        assertEquals(Color.AQUAMARINE, CS3244_COPY.getColor());
        assertTrue(CS3244_COPY.hasTag(new UserTag("Cool")));
        CS3244_COPY.deleteUserTag(new UserTag("Cool"));
        assertFalse(CS3244_COPY.hasTag(new UserTag("Cool")));
    }
}
