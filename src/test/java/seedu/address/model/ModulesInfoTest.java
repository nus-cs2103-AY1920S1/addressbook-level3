package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalModulesInfo.CS2040S;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class ModulesInfoTest {

    private final ModulesInfo modulesInfo = getTypicalModulesInfo();

    @Test
    public void find_nullString_returnsNull() {
        ModuleInfo moduleInfo = modulesInfo.find(null);
        assertEquals(moduleInfo, null);
    }

    @Test
    public void find_invalidModuleCode_returnsNull() {
        ModuleInfo moduleInfo = modulesInfo.find("CS2040C");
        assertEquals(moduleInfo, null);
    }

    @Test
    public void find_validModuleCode_returnsModuleInfo() {
        ModuleInfo moduleInfo = modulesInfo.find("CS2040S");
        assertEquals(moduleInfo, CS2040S);
    }

    @Test
    public void getFocusAreaNames_returnsAllNames() {
        HashSet<String> set = modulesInfo.getFocusAreaNames();
        HashSet<String> expectedSet = new HashSet<>();
        expectedSet.add("AI");
        expectedSet.add("MIR");
        assertEquals(set, expectedSet);
    }
}
