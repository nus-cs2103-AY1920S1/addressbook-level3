package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalModulesInfo.CS1101S;
import static seedu.address.testutil.TypicalModulesInfo.CS2040S;
import static seedu.address.testutil.TypicalModulesInfo.CS4248;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void getInformation_hasPrerequisites_returnsString() {
        String actual = CS2040S.getInformation();
        String expected = "CS2040S: Data Structures and Algorithms (Core module)\n"
                + "4 MCs, not S/U-able\n"
                + "Prerequisites: CS1231S and CS1101S\n"
                + "CS2040S description";
        assertEquals(expected, actual);
    }

    @Test
    public void getInformation_noPrerequisites_returnsString() {
        String actual = CS1101S.getInformation();
        String expected = "CS1101S: Programming Methodology (Core module)\n"
                + "4 MCs, S/U-able\n"
                + "Prerequisites: none\n"
                + "CS1101S description";
        assertEquals(expected, actual);
    }

    @Test
    public void getCode_returnsCode() {
        String cs2040code = CS2040S.getCode();
        assertEquals("CS2040S", cs2040code);
    }

    @Test
    public void getName_returnsName() {
        String cs2040name = CS2040S.getName();
        assertEquals("Data Structures and Algorithms", cs2040name);
    }

    @Test
    public void getMc_returnsMc() {
        int cs2040mc = CS2040S.getMc();
        assertEquals(4, cs2040mc);
    }

    @Test
    public void getIsCore_returnsIsCore() {
        boolean cs2040isCore = CS2040S.getIsCore();
        assertEquals(true, cs2040isCore);
    }

    @Test
    public void getFocusPrimaries_returnsFocusPrimaries() {
        List<String> cs4248focusPrimaries = CS4248.getFocusPrimaries();
        assertEquals(Arrays.asList("AI", "MIR"), cs4248focusPrimaries);
    }

    @Test
    public void getFocusElectives_returnsFocusElectives() {
        List<String> cs2040focusElectives = CS2040S.getFocusElectives();
        assertEquals(Arrays.asList(), cs2040focusElectives);
    }
}
