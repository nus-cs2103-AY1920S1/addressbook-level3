package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import seedu.address.model.ModuleInfo;
import seedu.address.model.ModulesInfo;

/**
 * A utility class containing a list of {@code ModuleInfo} objects to be used in tests.
 */
public class TypicalModulesInfo {

    public static final ModuleInfo CS2040S = new ModuleInfo(
            "CS2040S",
            "Data Structures and Algorithms",
            4,
            false,
            new ArrayList<>(),
            new ArrayList<>(),
            "CS2040S description",
            "(AND CS1231S CS1101S)");

    public static final ModuleInfo CS4248 = new ModuleInfo(
            "CS4248",
            "Natural Language Processing",
            4,
            false,
            new ArrayList<>(Arrays.asList("AI", "MIR")),
            new ArrayList<>(),
            "CS4248 description",
            "(AND (OR CS3243 CS3245) ST2334)");

    public static ModulesInfo getTypicalModulesInfo() {
        HashMap<String, ModuleInfo> mapModulesInfo = new HashMap<String, ModuleInfo>();
        mapModulesInfo.put("CS2040S", CS2040S);
        mapModulesInfo.put("CS4248", CS4248);
        ModulesInfo modulesInfo = new ModulesInfo(mapModulesInfo);
        return modulesInfo;
    }
}
