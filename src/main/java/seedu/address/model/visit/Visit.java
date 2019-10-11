package seedu.address.model.visit;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.visittask.UniqueVisitTaskList;

/**
 * Represents a Visit in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Visit {
    // Data fields
    private final Remark remark;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;
    private final UniqueVisitTaskList visitTasks;

    /**
     * Every field must be present and not null.
     */
    public Visit(Remark remark, StartDateTime startDateTime,
                 EndDateTime endDateTime, UniqueVisitTaskList visitTasks) {
        requireAllNonNull(remark, startDateTime, endDateTime, visitTasks);
        this.remark = remark;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.visitTasks = visitTasks;
    }

    public Remark getRemark() {
        return remark;
    }

    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    public EndDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Returns a VisitTaskList.
     */
    public UniqueVisitTaskList getVisitTasks() {
        return visitTasks;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameVisit(Visit otherVisit) {
        if (otherVisit == this) {
            return true;
        }

        return otherVisit != null
                && otherVisit.getRemark().equals(getRemark())
                && (otherVisit.getStartDateTime().equals(getStartDateTime())
                || otherVisit.getEndDateTime().equals(getEndDateTime()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Visit)) {
            return false;
        }

        Visit otherVisit = (Visit) other;
        return otherVisit.getRemark().equals(getRemark())
                && otherVisit.getStartDateTime().equals(getStartDateTime())
                && otherVisit.getEndDateTime().equals(getEndDateTime())
                && otherVisit.getVisitTasks().equals(getVisitTasks());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(remark, startDateTime, endDateTime, visitTasks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Start Time: ")
                .append(getStartDateTime())
                .append(" End Time: ")
                .append(getEndDateTime())
                .append(" Tags: ");
        getVisitTasks().forEach(builder::append);
        builder.append("Remarks")
                .append(getRemark());
        return builder.toString();
    }

}
