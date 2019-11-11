package mams.testutil;

import static mams.testutil.TypicalAppeals.getTypicalAppeals;
import static mams.testutil.TypicalModules.getTypicalModules;
import static mams.testutil.TypicalStudents.getTypicalStudents;

import mams.model.Mams;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Student;

/**
 * Utility class for building a sample MAMS populated with some data from {@code TypicalAppeals},
 * {@code TypicalModules}, and {@code TypicalStudents}.
 */
public class TypicalMams {

    /**
     * Returns an {@code Mams} with all the typical students.
     */
    public static Mams getTypicalMams() {
        Mams ab = new Mams();
        for (Appeal appeal : getTypicalAppeals()) {
            ab.addAppeal(appeal);
        }
        for (Module module : getTypicalModules()) {
            ab.addModule(module);
        }
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }
}
