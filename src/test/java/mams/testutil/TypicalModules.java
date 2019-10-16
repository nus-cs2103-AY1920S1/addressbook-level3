package mams.testutil;

import java.util.List;

import mams.model.Mams;
import mams.model.module.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    public static final Module CS1010 = new ModuleBuilder()
            .withModuleCode("CS1010").withModuleName("Programming Methodology")
            .withModuleDescription("test1").withLecturerName("Tung Kum Hoe Anthony")
            .withTimeSlot("1,2,45,46,47").withQuota("100")
            .withTags("A6614133H,A1748370H").build();
    public static final Module CS1231 = new ModuleBuilder()
            .withModuleCode("CS1231").withModuleName("Discrete Structures")
            .withModuleDescription("test2").withLecturerName("Terence Sim")
            .withTimeSlot("1,2,45,46,47").withQuota("150")
            .withTags("A6614133H,A1748370H").build();
    public static final Module CS1020 = new ModuleBuilder()
            .withModuleCode("CS1020").withModuleName("Data Structures and Algorithms Is")
            .withModuleDescription("test3").withLecturerName("Tan Sun Teck")
            .withTimeSlot("21,20,55,56,57").withQuota("200")
            .withTags("A5802229H,A1748370H,A5524592H").build();

    private TypicalModules() {} // prevents instantiation

    public static Mams getTypicalMams() {
        Mams ab = new Mams();
        for (Module module : getTypicalModule()) {
            ab.addModule(module);
        }
        return ab;
    }

    private static List<Module> getTypicalModule() {
        return null;
    }
}
