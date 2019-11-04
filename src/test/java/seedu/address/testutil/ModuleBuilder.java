package seedu.address.testutil;

import java.util.*;

import seedu.address.model.common.Module;
import seedu.address.model.cap.person.*;
import seedu.address.model.cap.util.*;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS2103";
    public static final String DEFAULT_TITLE = "Software Engineering";
    public static final String DEFAULT_ACADEMIC_YEAR = "1920";
    private static final int DEFAULT_SEMESTER_PERIOD = 1;
    public static final String DEFAULT_DESCRIPTION = "This module introduces the necessary conceptual and analytical "
            + "tools for systematic and rigorous development of software systems. ";
    public static final int DEFAULT_CREDIT = 4;
    public static final String DEFAULT_FACULTY = "Computing";
    public static final String DEFAULT_GRADE = "A";

    private ModuleCode moduleCode;
    private Title title;
    private Semester semester;
    private AcademicYear academicYear;
    private SemesterPeriod semesterPeriod;
    private Description description;
    private Credit credit;
    private Faculty faculty;
    private Grade grade;

    public ModuleBuilder() {
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        title = new Title(DEFAULT_TITLE);
        academicYear = new AcademicYear(DEFAULT_ACADEMIC_YEAR);
        semesterPeriod = new SemesterPeriod(DEFAULT_SEMESTER_PERIOD);
        semester = new Semester(semesterPeriod, academicYear);
        description = new Description(DEFAULT_DESCRIPTION);
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
        description = moduleToCopy.getDescription();
        credit = moduleToCopy.getCredit();
        faculty = moduleToCopy.getFaculty();
        grade = moduleToCopy.getGrade();
    }

    /**
     * Sets the {@code title} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleCode(String title) {
        this.moduleCode = new ModuleCode(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code ModuleDay} of the {@code Module} that we are building.
     */
    public ModuleBuilder withAcademicYear(String academicYear) {
        this.academicYear = new AcademicYear(academicYear);
        return this;
    }

    /**
     * Sets the {@code ModuleTime} of the {@code Module} that we are building.
     */
    public ModuleBuilder withSemesterPeriod(int semesterPeriod) {
        this.semesterPeriod = new SemesterPeriod(semesterPeriod);
        return this;
    }

    /**
     * Sets the {@code ModuleTime} of the {@code Module} that we are building.
     */
    public ModuleBuilder withSemester(String academicYear, int semesterPeriod) {
        this.semester = new Semester(new SemesterPeriod(semesterPeriod), new AcademicYear(academicYear));
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Module} that we are building.
     */
    public ModuleBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code week} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCredit(int credit) {
        this.credit = new Credit(credit);
        return this;
    }

    public ModuleBuilder withFaculty(String faculty) {
        this.faculty = new Faculty(faculty);
        return this;
    }

    public ModuleBuilder withGrade(String grade) {
        this.grade = new Grade(grade);
        return this;
    }

    public Module build() {
        return new Module(moduleCode, title, semester, description, credit, faculty, grade);
    }
}
