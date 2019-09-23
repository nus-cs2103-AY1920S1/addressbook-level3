package seedu.tarence.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.tarence.testutil.TypicalModules.CS1101S;
import static seedu.tarence.testutil.TypicalModules.CS2103;
import static seedu.tarence.testutil.TypicalModules.VALID_MODCODE_CS1101S;

import org.junit.jupiter.api.Test;

import seedu.tarence.testutil.ModuleBuilder;

public class ModuleTest {

    @Test
    public void equals() {
        // same values -> returns true
        Module cs2103Copy = new ModuleBuilder(CS2103).build();
        assertTrue(CS2103.equals(cs2103Copy));

        // same object -> returns true
        assertTrue(CS2103.equals(CS2103));

        // null -> returns false
        assertFalse(CS2103.equals(null));

        // different type -> returns false
        assertFalse(CS2103.equals(5));

        // different module -> returns false
        assertFalse(CS2103.equals(CS1101S));

        // different name -> returns false
        Module editedCS2103 = new ModuleBuilder(CS2103).withModCode(VALID_MODCODE_CS1101S).build();
        assertFalse(CS2103.equals(editedCS2103));

    }
}
