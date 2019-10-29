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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ModuleSummary)) {
            return false;
        }
        ModuleSummary m = (ModuleSummary) other;
        if (m == this) {
            return true;
        } else if (m.moduleId.equals(this.moduleId)
                && m.title.equals(this.title)
                && m.semesters.equals(this.semesters)) {
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
