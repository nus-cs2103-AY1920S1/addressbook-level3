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
    public static final ModulesInfo TYPICAL_MODULES_INFO = getTypicalModulesInfo();

    public static final ModuleInfo CS1101S = new ModuleInfo(
            "CS1101S",
            "Programming Methodology",
            4,
            true,
            true,
            new ArrayList<>(),
            new ArrayList<>(),
            "CS1101S description",
            "");

    public static final ModuleInfo CS1231S = new ModuleInfo(
            "CS1231S",
            "Discrete Structures",
            4,
            true,
            true,
            new ArrayList<>(),
            new ArrayList<>(),
            "CS1231S description",
            "");

    public static final ModuleInfo MA1521 = new ModuleInfo(
            "MA1521",
            "Calculus for Computing",
            4,
            true,
            true,
            new ArrayList<>(),
            new ArrayList<>(),
            "MA1521 description",
            "");

    public static final ModuleInfo IS1103X = new ModuleInfo(
            "IS1103X",
            "IS Innovations in Organisation",
            4,
            true,
            true,
            new ArrayList<>(),
            new ArrayList<>(),
            "IS1103X description",
            "");

    public static final ModuleInfo CS2030 = new ModuleInfo(
            "CS2030",
            "Programming Methodology II",
            4,
            false,
            true,
            new ArrayList<>(),
            new ArrayList<>(),
            "CS2030 description",
            "CS1101S");

    public static final ModuleInfo CS2040S = new ModuleInfo(
            "CS2040S",
            "Data Structures and Algorithms",
            4,
            false,
            true,
            new ArrayList<>(),
            new ArrayList<>(),
            "CS2040S description",
            "(AND CS1231S CS1101S)");

    public static final ModuleInfo CS2100 = new ModuleInfo(
            "CS2100",
            "Computer Organisation",
            4,
            false,
            true,
            new ArrayList<>(),
            new ArrayList<>(),
            "CS2100 description",
            "CS1101S");

    public static final ModuleInfo CS2103T = new ModuleInfo(
            "CS2103T",
            "Software Engineering",
            4,
            false,
            true,
            new ArrayList<>(Arrays.asList("SE")),
            new ArrayList<>(),
            "CS2103T description",
            "(AND CS2030 CS2040S)");

    public static final ModuleInfo CS3230 = new ModuleInfo(
            "CS3230",
            "Design and Analysis of Algorithms",
            4,
            false,
            true,
            new ArrayList<>(Arrays.asList("Algo")),
            new ArrayList<>(),
            "CS3230 description",
            "(AND CS2040S CS1231S)");

    public static final ModuleInfo CS4248 = new ModuleInfo(
            "CS4248",
            "Natural Language Processing",
            4,
            false,
            true,
            new ArrayList<>(Arrays.asList("AI", "MIR")),
            new ArrayList<>(),
            "CS4248 description",
            "(AND (OR CS3243 CS3245) ST2334)");

    public static ModulesInfo getTypicalModulesInfo() {
        HashMap<String, ModuleInfo> mapModulesInfo = new HashMap<String, ModuleInfo>();
        mapModulesInfo.put("CS1101S", CS1101S);
        mapModulesInfo.put("CS1231S", CS1231S);
        mapModulesInfo.put("MA1521", MA1521);
        mapModulesInfo.put("IS1103X", IS1103X);
        mapModulesInfo.put("CS2030", CS2030);
        mapModulesInfo.put("CS2040S", CS2040S);
        mapModulesInfo.put("CS2100", CS2100);
        mapModulesInfo.put("CS2103T", CS2103T);
        mapModulesInfo.put("CS3230", CS3230);
        mapModulesInfo.put("CS4248", CS4248);
        return new ModulesInfo(mapModulesInfo);
    }
}
