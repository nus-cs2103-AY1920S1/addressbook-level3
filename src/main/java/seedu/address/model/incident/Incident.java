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

/**
 * Represents an incident report in the IMS.
 */
public class Incident {

    // auto-filled fields

    /** The operator on call who created this incident report. */
    private final Person operator;

    /** Date and time information of this incident report. */
    private final IncidentDateTime incidentDateTime;

    /** The vehicle assigned to investigate this incident. */
    //private Vehicle vehicle; TODO add vehicle details to incident report

    /** The unique id associated with this incident. */
    private final IncidentId id;

    /** The location associated with this incident. */
    private final District location;

    // fields to be filled in by the operator

    /** A description of this incident. */
    private Description description;

    /** The caller number associated with this incident. */
    private CallerNumber callerNumber;

    /** Enum to track incident status. */
    // draft - not all fields filled AND not submitted.
    // complete - all fields filled AND not submitted.
    // final - all fields filled AND submitted.
    private enum Status {
        DRAFT, COMPLETE, FINAL
    }

    private Status status = Status.DRAFT; // default is draft

    /** Constructor for generating an incident draft according to 'new' command i.e. fills auto-filled fields.
     * @param operator operator generating the new incident report.
     * @param location district number where the incident occurred.
     */
    public Incident (Person operator, District location) {
        this.operator = operator;
        this.location = location;

        this.incidentDateTime = new IncidentDateTime();
        this.id = new IncidentId(incidentDateTime.getMonth(), incidentDateTime.getYear());
        // this.vehicle = TODO

        this.status = Status.DRAFT; // newly created report

        // set to null as they will be filled in later
        this.description = null;
        this.callerNumber = null;
    }

    // load past incident cases
    public Incident(IncidentId id, District location, IncidentDateTime incidentDateTime, String operator) {
        // TODO: figure out importing rest of person class
        this.operator = new Person(new Name(operator), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("friends"), new Username("user1"), new Password("pass123"));
        this.incidentDateTime = incidentDateTime;
        this.id = id;
        this.description = new Description("Fluff description for search testing arson fire fires");
        this.location = location;
        this.callerNumber = new CallerNumber("98989898");
        //this.vehicle = TODO
    }

    // constructor used by edit command.
    // TODO change to accommodate 'Status'. i.e. only 'FINAL' reports can be edited.
    public Incident(District district, IncidentDateTime incidentDateTime, CallerNumber callerNumber, Description desc) {
        this.operator = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("friends"), new Username("user1"), new Password("pass123"));
        this.incidentDateTime = incidentDateTime;
        this.location = district;
        this.callerNumber = callerNumber;
        this.description = desc;
        this.id = new IncidentId(incidentDateTime.getMonth(), incidentDateTime.getYear());
        // this.vehicle = TODO
    }

    /**
     * Constructor for generating an incident draft according to 'fill' command i.e. all fields filled.
     */
    public Incident(Person operator, District location, IncidentDateTime incidentDateTime, IncidentId incidentId,
                    CallerNumber callerNumber, Description description, Status status) {
        this.operator = operator;
        this.location = location;
        this.incidentDateTime = incidentDateTime;
        this.id = incidentId;
        this.callerNumber = callerNumber;
        this.description = description;
        this.status = Status.COMPLETE;
    }

    /**
     * Returns a new updated incident report by filling callerNumber and description fields.
     * Triggered by 'fill' command.
     * @param callerNumber phone number of the caller reporting the incident.
     * @param description description of this incident.
     * @return updated incident report.
     */
    public static Incident updateReport(Incident toUpdate, CallerNumber callerNumber, Description description) {
        return new Incident(toUpdate.getOperator(), toUpdate.getDistrict(), toUpdate.getIncidentDateTime(),
                toUpdate.getIncidentId(), callerNumber, description, Status.COMPLETE);
    }

    /**
     * Submits this report by updating its status.
     */
    public void submit() {
        // updateStatus(Status.FINAL); // report has been submitted
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
        return this.location;
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

    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Checks if incident is a draft.
     * @return true if incident is a draft, false otherwise.
     */
    public boolean isDraft() {
        return this.status.equals(Status.DRAFT);
    }

    /**
     * Checks if incident is completely filled.
     * @return true if incident is completely filled, false otherwise.
     */
    public boolean isComplete() {
        return this.status.equals(Status.COMPLETE);
    }

    /**
     * Checks if incident has been submitted.
     * @return true if incident has been submitted, false otherwise.
     */
    public boolean isSubmitted() {
        return this.status.equals(Status.FINAL);
    }

    /**
     * To be uncommented after vehicle assignment is done. Commented now to avoid code quality check failures.
     * public void addVehicle(Vehicle vehicle) {
     *    this.vehicle = vehicle;
     * }
     */

    /**
     * Returns true if both Vehicles of the same VehicleType have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two Vehicles.
     */
    public boolean isSameIncident(Incident otherIncident) {
        if (otherIncident == this) {
            return true;
        }

        return otherIncident != null
                && otherIncident.getIncidentId().equals(getIncidentId());
    }

    /**
     * Returns true if both Vehicles have the same identity and data fields.
     * This defines a stronger notion of equality between two Vehicles.
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
        return otherIncident.getDateTime().equals(getDateTime());
        /* TODO: Fix equality check
        return otherIncident.getIncidentId().equals(getIncidentId())
                && otherIncident.getDesc().equals(getDesc());
         */
    }

}
