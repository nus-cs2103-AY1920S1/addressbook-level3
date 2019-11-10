package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.cap.CapLog;
import seedu.address.model.common.Module;


/**
 * A utility class containing a list of {@ModuleCode } objects to be used in tests.
 */
public class TypicalModule {

    public static final Module CS2103 = new ModuleBuilder().withModuleCode("CS2103")
            .withModuleTitle("Software Engineering")
            .withSemester("1920", 1)
            .withCredit(4)
            .withGrade("A").build();
    public static final Module CS2100 = new ModuleBuilder().withModuleCode("CS2100")
            .withModuleTitle("Computer Organisation")
            .withSemester("1920", 1)
            .withCredit(4)
            .withGrade("A").build();
    public static final Module CS2101 = new ModuleBuilder().withModuleCode("CS2101")
            .withModuleTitle("Effective Communication for Computing Professionals")
            .withSemester("1920", 1)
            .withCredit(4)
            .withGrade("B").build();
    public static final Module CS3226 = new ModuleBuilder().withModuleCode("CS3226")
            .withSemester("1920", 2)
            .withModuleTitle("Web Programming and Applications")
            .withGrade("A-")
            .withCredit(4).build();
    public static final Module CS3233 = new ModuleBuilder().withModuleCode("CS3233")
            .withSemester("2021", 2)
            .withCredit(4)
            .withGrade("B+")
            .withModuleTitle("Competitive Programming").build();

    private TypicalModule() {} // prevents instantiation

    /**
     * Returns a {@ModuleCode CapLog} with all the typical modules.
     */
    public static CapLog getTypicalCapLog() {
        CapLog capLog = new CapLog();
        for (Module module : getTypicalModules()) {
            capLog.addModule(module);
        }
        return capLog;
    }

    public static List<Module> getTypicalModules() {
        //populate with students
        return new ArrayList<>(Arrays.asList(CS2103, CS2100, CS2101, CS3226, CS3233));
    }
}
