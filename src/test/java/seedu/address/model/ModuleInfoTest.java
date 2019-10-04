package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalModulesInfo.CS2040S;
import static seedu.address.testutil.TypicalModulesInfo.CS4248;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ModuleInfoTest {

    @Test
    public void parsePrereqTree_validPrereqTreeString_validPrereqTree() {
        PrereqTree cs2040tree = new PrereqNode("AND", new ArrayList<>(Arrays.asList(
                new PrereqLeaf("CS1231S"),
                new PrereqLeaf("CS1101S")
        )));
        assertEquals(cs2040tree, CS2040S.getPrereqTree());
    }

    @Test
    public void verify_failPrerequisites_returnsFalse() {
        boolean result1 = CS2040S.verify(Arrays.asList("CS1101S", "CS1231"));
        boolean result2 = CS4248.verify(Arrays.asList("ST2131", "CS32243"));
        assertEquals(result1, false);
        assertEquals(result2, false);
    }

    @Test
    public void verify_passPrerequisites_returnsTrue() {
        boolean result1 = CS2040S.verify(Arrays.asList("CS1101S", "CS1231S", "CS2222"));
        boolean result2 = CS4248.verify(Arrays.asList("ST2131", "CS3243", "ST2334"));
        assertEquals(result1, true);
        assertEquals(result2, true);
    }
}
