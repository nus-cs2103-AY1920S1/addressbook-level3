package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.module.exceptions.SemesterNotFoundException;

/**
 * The module details
 */
public class Module {
    private final ModuleId moduleId;
    private final Title title;
    private final Description description;
    private final List<Semester> semesterData = new ArrayList<>();

    public Module(ModuleId moduleId, Title title, Description description, List<Semester> semesterData) {
        this.moduleId = moduleId;
        this.title = title;
        this.description = description;
        this.semesterData.addAll(semesterData);
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public ModuleId getModuleId() {
        return moduleId;
    }

    public ModuleCode getModuleCode() {
        return moduleId.getModuleCode();
    }

    public AcadYear getAcadYear() {
        return moduleId.getAcadYear();
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
        return "AY" + moduleId + " " + title;
    }

    /**
     * Returns true if both modules are the same instance of module.
     */
    public boolean equals(Module other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other.getModuleId().equals(this.getModuleId())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleId, title, description, semesterData);
    }
}
