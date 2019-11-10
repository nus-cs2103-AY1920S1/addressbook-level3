package seedu.address.model.incident;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.vehicle.Availability.VEHICLE_BUSY_TAG;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;

/**
 * Represents an incident report in the IMS.
 */
public class Incident {

    // auto-filled fields

    /** The operator on call who created this incident report. */
    private final Person operator;

    /** Date and time information of this incident report. */
    private final IncidentDateTime incidentDateTime;

    /**
     * The vehicle assigned to investigate this incident.
     * Assigned when the new incident is created, and cannot be changed after.
     * */
    private Vehicle vehicle;

    /** The unique id associated with this incident. */
    private final IncidentId id;

    /** The district associated with this incident. */
    private final District district;

    // fields to be filled in by the operator

    /** A description of this incident. */
    private Description description;

    /** The caller number associated with this incident. */
    private CallerNumber callerNumber;

    /** Enum to track incident status. */
    public enum Status {
        INCOMPLETE_DRAFT("Incomplete Draft"), // incomplete draft - not all fields filled AND not submitted.
        COMPLETE_DRAFT ("Complete Draft"), // complete draft - all fields filled AND not submitted.
        SUBMITTED_REPORT("Submitted"); // submitted report - all fields filled AND submitted.

        public static final String MESSAGE_CONSTRAINTS =
                "Status can only take on one of three values: INCOMPLETE_DRAFT, COMPLETE_DRAFT, SUBMITTED_REPORT";

        private String statusLabel;
        Status(String statusLabel) {
            this.statusLabel = statusLabel;
        }

        @Override
        public String toString() {
            return statusLabel;
        }
    }

    /** The status of this incident. */
    private Status status; // default is draft

    /** Constructor for generating an incident draft according to 'new' command i.e. fills auto-filled fields.
     * @param operator operator generating the new incident report.
     * @param district district number where the incident occurred.
     */
    public Incident (Person operator, District district) {
        requireAllNonNull();
        this.operator = operator;
        this.district = district;

        this.incidentDateTime = new IncidentDateTime();
        this.id = new IncidentId(incidentDateTime.getMonth(), incidentDateTime.getYear());

        this.status = Status.INCOMPLETE_DRAFT; // newly created report

        // set to null as they will be filled in later
        this.description = new Description("[TO BE FILLED]");
        this.callerNumber = new CallerNumber("00000000");
    }

    /**
     * Constructor that takes in all attributes of incident. Only called when loading data.
     * @param operator operator generating the new incident report.
     * @param district district number where the incident occurred.
     * @param incidentDateTime date and time of creation of the incident.
     * @param incidentId unique id used to identify the incident.
     * @param callerNumber phone number of the caller who reported the incident.
     * @param description description of the incident.
     * @param status draft/submit status of the incident.
     * @param vehicle vehicle dispatched to investigate the incident.
     */
    public Incident(Person operator, District district, IncidentDateTime incidentDateTime, IncidentId incidentId,
                    CallerNumber callerNumber, Description description, Status status, Vehicle vehicle) {
        requireAllNonNull();
        this.operator = operator;
        this.district = district;
        this.incidentDateTime = incidentDateTime;
        this.id = incidentId;
        this.callerNumber = callerNumber;
        this.description = description;
        this.status = status;
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return this.vehicle; // should never be null
    }

    public IncidentDateTime getDateTime() {
        return this.incidentDateTime;
    }

    public Description getDesc() {
        return this.description;
    }

    public CallerNumber getCallerNumber() {
        return this.callerNumber;
    }

    public District getDistrict() {
        return this.district;
    }

    public IncidentId getIncidentId() {
        return id;
    }

    public Person getOperator() {
        return operator;
    }

    public IncidentDateTime getIncidentDateTime() {
        return incidentDateTime;
    }

    public Status getStatus() {
        return this.status;
    }

    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Checks if incident is a complete or an incomplete draft.
     * @return true if incident is a draft, false otherwise.
     */
    public boolean isDraft() {
        assert this.status != null;
        return this.status.equals(Status.COMPLETE_DRAFT) || this.status.equals(Status.INCOMPLETE_DRAFT);
    }

    /**
     * Checks if incident is a complete draft ready for submission.
     * @return true if incident is a complete draft, false otherwise.
     */
    public boolean isCompleteDraft() {
        assert this.status != null;
        return this.status.equals(Status.COMPLETE_DRAFT);
    }

    /**
     * Checks if all required fields in this incident are 'filled' i.e. not null.
     * @return true if all required incident fields are non-null, false otherwise.
     */
    public boolean areAllFieldsFilled() {
        // method can be extended if new compulsory incident fields are added
        return this.operator != null && this.district != null && this.incidentDateTime != null && this.id != null
                && this.callerNumber != null && this.description != null && this.vehicle != null;
    }

    /**
     * Checks if incident is an incomplete draft.
     * @return true if incident is a incomplete draft, false otherwise.
     */
    public boolean isIncompleteDraft() {
        assert this.status != null;
        return this.status.equals(Status.INCOMPLETE_DRAFT);
    }

    /**
     * Checks if incident has been submitted.
     * @return true if incident has been submitted, false otherwise.
     */
    public boolean isSubmitted() {
        assert this.status != null;
        return this.status.equals(Status.SUBMITTED_REPORT);
    }

    /**
     * Returns true if both Incidents have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two Incidents.
     */
    public boolean isSameIncident(Incident otherIncident) {
        if (otherIncident == this) {
            return true;
        }

        return otherIncident != null
                && otherIncident.getDateTime().equals(getDateTime())
                && otherIncident.getCallerNumber().equals(getCallerNumber())
                && otherIncident.getDesc().equals(getDesc())
                && otherIncident.getDistrict().equals(getDistrict());
    }

    /**
     * Assigns vehicle to incident.
     * @param vehicle vehicle to be assigned to investigate the incident.
     */
    public void addVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        vehicle.setAvailability(new Availability(VEHICLE_BUSY_TAG));
    }

    /**
     * Returns true if both Incidents have the same identity and data fields.
     * This defines a stronger notion of equality between two Incidents.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Incident)) {
            return false;
        }

        Incident otherIncident = (Incident) other;

        return otherIncident.getDateTime().equals(getDateTime())
                && otherIncident.getCallerNumber().equals(getCallerNumber())
                && otherIncident.getDistrict().equals(getDistrict())
                && otherIncident.getIncidentId().equals(getIncidentId());
    }

    @Override
    public String toString() {
        return "Incident #" + id.getId();
    }
}
