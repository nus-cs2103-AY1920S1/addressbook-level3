package seedu.address.testutil;

import seedu.address.model.cap.module.AcademicYear;
import seedu.address.model.cap.module.Credit;
import seedu.address.model.cap.module.Grade;
import seedu.address.model.cap.module.ModuleCode;
import seedu.address.model.cap.module.Semester;
import seedu.address.model.cap.module.SemesterPeriod;
import seedu.address.model.cap.module.Title;
import seedu.address.model.common.Module;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    private static final String DEFAULT_MODULE_CODE = "CS2103";
    private static final String DEFAULT_TITLE = "Software Engineering";
    private static final String DEFAULT_ACADEMIC_YEAR = "1920";
    private static final int DEFAULT_SEMESTER_PERIOD = 1;
    private static final int DEFAULT_CREDIT = 4;
    private static final String DEFAULT_GRADE = "A";

    private ModuleCode moduleCode;
    private Title title;
    private Semester semester;
    private AcademicYear academicYear;
    private SemesterPeriod semesterPeriod;
    private Credit credit;
    private Grade grade;

    public ModuleBuilder() {
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        title = new Title(DEFAULT_TITLE);
        academicYear = new AcademicYear(DEFAULT_ACADEMIC_YEAR);
        semesterPeriod = new SemesterPeriod(DEFAULT_SEMESTER_PERIOD);
        semester = new Semester(semesterPeriod, academicYear);
        credit = new Credit(DEFAULT_CREDIT);
        grade = new Grade(DEFAULT_GRADE);
    }

    /**
     * Initializes the moduleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        moduleCode = moduleToCopy.getModuleCode();
        title = moduleToCopy.getTitle();
        academicYear = moduleToCopy.getSemester().getAcademicYear();
        semesterPeriod = moduleToCopy.getSemester().getSemesterPeriod();
        semester = moduleToCopy.getSemester();
        credit = moduleToCopy.getCredit();
        grade = moduleToCopy.getGrade();
    }

    /**
     * Sets the {@code ModuleCode} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleCode(String title) {
        this.moduleCode = new ModuleCode(title);
        return this;
    }

    /**
     * Parses the {@code ModuleTitle} into a {@code Set<Tag>} and set it to the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code AcademicYear} of the {@code Module} that we are building.
     */
    public ModuleBuilder withAcademicYear(String academicYear) {
        this.academicYear = new AcademicYear(academicYear);
        return this;
    }

    /**
     * Sets the {@code SemesterPeriod} of the {@code Module} that we are building.
     */
    public ModuleBuilder withSemesterPeriod(int semesterPeriod) {
        this.semesterPeriod = new SemesterPeriod(semesterPeriod);
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code Module} that we are building.
     */
    public ModuleBuilder withSemester(String academicYear, int semesterPeriod) {
        this.semester = new Semester(new SemesterPeriod(semesterPeriod), new AcademicYear(academicYear));
        return this;
    }

    /**
     * Sets the {@code Credit} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCredit(int credit) {
        this.credit = new Credit(credit);
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code Module} that we are building.
     */
    public ModuleBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }

    /**
     * Initiates the build of a module.
     */
    public Module build() {
        return new Module(moduleCode, title, semester, credit, grade);
    }
}
