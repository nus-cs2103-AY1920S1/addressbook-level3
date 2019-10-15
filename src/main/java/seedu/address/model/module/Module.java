package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.module.exceptions.SemesterNotFoundException;

/**
 * The module details
 */
public class Module {
    private final ModuleCode moduleCode;
    private final Title title;
    private final Description description;
    private final AcadYear acadYear;
    private final List<Semester> semesterData = new ArrayList<>();

    public Module(ModuleCode moduleCode, Title title, Description description,
                  AcadYear acadYear, List<Semester> semesterData) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.acadYear = acadYear;
        this.semesterData.addAll(semesterData);
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public AcadYear getAcadYear() {
        return acadYear;
    }

    public List<Semester> getSemesterData() {
        return semesterData;
    }

    public Semester getSemester(SemesterNo semesterNo) {
        for (Semester semester : semesterData) {
            if (semester.getSemesterNo().equals(semesterNo)) {
                return semester;
            }
        }
        throw new SemesterNotFoundException();
    }

    @Override
    public String toString() {
        return "AY" + acadYear + " " + moduleCode + " " + title;
    }

    /**
     * Returns true if both modules are the same instance of module.
     */
    public boolean equals(Module other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other.getAcadYear().equals(this.getAcadYear())
            && other.getModuleCode().equals(this.getModuleCode())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleCode, title, description, acadYear, semesterData);
    }
}
