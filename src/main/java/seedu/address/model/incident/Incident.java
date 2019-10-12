package seedu.address.model.incident;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;

/**
 * Represents an incident report in the IMS.
 */
public class Incident {

    //is autofilled
    private Person operator;
    private IncidentDateTime incidentDateTime;
    private Vehicle car;
    private IncidentId id;

    //needs to be entered by operator
    private final Description incidentDesc;
    private final District location;
    private final CallerNumber callerNumber;


    /**
     * Creates a new Incident report, fields will be filled in through prompts in the GUI.
     * @param caller is the phone number of the caller that reported the incident.
     */
    public Incident(String caller) {
        //this.operator = autofilled on sign in
        // TODO: autofill operator upon sign in. Currently dummy data
        this.operator = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"));
        this.incidentDateTime = new IncidentDateTime();
        this.id = new IncidentId(incidentDateTime.getMonth(), incidentDateTime.getYear());
        this.incidentDesc = promptForDescription();
        this.location = promptForLocation();
        this.callerNumber = new CallerNumber(caller);
        //this.car = VehicleAssigner.assignVehicle(location);
    }

    // load past incident cases
    public Incident(IncidentId id, District location, IncidentDateTime incidentDateTime, String operator) {
        // TODO: figure out importing rest of person class
        this.operator = new Person(new Name(operator), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"));
        this.incidentDateTime = incidentDateTime;
        this.id = id;
        this.incidentDesc = new Description("Fluff description");
        this.location = location;
        this.callerNumber = new CallerNumber("98989898");
        //this.car = VehicleAssigner.assignVehicle(location);
    }


    /**
     * static method to prompt operator for incident location
     * @return district which will be stored to location
     */
    public static District promptForLocation() {
        System.out.println("Enter location:"); //need to change to GUI prompt
        Scanner sc = new Scanner(System.in); //need to change to GUI input
        int dist = Integer.parseInt(sc.next());
        while (!District.isValidDistrict(dist)) {
            System.out.println("Please enter a valid district");
            dist = Integer.parseInt(sc.next());
        }
        return new District(dist);
    }

    /**
     * static method to prompt operator for incident description
     * @return a new description object
     */
    public static Description promptForDescription() {
        System.out.println("Enter incident description now? y/n"); //change to GUI
        Scanner sc = new Scanner(System.in); //change to GUI
        String desc = "";
        if (sc.next().equals("y")) {
            System.out.println("Please enter description:");
            desc = sc.nextLine();
        }
        return new Description(desc);
    }


    public IncidentDateTime getDateTime() {
        return this.incidentDateTime;
    }

    public Description getDesc() {
        return this.incidentDesc;
    }

    public CallerNumber getCallerNumber() {
        return this.callerNumber;
    }

    public District getLocation() { return this.location; }

    public Vehicle getCar() {
        return car;
    }

    public IncidentId getIncidentId() { return id; }

    public Person getOperator() { return operator; }

    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

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
        return otherIncident.getIncidentId().equals(getIncidentId())
                && otherIncident.getDesc().equals(getDesc());
    }
}
