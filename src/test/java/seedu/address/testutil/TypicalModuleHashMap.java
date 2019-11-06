package seedu.address.testutil;

import static seedu.address.testutil.TypicalModule.CS2102;
import static seedu.address.testutil.TypicalModule.CS3244;
import static seedu.address.testutil.TypicalModule.CS5219;
import static seedu.address.testutil.TypicalModule.CS5339;
import static seedu.address.testutil.TypicalModule.ST2334;
import static seedu.address.testutil.TypicalModulesInfo.CS1101S;
import static seedu.address.testutil.TypicalModulesInfo.CS2040S;
import static seedu.address.testutil.TypicalModulesInfo.CS4248;

import java.util.HashMap;

import seedu.address.model.Color;
import seedu.address.model.ModuleInfo;
import seedu.address.model.PrereqTree;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Name;
import seedu.address.model.tag.UniqueTagList;

/**
 * A utility class containing a hash map of sample modules to be used in tests.
 */
public class TypicalModuleHashMap {

    public static final Module MODULE_CS1101S = convertModuleInfoToModule(CS1101S);
    public static final Module MODULE_CS2040S = convertModuleInfoToModule(CS2040S);
    public static final Module MODULE_CS4248 = convertModuleInfoToModule(CS4248);
    public static final HashMap<String, Module> TYPICAL_MODULE_HASHMAP = getTypicalModuleHashMap();

    public static HashMap<String, Module> getTypicalModuleHashMap() {
        HashMap<String, Module> moduleHashMap = new HashMap<>();
        moduleHashMap.put("ST2334", ST2334);
        moduleHashMap.put("CS3244", CS3244);
        moduleHashMap.put("CS1101S", TypicalModule.CS1101S);
        moduleHashMap.put("CS2102", CS2102);
        moduleHashMap.put("CS5339", CS5339);
        moduleHashMap.put("CS5219", CS5219);
        return moduleHashMap;
    }

    /**
     * Converts {@code ModuleInfo} from the class {@link TypicalModulesInfo} into a {@code Module} object.
     *
     * @param moduleInfo ModuleInfo for a module.
     * @return The Module that the input ModuleInfo represents.
     */
    public static Module convertModuleInfoToModule(ModuleInfo moduleInfo) {
        Name moduleName = new Name(moduleInfo.getName());
        ModuleCode moduleCode = new ModuleCode(moduleInfo.getCode());
        int moduleMcCount = moduleInfo.getMc();
        Color moduleColor = Color.RED;
        PrereqTree modulePrereqTree = moduleInfo.getPrereqTree();
        // TODO: get tags from moduleinfo
        UniqueTagList moduleTags = new UniqueTagList();
        return new Module(moduleName, moduleCode, moduleMcCount, moduleColor, modulePrereqTree, moduleTags);
    }
}
