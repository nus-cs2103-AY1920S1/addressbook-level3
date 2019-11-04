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

    public static final Module ALICE = new ModuleBuilder().withModuleCode("CS2103")
            .withModuleTitle("Software Programming")
            .withDescription("This module introduces the necessary conceptual and analytical "
                    + "tools for systematic and rigorous development of software systems.")
            .withSemester("1920", 1)
            .withCredit(4)
            .withFaculty("Computing")
            .withGrade("A").build();
    public static final Module BENSON = new ModuleBuilder().withModuleCode("CS2100")
            .withDescription("The objective of this module is to familiarise"
                    + " students with the fundamentals of computing devices.")
            .withModuleTitle("Computer Organisation")
            .withSemester("1920", 1)
            .withFaculty("Computing")
            .withCredit(4)
            .withGrade("A").build();
    public static final Module CARL = new ModuleBuilder().withModuleCode("CS2101")
            .withModuleTitle("Effective Communication for Computing Professionals")
            .withDescription("This module aims to equip students with "
                    + "the skills needed to communicate technical information.")
            .withSemester("1920", 1)
            .withFaculty("Computing")
            .withCredit(4)
            .withGrade("A-").build();
    public static final Module DANIEL = new ModuleBuilder().withModuleCode("CS3226")
            .withSemester("1920", 2)
            .withDescription("This module introduces students to software development on the Web platforms.")
            .withModuleTitle("Web Programming and Applications")
            .withGrade("A-")
            .withFaculty("Computing")
            .withCredit(4).build();
    public static final Module ELLE = new ModuleBuilder().withModuleCode("CS3233")
            .withSemester("2021", 2)
            .withFaculty("Computing")
            .withCredit(4)
            .withGrade("A-")
            .withDescription("This module aims to prepare students in competitive problem solving.")
            .withModuleTitle("Competitive Programming").build();

    private TypicalModule() {} // prevents instantiation

    /**
     * Returns an {@ModuleCode CalendarAddressBook} with all the typical s.
     */
    public static CapLog getTypicalCapLog() {
        CapLog capLog = new CapLog();
        for (Module module : getTypicalModules()) {
            capLog.addModule(module);
        }
        return capLog;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
    }
}
