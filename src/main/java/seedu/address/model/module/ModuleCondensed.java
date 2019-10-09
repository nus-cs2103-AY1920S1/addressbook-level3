package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The module details
 */
public class ModuleCondensed {
    private final ModuleCode moduleCode;
    private final Title title;
    private final AcadYear acadYear;
    private final List<Integer> semesters = new ArrayList<>();

    public ModuleCondensed(ModuleCode moduleCode, Title title, AcadYear acadYear, List<Integer> semesters) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.acadYear = acadYear;
        this.semesters.addAll(semesters);
    }

    public Title getTitle() {
        return title;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public AcadYear getAcadYear() {
        return acadYear;
    }

    public List<Integer> getSemesters() {
        return semesters;
    }

    @Override
    public String toString() {
        return "AY" + acadYear + " " + moduleCode + " " + title;
    }

    /**
     * Returns true if both modules are the same instance of module.
     */
    public boolean equals(ModuleCondensed other) {
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
        return Objects.hash(moduleCode, title, acadYear, semesters);
    }
}
