package cs.f10.t1.nursetraverse.model.visit;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.model.datetime.EndDateTime;
import cs.f10.t1.nursetraverse.model.datetime.StartDateTime;
import cs.f10.t1.nursetraverse.model.patient.Patient;
import cs.f10.t1.nursetraverse.model.visittask.VisitTask;

/**
 * Represents a Visit in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Visit {
    // Pointer fields (not actually stored as a variable in JSON or CSV files)
    private final Patient patient;

    // Data fields
    private final Remark remark;
    private final StartDateTime startDateTime;
    private final EndDateTime endDateTime;
    private final List<VisitTask> visitTasks;

    /**
     * Every field must be present and not null, with the exception of endDateTime
     * to accommodate for visits that have not finished.
     */
    public Visit(Remark remark, StartDateTime startDateTime,
                 EndDateTime endDateTime, List<VisitTask> visitTasks, Patient patient) {
        CollectionUtil.requireAllNonNull(remark, startDateTime, visitTasks, patient);
        this.remark = remark;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.visitTasks = visitTasks;
        this.patient = patient;
    }

    public Remark getRemark() {
        return remark;
    }

    public StartDateTime getStartDateTime() {
        return startDateTime;
    }

    public Optional<EndDateTime> getEndDateTime() {
        return endDateTime == null ? Optional.empty() : Optional.of(endDateTime);
    }

    /**
     * Returns an immutable visit task list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<VisitTask> getVisitTasks() {
        return Collections.unmodifiableList(visitTasks);
    }

    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns true if both visits have the same data fields and visit tasks.
     * This defines a stronger notion of equality between two visits.
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
                && CollectionUtil.checkEqual(otherVisit.getVisitTasks(), getVisitTasks());
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
                .append(" End Time: ");
        Optional<EndDateTime> endDateTime = getEndDateTime();
        builder.append(endDateTime.equals(Optional.empty()) ? "Ongoing" : endDateTime)
                .append(" Tags: ");
        getVisitTasks().forEach(builder::append);
        builder.append(" Remarks: ")
                .append(getRemark());
        return builder.toString();
    }

}
