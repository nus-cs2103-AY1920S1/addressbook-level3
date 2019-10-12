package seedu.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.incident.Incident;
import seedu.address.model.incident.IncidentId;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Incident[] getSampleIncidents() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return new Incident[] {
                new Incident(new IncidentId(3, 2018), new District(3),
                        LocalDateTime.parse("2018-03-03T10:15:30", formatter), "Jeremy Irons"),
                new Incident(new IncidentId(10, 2016), new District(20),
                        LocalDateTime.parse("2016-10-10T12:30:35", formatter), "Chris Pratt"),
                new Incident(new IncidentId(2, 2015), new District(20),
                        LocalDateTime.parse("2016-10-10T12:30:35", formatter), "Chris Pratt"),
                new Incident(new IncidentId(3, 2013), new District(20),
                        LocalDateTime.parse("2016-10-10T12:30:35", formatter), "Chris Pratt"),
                new Incident(new IncidentId(12, 2015), new District(20),
                        LocalDateTime.parse("2016-10-10T12:30:35", formatter), "Chris Pratt")
//                new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("BBA2222F"),
//                        new District(6), new Availability("BUSY")),
//                new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("FKTH1221P"),
//                        new District(20), new Availability("AVAILABLE")),
//                new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("OLI4445C"),
//                        new District(8), new Availability("BUSY"))
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
