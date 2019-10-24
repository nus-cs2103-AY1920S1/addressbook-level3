package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalModulesInfo.CS1101S;
import static seedu.address.testutil.TypicalModulesInfo.CS2040S;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Color;
import seedu.address.model.tag.Tag;

/**
 * A test class for {@code Module}.
 */
public class ModuleTest {

    @Test
    public void equals() {
        // same module -> true
        assertTrue(CS1101S.equals(CS1101S));

        // different module -> false
        assertFalse(CS1101S.equals(CS2040S));

        // skeletal modules
        Module skeletalCS1101S = new Module(new ModuleCode("CS1101S"), Color.RED, new ArrayList<Tag>());
        Module skeletalCS2040S = new Module(new ModuleCode("CS2040S"), Color.RED, new ArrayList<Tag>());
        assertFalse(skeletalCS1101S.equals(CS1101S));
        assertFalse(CS1101S.equals(skeletalCS1101S));
        assertFalse(skeletalCS2040S.equals(CS2040S));
        assertFalse(CS2040S.equals(skeletalCS2040S));

    }

}
