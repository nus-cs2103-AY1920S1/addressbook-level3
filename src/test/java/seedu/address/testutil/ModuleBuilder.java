package seedu.address.testutil;

import seedu.address.model.cap.person.AcademicYear;
import seedu.address.model.cap.person.Credit;
import seedu.address.model.cap.person.Faculty;
import seedu.address.model.cap.person.Grade;
import seedu.address.model.cap.person.ModuleCode;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.cap.person.SemesterPeriod;
import seedu.address.model.cap.person.Title;
import seedu.address.model.common.Module;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    private static final String DEFAULT_MODULE_CODE = "CS2103";
    private static final String DEFAULT_TITLE = "Software Engineering";
    private static final String DEFAULT_ACADEMIC_YEAR = "1920";
    private static final int DEFAULT_SEMESTER_PERIOD = 1;
    private static final String DEFAULT_DESCRIPTION = "This module introduces the necessary conceptual and analytical "
            + "tools for systematic and rigorous development of software systems. ";
    private static final int DEFAULT_CREDIT = 4;
    private static final String DEFAULT_FACULTY = "Computing";
    private static final String DEFAULT_GRADE = "A";

    private ModuleCode moduleCode;
    private Title title;
    private Semester semester;
    private AcademicYear academicYear;
    private SemesterPeriod semesterPeriod;
    private Credit credit;
    private Faculty faculty;
    private Grade grade;

    public ModuleBuilder() {
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        title = new Title(DEFAULT_TITLE);
        academicYear = new AcademicYear(DEFAULT_ACADEMIC_YEAR);
        semesterPeriod = new SemesterPeriod(DEFAULT_SEMESTER_PERIOD);
        semester = new Semester(semesterPeriod, academicYear);
        credit = new Credit(DEFAULT_CREDIT);
        faculty = new Faculty(DEFAULT_FACULTY);
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
        faculty = moduleToCopy.getFaculty();
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
     * Sets the {@code Faculty} of the {@code Module} that we are building.
     */
    public ModuleBuilder withFaculty(String faculty) {
        this.faculty = new Faculty(faculty);
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
        return new Module(moduleCode, title, semester, credit, faculty, grade);
    }
}
