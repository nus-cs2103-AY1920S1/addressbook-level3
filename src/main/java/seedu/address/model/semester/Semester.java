package seedu.address.model.semester;

import java.util.Iterator;
import java.util.List;

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
    private boolean isBlocked;
    private String reasonForBlocked;
    private boolean isExpanded = false;

    /**
     * SemesterName field must be present and not null.
     */
    public Semester(SemesterName semesterName) {
        this.semesterName = semesterName;
        isBlocked = false;
    }

    /**
     * This constructor is for {@code JsonAdaptedSemester} to create a semester with skeletal modules inside.
     */
    public Semester(SemesterName semesterName, boolean isBlocked,
                    String reasonForBlocked, List<Module> modules) {
        this.semesterName = semesterName;
        this.isBlocked = isBlocked;
        this.reasonForBlocked = reasonForBlocked;
        for(Module module : modules) {
            this.modules.add(module);
        }
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

    public boolean isExpanded() {
        return isExpanded;
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

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void removeModule(Module module) {
        modules.remove(module);
    }

    // NOTE: this is for the GUI to use for Milestone 2
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(semesterName + ":" + "\n");
        if (modules != null) {
            Iterator<Module> moduleIterator = modules.iterator();
            while (moduleIterator.hasNext()) {
                Module module = moduleIterator.next();
                result.append(module.toString() + "\n");
            }
        } else {
            result.append("NO MODULES FOUND \n");
        }

        return result.toString();
    }
}
