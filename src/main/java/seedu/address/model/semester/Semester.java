package seedu.address.model.semester;

import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;

/**
 * Represents a semester of university for CS Undergraduate Students.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Semester {
    // Identity fields
    private final SemesterName semesterName;

    // Data fields
    private final UniqueModuleList modules = new UniqueModuleList();
    private boolean isBlocked = false;
    private String reasonForBlocked;

    /**
     * SemesterName field must be present and not null.
     */
    public Semester(SemesterName semesterName) {
        this.semesterName = semesterName;
    }

    public SemesterName getSemesterName() {
        return semesterName;
    }

    public UniqueModuleList getModules() {
        return modules;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public String getReasonForBlocked() {
        return reasonForBlocked;
    }

    public int getMcCount() {
        return modules.getMcCount();
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void setReasonForBlocked(String reasonForBlocked) {
        this.reasonForBlocked = reasonForBlocked;
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void removeModule(Module module) {
        modules.remove(module);
    }
}
