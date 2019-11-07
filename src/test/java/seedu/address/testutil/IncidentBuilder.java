package seedu.address.testutil;

import static seedu.address.model.incident.Incident.getTagSet;

import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.Incident.Status;
import seedu.address.model.incident.IncidentDateTime;
import seedu.address.model.incident.IncidentId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Username;
import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.model.vehicle.VehicleNumber;
import seedu.address.model.vehicle.VehicleType;

/**
 * A utility class to help with the building of Incident objects.
 */
public class IncidentBuilder {
    public static final String DEFAULT_DISTRICT = "1";
    public static final String DEFAULT_CALLER = "85355255";
    public static final String DEFAULT_DATETIME = "2016-12-02T14:30:40";
    public static final String DEFAULT_DESC = "This is an incident description.";
    public static final String DEFAULT_ID = "1220160001";
    public static final Person DEFAULT_PERSON = new Person(new Name("Alex Yeoh"),
            new Phone("87438807"),
            new Email("alexyeoh@example.com"), getTagSet("friends"),
            new Username("user1"),
            new Password("pass123"));
    public static final Vehicle DEFAULT_VEHICLE =
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("ABC1234D"),
                    new District(9), new Availability("AVAILABLE"));


    private Person operator;
    private Status status;
    private District district;
    private IncidentDateTime dateTime;
    private Description desc;
    private CallerNumber caller;
    private IncidentId id;
    private Vehicle vehicle;

    public IncidentBuilder() {
        district = new District(Integer.parseInt(DEFAULT_DISTRICT));
        dateTime = new IncidentDateTime(DEFAULT_DATETIME);
        desc = new Description(DEFAULT_DESC);
        caller = new CallerNumber(DEFAULT_CALLER);
        id = new IncidentId(DEFAULT_ID);
        operator = DEFAULT_PERSON;
        status = Status.SUBMITTED_REPORT;
        vehicle = DEFAULT_VEHICLE;
    }

    /**
     * Initializes the IncidentBuilder with the data of {@code IncidentToCopy}.
     */
    public IncidentBuilder(Incident incidentToCopy) {
        district = incidentToCopy.getDistrict();
        desc = incidentToCopy.getDesc();
        caller = incidentToCopy.getCallerNumber();
        dateTime = new IncidentDateTime(DEFAULT_DATETIME);
        id = new IncidentId(DEFAULT_ID);
        operator = DEFAULT_PERSON;
        status = Status.SUBMITTED_REPORT;
        vehicle = DEFAULT_VEHICLE;
    }

    /**
     * Initializes the IncidentBuilder with the data of incomplete draft {@code IncidentToCopy} and supplied caller
     * number and description.
     */
    public IncidentBuilder(Incident incidentToCopy, CallerNumber callerNumber, Description description) {
        district = incidentToCopy.getDistrict();
        dateTime = incidentToCopy.getIncidentDateTime();
        id = incidentToCopy.getIncidentId();
        operator = incidentToCopy.getOperator();
        status = Status.COMPLETE_DRAFT;
        vehicle = incidentToCopy.getVehicle();
        caller = callerNumber;
        desc = description;
    }

    /**
     * Initializes the IncidentBuilder with the data of incomplete draft {@code IncidentToCopy} and supplied status.
     */
    public IncidentBuilder(Incident incidentToCopy, Status updatedStatus) {
        district = incidentToCopy.getDistrict();
        dateTime = incidentToCopy.getIncidentDateTime();
        id = incidentToCopy.getIncidentId();
        operator = incidentToCopy.getOperator();
        status = updatedStatus;
        vehicle = incidentToCopy.getVehicle();
        caller = incidentToCopy.getCallerNumber();
        desc = incidentToCopy.getDesc();
    }

    /**
     * Sets the {@code District} of the {@code Incident} that we are building.
     */
    public IncidentBuilder withDistrict(String district) {
        this.district = new District(Integer.parseInt(district));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Incident} that we are building.
     */
    public IncidentBuilder withDescription(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    /**
     * Sets the {@code IncidentId} of the {@code Incident} that we are building.
     */
    public IncidentBuilder withId(String id) {
        this.id = new IncidentId(id);
        return this;
    }


    /**
     * Sets the {@code CallerNumber} of the {@code Incident} that we are building.
     */
    public IncidentBuilder withCaller(String caller) {
        this.caller = new CallerNumber(caller);
        return this;
    }


    public Incident build() {
        return new Incident(operator, district, dateTime, id, caller, desc, status, vehicle);
    }
}
