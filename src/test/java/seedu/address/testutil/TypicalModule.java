package seedu.address.testutil;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Color;
import seedu.address.model.module.Module;

/**
 * A utility class containing sample modules to be used in tests.
 */
public class TypicalModule {
    public static final Module CS1101S = new ModuleBuilder()
            .withModuleCode("CS1101S")
            .withName("Programming Methodology")
            .withColor(Color.RED)
            .withPrereqsSatisfied(true)
            .withPrereqTree(ParserUtil.parsePrereqTree(""))
            .withMcCount(4)
            .withTags("Hard")
            .build();
    public static final Module CS2102 = new ModuleBuilder()
            .withModuleCode("CS2102")
            .withName("Database Systems")
            .withColor(Color.RED)
            .withPrereqsSatisfied(true)
            .withPrereqTree(ParserUtil.parsePrereqTree("(AND (OR CS1020 CS2020 CS2030 CS2040) CS1231)"))
            .withMcCount(4)
            .withTags("Database")
            .build();
    public static final Module ST2334 = new ModuleBuilder()
            .withModuleCode("ST2334")
            .withName("Probability and Statistics")
            .withColor(Color.RED)
            .withPrereqsSatisfied(true)
            .withPrereqTree(ParserUtil.parsePrereqTree("(OR MA1102R MA1312 MA1505 MA1507 MA1521)"))
            .withMcCount(4)
            .withTags("Stats")
            .build();
    public static final Module CS3244 = new ModuleBuilder()
            .withModuleCode("CS3244")
            .withName("Machine Learning")
            .withColor(Color.RED)
            .withPrereqsSatisfied(false)
            .withPrereqTree(ParserUtil.parsePrereqTree("(AND MA1101R MA1521 ST2334 (OR CS2010 CS2020 CS2040)"))
            .withMcCount(4)
            .withTags("Cool", "AI", "ML")
            .build();
    public static final Module CS5219 = new ModuleBuilder()
            .withModuleCode("CS5219")
            .withName("Automated Software Validation\n")
            .withColor(Color.RED)
            .withPrereqsSatisfied(false)
            .withPrereqTree(ParserUtil.parsePrereqTree("CS2104"))
            .withMcCount(4)
            .withTags("SWE")
            .build();
    public static final Module CS5339 = new ModuleBuilder()
            .withModuleCode("CS5339")
            .withName("Theory and Algorithms for Machine Learning\n")
            .withColor(Color.RED)
            .withPrereqsSatisfied(false)
            .withPrereqTree(ParserUtil.parsePrereqTree("CS3244"))
            .withMcCount(4)
            .withTags("SWE")
            .build();

    private TypicalModule() {
    } // prevents instantiation
}
