package seedu.module.model.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a TrackedModule in the ModuleList.
 */
public class TrackedModule implements Module, Trackable {

    // Identity field
    private final ArchivedModule archivedModule;
    private List<Deadline> deadlineList = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public TrackedModule(ArchivedModule archivedModule) {
        this.archivedModule = archivedModule;
    }

    public String getModuleCode() {
        return archivedModule.getModuleCode();
    }

    public String getTitle() {
        return archivedModule.getTitle();
    }

    public String getDescription() {
        return archivedModule.getDescription();
    }

    public String getDeadline() {
        String deadlineString = "Deadline: \n";
        for (int i = 0; i < deadlineList.size(); i++) {
            deadlineString += ((i + 1) + ". " + deadlineList.get(i).getDescription()
                    + ", " + deadlineList.get(i).getTime()) + "\n";
        }
        return deadlineString;
    }

    public List<Deadline> getDeadlineList() {
        return deadlineList;
    }

    public void addDeadline(Deadline deadline) {
        this.deadlineList.add(deadline);
    }

    /**
     * Returns true if both modules of the same name have the same identity field.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(TrackedModule otherTrackedModule) {
        if (otherTrackedModule == this) {
            return true;
        }

        return otherTrackedModule != null
                && otherTrackedModule.getModuleCode().equals(getModuleCode());
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TrackedModule)) {
            return false;
        }

        TrackedModule otherTrackedModule = (TrackedModule) other;
        return otherTrackedModule.getModuleCode().equals(getModuleCode())
                && otherTrackedModule.getTitle().equals(getTitle())
                && otherTrackedModule.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(archivedModule, deadlineList);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleCode())
                .append(" Title: ").append(getTitle())
                .append(" Description: ").append(getDescription())
                .append(" Deadline: ").append(getDeadline());
        return builder.toString();
    }

}
