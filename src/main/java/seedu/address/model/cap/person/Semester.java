package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.common.Module;

/**
 * Represents a Semester in a specific academic year and semesterPeriod.
 */
public class Semester {

    private final SemesterPeriod semesterPeriod;
    private final AcademicYear academicYear;
    private final ArrayList<Module> modules;
    /**
     * Constructs a {@code Semester}.
     *
     * @param semesterPeriod A valid semester period.
     * @param academicYear A valid academic year.
     */
    public Semester(SemesterPeriod semesterPeriod, AcademicYear academicYear) {
        requireNonNull(semesterPeriod);
        requireNonNull(academicYear);
        this.semesterPeriod = semesterPeriod;
        this.academicYear = academicYear;
        modules = new ArrayList<>();
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public SemesterPeriod getSemesterPeriod() {
        return semesterPeriod;
    }

    /**
     * Compares if two modules are the same.
     * @param otherSemester module of comparison
     * @return boolean value on whether the modules are identical
     */
    public boolean isSameSemester(Semester otherSemester) {
        if (otherSemester == this) {
            return true;
        }

        return otherSemester != null
            && otherSemester.getSemesterPeriod().equals(getSemesterPeriod())
            && (otherSemester.getAcademicYear().equals(getAcademicYear()));
    }

    /**
     * Add modules to the module array.
     * @param modules
     */
    public void addModules(Module[] modules) {
        for (Module module : modules) {
            this.modules.add(module);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Semester // instanceof handles nulls
            && semesterPeriod == semesterPeriod // check for same semester period
            && academicYear.equals(((Semester) other).academicYear)); // state check
    }

    @Override
    public String toString() {
        return academicYear.toString() + "S" + semesterPeriod.toString();
    }
}
