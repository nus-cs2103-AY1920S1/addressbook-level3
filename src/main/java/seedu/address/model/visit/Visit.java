package seedu.address.model.visit;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.visittodoitem.VisitTodoItemList;

/**
 * Represents a Visit in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Visit {
    // Data fields
    private final Remark remark;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;
    private final VisitTodoItemList visitTodoItems;

    /**
     * Every field must be present and not null.
     */
    public Visit(Remark remark, StartDateTime startDateTime,
                 EndDateTime endDateTime, VisitTodoItemList visitTodoItems) {
        requireAllNonNull(remark, startDateTime, endDateTime, visitTodoItems);
        this.remark = remark;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.visitTodoItems = visitTodoItems;
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
     * Returns a VisitTodoItemList.
     */
    public VisitTodoItemList getVisitTodoItems() {
        return visitTodoItems;
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
                && otherVisit.getVisitTodoItems().equals(getVisitTodoItems());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(remark, startDateTime, endDateTime, visitTodoItems);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Start Time: ")
                .append(getStartDateTime())
                .append(" End Time: ")
                .append(getEndDateTime())
                .append(" Tags: ");
        getVisitTodoItems().forEach(builder::append);
        builder.append("Remarks")
                .append(getRemark());
        return builder.toString();
    }

}
