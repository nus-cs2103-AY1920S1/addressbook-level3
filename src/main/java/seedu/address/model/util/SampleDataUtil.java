package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.IncidentManager;
import seedu.address.model.ReadOnlyIncidentManager;

import seedu.address.model.incident.CallerNumber;
import seedu.address.model.incident.Description;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.IncidentDateTime;
import seedu.address.model.incident.IncidentId;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Username;
import seedu.address.model.tag.Tag;
import seedu.address.model.vehicle.Availability;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;
import seedu.address.model.vehicle.VehicleNumber;
import seedu.address.model.vehicle.VehicleType;

/**
 * Contains utility methods for populating {@code IncidentManager} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("Admin", "Team-1"), new Username("Agent01"), new Password("password")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getTagSet("Team-1"), new Username("Agent02"), new Password("password")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getTagSet("Team-2"), new Username("Operator01"), new Password("password")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getTagSet("Team-2"), new Username("Operator02"), new Password("password")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getTagSet("Team-3"), new Username("Agent03"), new Password("password")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                getTagSet("Team-3", "Admin"), new Username("Operator03"), new Password("password"))
        };
    }

    public static Incident[] getSampleIncidents() {
        Person[] samplePersons = getSamplePersons();
        IncidentDateTime[] sampleIncidentDateTimes = getSampleIncidentDateTimes();
        Incident[] sampleIncidents = new Incident[] {
            new Incident(samplePersons[0], new District(1), sampleIncidentDateTimes[0],
                new IncidentId(sampleIncidentDateTimes[0].getMonth(), sampleIncidentDateTimes[0].getYear()),
                new CallerNumber("93894576"), new Description("Shoplifting reported in District 1 Mall"),
                Incident.Status.SUBMITTED_REPORT),
            new Incident(samplePersons[1], new District(2), sampleIncidentDateTimes[1],
                new IncidentId(sampleIncidentDateTimes[1].getMonth(), sampleIncidentDateTimes[1].getYear()),
                new CallerNumber("98098765"), new Description("Bicycle theft at District 2 MRT station"),
                Incident.Status.SUBMITTED_REPORT),
            new Incident(samplePersons[2], new District(3), sampleIncidentDateTimes[2],
                new IncidentId(sampleIncidentDateTimes[2].getMonth(), sampleIncidentDateTimes[2].getYear()),
                new CallerNumber("87595849"), new Description("Pet cat stuck on a tree in District 3 stadium"),
                Incident.Status.COMPLETE_DRAFT),
            new Incident(samplePersons[3], new District(4), sampleIncidentDateTimes[3],
                new IncidentId(sampleIncidentDateTimes[3].getMonth(), sampleIncidentDateTimes[3].getYear()),
                new CallerNumber("89090908"), new Description("Minor traffic accident in District 4 highway"),
                Incident.Status.COMPLETE_DRAFT),
            new Incident(samplePersons[4], new District(5), sampleIncidentDateTimes[4],
                new IncidentId(sampleIncidentDateTimes[4].getMonth(), sampleIncidentDateTimes[4].getYear()),
                new CallerNumber("87656743"), new Description("Arson reported at District 5 warehouse"),
                Incident.Status.COMPLETE_DRAFT)
        };

        return sampleIncidents;
    }

    private static IncidentDateTime[] getSampleIncidentDateTimes() {
        return new IncidentDateTime[] {
            new IncidentDateTime("Dec 21, 2012, 12:00:00 PM"),
            new IncidentDateTime("Jan 11, 2013, 12:00:00 AM"),
            new IncidentDateTime("May 01, 2014, 1:00:00 AM"),
            new IncidentDateTime("Jun 16, 2015, 2:00:00 PM"),
            new IncidentDateTime("Sep 01, 2016, 9:00:00 PM")
        };
    }

    public static Vehicle[] getSampleVehicles() {
        return new Vehicle[] {
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("SGS2121G"),
                new District(1), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("BBA2222F"),
                new District(6), new Availability("BUSY")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("FKTH1221P"),
                new District(20), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("OLI4445C"),
                new District(8), new Availability("BUSY"))
        };
    }

    public static ReadOnlyIncidentManager getSampleIncidentManager() {
        IncidentManager sampleAb = new IncidentManager();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Incident sampleIncident : getSampleIncidents()) {
            sampleAb.addIncident(sampleIncident);
        }
        for (Vehicle sampleVehicle : getSampleVehicles()) {
            sampleAb.addVehicle(sampleVehicle);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
