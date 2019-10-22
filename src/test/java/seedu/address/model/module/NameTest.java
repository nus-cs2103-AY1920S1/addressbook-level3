package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalModulesInfo.CS1101S;
import static seedu.address.testutil.TypicalModulesInfo.CS2040S;

import org.junit.jupiter.api.Test;

/**
 * A test class for {@code Name}.
 */
public class NameTest {

    @Test
    public void equals() {
        // same module -> true
        assertTrue(CS1101S.getName().equals(CS1101S.getName()));

        // different modules -> false
        assertFalse(CS1101S.getName().equals(CS2040S.getName()));
    }
}
