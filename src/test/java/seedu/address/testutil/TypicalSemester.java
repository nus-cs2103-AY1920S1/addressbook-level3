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

public class TypicalSemester {
    private static List<Module> typicalMods1 = typicalModuleList();
    private static List<Module> typicalMods2 = typicalModuleList2();
    public static final Semester EMPTY_SEMESTER = new Semester(SemesterName.Y1S1);
    public static final Semester FULL_UNBLOCKED_SEMESTER_1 =
            new Semester(SemesterName.Y1S2, false, "", typicalMods1);
    public static final Semester FULL_UNBLOCKED_SEMESTER_2 =
            new Semester(SemesterName.Y1S2, false, "", typicalMods2);

    private static List<Module> typicalModuleList() {
        List<Module> modules = new ArrayList<>();
        modules.add(ST2334);
        modules.add(CS3244);
        modules.add(CS1101S);
        return modules;
    }

    private static List<Module> typicalModuleList2() {
        List<Module> modules = new ArrayList<>();
        modules.add(CS2102);
        modules.add(CS5339);
        modules.add(CS5219);
        return modules;
    }
}
