package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalModulesInfo.CS2040S;
import static seedu.address.testutil.TypicalModulesInfo.getTypicalModulesInfo;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

//import java.util.HashSet;

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
    public void getModuleCodeStrings_returnsModuleCodeStrings() {
        List<String> moduleCodeStrings = modulesInfo.getModuleCodeStrings();
        List<String> expected = Arrays.asList("CS1101S", "CS1231S", "CS2030", "CS2040S", "CS2100", "CS2103T",
                "CS3230", "CS4248", "IS1103X", "MA1521");
        assertEquals(moduleCodeStrings, expected);
    }
}
