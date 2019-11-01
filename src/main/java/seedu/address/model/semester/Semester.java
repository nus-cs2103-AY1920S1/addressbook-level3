package seedu.address.model.semester;

import java.util.List;

import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;

/**
 * Represents a semester of university for CS Undergraduate Students.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Semester implements Cloneable {
    // Identity fields
    private SemesterName semesterName; // removed final keyword for the clone() method below

    // Data fields
    private UniqueModuleList modules = new UniqueModuleList();
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
                    String reasonForBlocked, List<Module> modules, boolean isExpanded) {
        this.semesterName = semesterName;
        this.isBlocked = isBlocked;
        this.reasonForBlocked = reasonForBlocked;
        for (Module module : modules) {
            this.modules.add(module);
        }
        this.isExpanded = isExpanded;
    }

    @Override
    protected Semester clone() throws CloneNotSupportedException {
        Semester clone = (Semester) super.clone();
        clone.isBlocked = this.isBlocked;
        clone.reasonForBlocked = this.reasonForBlocked;
        clone.modules = this.modules.clone();
        return clone;
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

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getReasonForBlocked() {
        return reasonForBlocked;
    }

    public void setReasonForBlocked(String reasonForBlocked) {
        this.reasonForBlocked = reasonForBlocked;
    }

    public int getMcCount() {
        return modules.getMcCount();
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void removeModule(String module) {
        modules.remove(module);
    }

    public boolean hasModule(String module) {
        return this.modules.contains(module);
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    /**
     * Clears/deletes all modules this semester.
     */
    public void clearAllModules() {
        modules = new UniqueModuleList();
    }

    // NOTE: this is for the GUI to use for Milestone 2
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(semesterName).append(":").append("\n");
        for (Module module : modules) {
            result.append(module.toString()).append("\n");
            //result.append(module.getModuleCode().value).append("\n");
        }

        return result.toString();
    }


    /**
     * Converts this semester to a String suitable for display in a simplified study plan.
     */
    public String toStringForSimplifiedStudyPlan() {
        StringBuilder result = new StringBuilder();
        for (Module module : modules) {
            result.append("-").append(module.getModuleCode()).append("\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Semester) {
            Semester other = (Semester) obj;
            return this.semesterName == other.getSemesterName(); // so that no two semesters can have the same name
            /*
                    this.modules.equals(other.getModules())
                    && this.isBlocked == other.isBlocked
                    && (this.reasonForBlocked == null || this.reasonForBlocked.equals(other.reasonForBlocked))
                    && this.isExpanded == other.isExpanded
                    && this.semesterName == other.getSemesterName();

             */
        }
        return false;
    }

}
