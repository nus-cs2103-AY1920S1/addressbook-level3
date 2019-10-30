package seedu.address.model.incident;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Username;
import seedu.address.model.tag.Tag;
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

    // TODO delete this field once all constructors accept status as attribute.
    private Status status = Status.INCOMPLETE_DRAFT; // default is draft

    /** Constructor for generating an incident draft according to 'new' command i.e. fills auto-filled fields.
     * @param operator operator generating the new incident report.
     * @param district district number where the incident occurred.
     */
    public Incident (Person operator, District district) {
        this.operator = operator;
        this.district = district;

        this.incidentDateTime = new IncidentDateTime();
        this.id = new IncidentId(incidentDateTime.getMonth(), incidentDateTime.getYear());

        this.status = Status.INCOMPLETE_DRAFT; // newly created report

        // set to null as they will be filled in later
        this.description = null;
        this.callerNumber = null;
    }

    // constructor used by edit command.
    // TODO this constructor is redundant, update it to use the constructor below.
    public Incident(IncidentId id, District district, IncidentDateTime incidentDateTime,
                    CallerNumber callerNumber, Description desc) {

        this.operator = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("friends"), new Username("user1"), new Password("pass123"));
        this.incidentDateTime = incidentDateTime;
        this.district = district;
        this.callerNumber = callerNumber;
        this.description = desc;
        this.id = id;
    }

    /**
     * Constructor that takes in all attributes of incident. Only called when loading data.
     * @param operator
     * @param district
     * @param incidentDateTime
     * @param incidentId
     * @param callerNumber
     * @param description
     * @param status
     * @param vehicle
     */
    public Incident(Person operator, District district, IncidentDateTime incidentDateTime, IncidentId incidentId,
                    CallerNumber callerNumber, Description description, Status status, Vehicle vehicle) {
        this.operator = operator;
        this.district = district;
        this.incidentDateTime = incidentDateTime;
        this.id = incidentId;
        this.callerNumber = callerNumber;
        this.description = description;
        this.status = status;
        this.vehicle = vehicle;
    }

    /**
     * Constructor for generating an incident draft with all fields filled.
     * Vehicle should have already been attached to incident when draft first created.
     * // TODO add vehicle field
     */
    public Incident(Person operator, District district, IncidentDateTime incidentDateTime, IncidentId incidentId,
                    CallerNumber callerNumber, Description description, Status status) {
        this.operator = operator;
        this.district = district;
        this.incidentDateTime = incidentDateTime;
        this.id = incidentId;
        this.callerNumber = callerNumber;
        this.description = description;
        this.status = status;
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
        return this.status.equals(Status.COMPLETE_DRAFT) || this.status.equals(Status.INCOMPLETE_DRAFT);
    }

    /**
     * Checks if incident is a complete draft ready for submission.
     * @return true if incident is a complete draft, false otherwise.
     */
    public boolean isCompleteDraft() {
        return this.status.equals(Status.COMPLETE_DRAFT);
    }

    /**
     * Checks if incident is an incomplete draft.
     * @return true if incident is a incomplete draft, false otherwise.
     */
    public boolean isIncompleteDraft() {
        return this.status.equals(Status.INCOMPLETE_DRAFT);
    }

    /**
     * Checks if incident has been submitted.
     * @return true if incident has been submitted, false otherwise.
     */
    public boolean isSubmitted() {
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
     * @param vehicle
     */
    public void addVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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
                && otherIncident.getDesc().equals(getDesc())
                && otherIncident.getDistrict().equals(getDistrict());
    }

    // TODO: more refined toString method
    @Override
    public String toString() {
        return "Incident #" + id.getId();
    }
}
