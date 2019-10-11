package seedu.tarence.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.tarence.model.Application;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Tutorial;


/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    public static final String VALID_MODCODE_CS2103 = "CS2103";
    public static final String VALID_MODCODE_CS1101S = "CS1101S";

    public static final Module CS2103 = new ModuleBuilder().withModCode("CS2103")
            .withTutorials(new ArrayList<Tutorial>()).build();
    public static final Module CS1101S = new ModuleBuilder().withModCode("CS1101S")
            .withTutorials(new ArrayList<Tutorial>()).build();

    private TypicalModules() {} // prevents instantiation

    /**
     * Returns an {@code Application} with all the typical modules.
     */
    public static Application getTypicalApplication() {
        List<Student> students = TypicalStudents.getTypicalStudents();
        Tutorial tutorial = new TutorialBuilder().withStudents(students).build();
        List<Module> modules = new ArrayList<Module>();
        modules.add(CS1101S);
        modules.add(CS2103);
        modules.get(0).addTutorial(tutorial);

        Application ab = new Application();
        for (Module module : modules) {
            ab.addModule(module);
        }

        ab.addTutorial(tutorial);

        for (Student student : students) {
            ab.addStudent(student);
        }
        return ab;
    }
}
