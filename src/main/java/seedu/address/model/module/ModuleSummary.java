package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A summary of a module
 */
public class ModuleSummary {
    private final ModuleId moduleId;
    private final Title title;
    private final List<Integer> semesters = new ArrayList<>();

    public ModuleSummary(ModuleId moduleId, Title title, List<Integer> semesters) {
        this.moduleId = moduleId;
        this.title = title;
        this.semesters.addAll(semesters);
    }

    public ModuleId getModuleId() {
        return moduleId;
    }

    public Title getTitle() {
        return title;
    }

    public List<Integer> getSemesters() {
        return semesters;
    }

    /**
     * Returns true if both module summaries are the same instance of module summaries.
     */
    public boolean equals(ModuleSummary other) {
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
    public String toString() {
        return "AY" + moduleId + " " + title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleId, title, semesters);
    }
}
