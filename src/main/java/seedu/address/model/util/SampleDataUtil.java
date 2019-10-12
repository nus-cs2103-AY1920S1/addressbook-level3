package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                getTagSet("friends"), new Username("Agent01"), new Password("password")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getTagSet("colleagues", "friends"), new Username("Agent02"), new Password("password")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getTagSet("neighbours"), new Username("Operator01"), new Password("password")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getTagSet("family"), new Username("Operator02"), new Password("password")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getTagSet("classmates"), new Username("Agent03"), new Password("password")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                getTagSet("colleagues"), new Username("Operator03"), new Password("password"))
        };
    }

    public static Incident[] getSampleIncidents() {
        return new Incident[] {
            new Incident(new IncidentId(3, 2018), new District(3),
                    new IncidentDateTime("2018-03-03T10:15:30"), "Alex Yeoh"),
            new Incident(new IncidentId(10, 2016), new District(20),
                    new IncidentDateTime("2016-10-10T12:30:35"), "David Li"),
            new Incident(new IncidentId(2, 2015), new District(20),
                    new IncidentDateTime("2016-10-10T12:30:35"), "Bernice Yu"),
            new Incident(new IncidentId(3, 2013), new District(20),
                    new IncidentDateTime("2016-10-10T12:30:35"), "Alex Yeoh"),
            new Incident(new IncidentId(12, 2015), new District(20),
                    new IncidentDateTime("2016-10-10T12:30:35"), "David Li")
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

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
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
