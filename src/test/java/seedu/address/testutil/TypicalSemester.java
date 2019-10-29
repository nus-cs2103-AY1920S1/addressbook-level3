package seedu.address.testutil;

import static seedu.address.testutil.TypicalModule.CS1101S;
import static seedu.address.testutil.TypicalModule.CS2102;
import static seedu.address.testutil.TypicalModule.CS3244;
import static seedu.address.testutil.TypicalModule.CS5219;
import static seedu.address.testutil.TypicalModule.CS5339;
import static seedu.address.testutil.TypicalModule.ST2334;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;

/**
 * A utility class containing sample semesters to be used in tests.
 */
public class TypicalSemester {
    public static final Semester EMPTY_SEMESTER = new Semester(SemesterName.Y1S1);
    public static final Semester EMPTY_SEMESTER_1 = new Semester(SemesterName.Y1S1);
    public static final Semester EMPTY_SEMESTER_2 = new Semester(SemesterName.Y1S2);
    public static final Semester EMPTY_SEMESTER_3 = new Semester(SemesterName.Y2S1);
    public static final Semester EMPTY_SEMESTER_4 = new Semester(SemesterName.Y2S2);
    public static final Semester EMPTY_SEMESTER_5 = new Semester(SemesterName.Y3S1);
    public static final Semester EMPTY_SEMESTER_6 = new Semester(SemesterName.Y3S2);
    public static final Semester EMPTY_SEMESTER_7 = new Semester(SemesterName.Y4S1);
    public static final Semester EMPTY_SEMESTER_8 = new Semester(SemesterName.Y4S2);
    private static List<Module> typicalMods1 = typicalModuleList();
    public static final Semester FULL_UNBLOCKED_SEMESTER_1 =
            new Semester(SemesterName.Y1S1, false, "", typicalMods1);
    private static List<Module> typicalMods2 = typicalModuleList3();
    public static final Semester FULL_UNBLOCKED_SEMESTER_1_WITH_CS1101S =
            new Semester(SemesterName.Y1S1, false, "", typicalMods2);
    private static List<Module> typicalMods3 = typicalModuleList2();
    public static final Semester FULL_UNBLOCKED_SEMESTER_2 =
            new Semester(SemesterName.Y1S2, false, "", typicalMods3);

    /**
     * Generate a list of modules for testing.
     *
     * @return Typical module list.
     */
    private static List<Module> typicalModuleList() {
        List<Module> modules = new ArrayList<>();
        modules.add(ST2334);
        modules.add(CS3244);
        return modules;
    }

    /**
     * Generate a list of modules for testing, different from {@code typicalModuleList()}.
     *
     * @return Typical module list.
     */
    private static List<Module> typicalModuleList2() {
        List<Module> modules = new ArrayList<>();
        modules.add(CS2102);
        modules.add(CS5339);
        modules.add(CS5219);
        return modules;
    }

    /**
     * Generate a list of modules for testing, with one module (CS) more than {@code typicalModuleList()}.
     *
     * @return Typical module list.
     */
    private static List<Module> typicalModuleList3() {
        List<Module> modules = new ArrayList<>();
        modules.add(ST2334);
        modules.add(CS3244);
        modules.add(CS1101S);
        return modules;
    }
}
