package mams.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mams.model.module.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    public static final Module CS1010 = new ModuleBuilder()
            .withModuleCode("CS1010").withModuleName("Programming Methodology")
            .withModuleDescription("test1").withLecturerName("Tung Kum Hoe Anthony")
            .withTimeSlot("1,2,45,46,47").withQuota("10")
            .withTags("A6614133H").build();
    public static final Module CS1231 = new ModuleBuilder()
            .withModuleCode("CS1231").withModuleName("Discrete Structures")
            .withModuleDescription("test2").withLecturerName("Terence Sim")
            .withTimeSlot("1,2,45,46,47").withQuota("15")
            .withTags("A6614133H").build();
    public static final Module CS1020 = new ModuleBuilder()
            .withModuleCode("CS1020").withModuleName("Data Structures and Algorithms Is")
            .withModuleDescription("test3").withLecturerName("Tan Sun Teck")
            .withTimeSlot("21,20,57,58,59").withQuota("20")
            .withTags("A5802229").build();
    public static final Module CS2040 = new ModuleBuilder()
            .withModuleCode("CS2040").withModuleName("Data Structures and Algorithms")
            .withModuleDescription("test4").withLecturerName("Chong Ket Fah")
            .withTimeSlot("40,41,10,11,12").withQuota("20")
            .withTags("A5802229").build();
    private TypicalModules() {} // prevents instantiation

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS1010, CS1231, CS1020, CS2040));
    }
}
