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
    private static Person admin =
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
            getTagSet("Admin", "Team-1", "OC"), new Username("Agent01"), new Password("password"));

    public static Person getAdmin() {
        return admin;
    }

    private static Person[] getSamplePersons() {
        return new Person[] {
            admin,
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                getTagSet("Team-1"), new Username("Agent02"), new Password("password")),
            new Person(new Name("Ahmed Bahajjaj"), new Phone("92334716"), new Email("ahmed@example.com"),
                    getTagSet("Team-1"), new Username("Agent-11"), new Password("password")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                getTagSet("Team-2"), new Username("Operator01"), new Password("password")),
            new Person(new Name("Vivien Lim"), new Phone("93120238"), new Email("vivien@example.com"),
                    getTagSet("Team-2", "OC"), new Username("Operator-12"), new Password("password")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                getTagSet("Team-2"), new Username("Operator02"), new Password("password")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                getTagSet("Team-3"), new Username("Agent03"), new Password("password")),
            new Person(new Name("Ahmad Ibrahim"), new Phone("93647591"), new Email("ahmad@example.com"),
                    getTagSet("Team-3", "OC"), new Username("Agent-13"), new Password("password")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                getTagSet("Team-3", "Admin"), new Username("Operator03"), new Password("password")),
            new Person(new Name("Alex Lim"), new Phone("98984545"), new Email("alexlim@example.com"),
                getTagSet("Team-4"), new Username("Operator04"), new Password("password")),
            new Person(new Name("John Doe"), new Phone("87654321"), new Email("john@example.com"),
                    getTagSet("Team-4", "Intern"), new Username("Operator-14"), new Password("password")),
            new Person(new Name("Jane Tan"), new Phone("97856342"), new Email("alexlim@example.com"),
                    getTagSet("Team-4", "OC"), new Username("Operator05"), new Password("password")),
        };
    }

    private static Incident[] getSampleIncidents() {
        Person[] samplePersons = getSamplePersons();
        IncidentDateTime[] sampleIncidentDateTimes = getSampleIncidentDateTimes();
        Vehicle[] sampleVehicles = getSampleVehicles();

        Incident[] sampleIncidents = new Incident[] {
            new Incident(samplePersons[6], new District(2), sampleIncidentDateTimes[0],
                    new IncidentId(sampleIncidentDateTimes[0].getMonth(), sampleIncidentDateTimes[0].getYear()),
                    new CallerNumber("98984545"), new Description("Fight reported at District 2 intersection"),
                    Incident.Status.COMPLETE_DRAFT, sampleVehicles[1]), // 01

            new Incident(samplePersons[0], new District(1), sampleIncidentDateTimes[1],
                new IncidentId(sampleIncidentDateTimes[1].getMonth(), sampleIncidentDateTimes[1].getYear()),
                new CallerNumber("93894576"), new Description("Shoplifting reported in District 1 Mall"),
                Incident.Status.SUBMITTED_REPORT, sampleVehicles[0]), // 02

            new Incident(samplePersons[0], new District(2), sampleIncidentDateTimes[2],
                new IncidentId(sampleIncidentDateTimes[2].getMonth(), sampleIncidentDateTimes[2].getYear()),
                new CallerNumber("98098765"), new Description("PMD accident in District 2 park"),
                Incident.Status.COMPLETE_DRAFT, sampleVehicles[19]), // 03

            new Incident(samplePersons[0], new District(3), sampleIncidentDateTimes[3],
                new IncidentId(sampleIncidentDateTimes[3].getMonth(), sampleIncidentDateTimes[3].getYear()),
                new CallerNumber("87595849"), new Description("Tiger spotted in District 3 courthouse"),
                Incident.Status.COMPLETE_DRAFT, sampleVehicles[2]), // 04

            new Incident(samplePersons[3], new District(4), sampleIncidentDateTimes[4],
                new IncidentId(sampleIncidentDateTimes[4].getMonth(), sampleIncidentDateTimes[4].getYear()),
                new CallerNumber("89090908"), new Description("Minor traffic accident in District 4 highway"),
                Incident.Status.SUBMITTED_REPORT, sampleVehicles[12]), // 05

            new Incident(samplePersons[11], new District(27), sampleIncidentDateTimes[6],
                new IncidentId(sampleIncidentDateTimes[6].getMonth(), sampleIncidentDateTimes[6].getYear()),
                new CallerNumber("00000000"), new Description("[TO BE FILLED]]"),
                    Incident.Status.INCOMPLETE_DRAFT, sampleVehicles[20]), // 06

            new Incident(samplePersons[0], new District(4), sampleIncidentDateTimes[7],
                new IncidentId(sampleIncidentDateTimes[7].getMonth(), sampleIncidentDateTimes[7].getYear()),
                new CallerNumber("00000000"), new Description("[TO BE FILLED]"),
                Incident.Status.INCOMPLETE_DRAFT, sampleVehicles[3]), // 07

            new Incident(samplePersons[9], new District(21), sampleIncidentDateTimes[8],
                    new IncidentId(sampleIncidentDateTimes[8].getMonth(), sampleIncidentDateTimes[8].getYear()),
                    new CallerNumber("00000000"), new Description("[TO BE FILLED]"),
                    Incident.Status.INCOMPLETE_DRAFT, sampleVehicles[21]), // 08

            new Incident(samplePersons[4], new District(5), sampleIncidentDateTimes[5],
                    new IncidentId(sampleIncidentDateTimes[5].getMonth(), sampleIncidentDateTimes[5].getYear()),
                    new CallerNumber("87656743"), new Description("Arson reported at District 5 warehouse"),
                    Incident.Status.COMPLETE_DRAFT, sampleVehicles[4]), // 09

            new Incident(samplePersons[0], new District(11), sampleIncidentDateTimes[9],
                    new IncidentId(sampleIncidentDateTimes[9].getMonth(), sampleIncidentDateTimes[9].getYear()),
                    new CallerNumber("00000000"), new Description("[TO BE FILLED]"),
                    Incident.Status.INCOMPLETE_DRAFT, sampleVehicles[22]), // 10

            new Incident(samplePersons[5], new District(23), sampleIncidentDateTimes[10],
                    new IncidentId(sampleIncidentDateTimes[10].getMonth(), sampleIncidentDateTimes[10].getYear()),
                    new CallerNumber("93340229"),
                    new Description("3 people injured in a barfight in Club Pomelo"),
                    Incident.Status.SUBMITTED_REPORT, sampleVehicles[9]), // 11

            new Incident(samplePersons[7], new District(13), sampleIncidentDateTimes[11],
                    new IncidentId(sampleIncidentDateTimes[11].getMonth(), sampleIncidentDateTimes[11].getYear()),
                    new CallerNumber("98330495"),
                    new Description("Four cats were stuck on a tree and had to be rescued"),
                    Incident.Status.SUBMITTED_REPORT, sampleVehicles[7]), // 12

            new Incident(samplePersons[10], new District(17), sampleIncidentDateTimes[12],
                    new IncidentId(sampleIncidentDateTimes[12].getMonth(), sampleIncidentDateTimes[12].getYear()),
                    new CallerNumber("78394893"),
                    new Description("Traffic accident involving two speeding cars at the District 17 junction"),
                    Incident.Status.SUBMITTED_REPORT, sampleVehicles[11]), // 13

            new Incident(samplePersons[0], new District(7), sampleIncidentDateTimes[13],
                    new IncidentId(sampleIncidentDateTimes[13].getMonth(), sampleIncidentDateTimes[13].getYear()),
                    new CallerNumber("89079609"),
                    new Description("Suicide attempt at condo 7, block 16 by a middle-aged man"),
                    Incident.Status.SUBMITTED_REPORT, sampleVehicles[10]), // 14

            new Incident(samplePersons[2], new District(15), sampleIncidentDateTimes[14],
                    new IncidentId(sampleIncidentDateTimes[14].getMonth(), sampleIncidentDateTimes[14].getYear()),
                    new CallerNumber("95049504"),
                    new Description("Cyclists crash into jaywalking pedestrian late at night at Dickson road"),
                    Incident.Status.SUBMITTED_REPORT, sampleVehicles[10]), // 15

            new Incident(samplePersons[7], new District(7), sampleIncidentDateTimes[15],
                    new IncidentId(sampleIncidentDateTimes[15].getMonth(), sampleIncidentDateTimes[15].getYear()),
                    new CallerNumber("77898977"),
                    new Description("High-speed chase along the pan-continental expressway ended in a crash"),
                    Incident.Status.SUBMITTED_REPORT, sampleVehicles[12]) // 16
        };

        return sampleIncidents;
    }

    private static IncidentDateTime[] getSampleIncidentDateTimes() {
        return new IncidentDateTime[] {
            new IncidentDateTime("2012-12-21T12:00:00"),
            new IncidentDateTime("2013-01-11T00:00:00"),
            new IncidentDateTime("2014-05-01T01:00:00"),
            new IncidentDateTime("2015-06-16T14:00:00"),
            new IncidentDateTime("2016-09-01T21:00:00"),
            new IncidentDateTime("2017-03-02T22:30:00"),
            new IncidentDateTime("2017-11-22T16:30:00"),
            new IncidentDateTime("2018-01-24T04:15:43"),
            new IncidentDateTime("2018-04-02T18:47:04"),
            new IncidentDateTime("2018-12-09T05:10:00"),
            new IncidentDateTime("2019-02-16T09:32:44"),
            new IncidentDateTime("2019-05-21T11:43:56"),
            new IncidentDateTime("2019-06-11T13:37:14"),
            new IncidentDateTime("2019-09-29T19:26:16"),
            new IncidentDateTime("2019-11-23T21:53:07"),
            new IncidentDateTime("2019-12-01T23:14:10"),
        };
    }

    /**
     * The assigned vehicles are of "busy" status here because
     * it's just loading of data; they were alr dispatched.
     */
    public static Vehicle[] getSampleVehicles() {
        return new Vehicle[] {
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("SFD3204V"),
                    new District(1), new Availability("BUSY")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("SDF2044R"),
                    new District(2), new Availability("BUSY")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("FEP3249J"),
                    new District(3), new Availability("BUSY")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("GRB3294K"),
                    new District(4), new Availability("BUSY")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("GJR8923L"),
                    new District(5), new Availability("BUSY")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("SGS2121G"),
                    new District(1), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("BBA2222F"),
                    new District(6), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("FKH1221P"),
                    new District(20), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("OLI4445C"),
                    new District(8), new Availability("AVAILABLE")),

            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("SFD3204W"),
                    new District(1), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("SDG4044R"),
                    new District(2), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("FEG5249J"),
                    new District(3), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("GJE3294K"),
                    new District(4), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("GJN8923L"),
                    new District(5), new Availability("AVAILABLE")),

            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("SFD3264V"),
                    new District(6), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("SDF2344R"),
                    new District(7), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("FEP6249J"),
                    new District(8), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("GRB3994K"),
                    new District(9), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("GJR8123L"),
                    new District(10), new Availability("AVAILABLE")),

            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("POP4432L"),
                    new District(2), new Availability("BUSY")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("JFK4039D"),
                    new District(27), new Availability("BUSY")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("HFJ3029F"),
                    new District(21), new Availability("BUSY")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("HFJ3023F"),
                    new District(11), new Availability("BUSY")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("KLM1209A"),
                   new District(12), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("KLM1208G"),
                   new District(2), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("SGX9213A"),
                    new District(4), new Availability("BUSY")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("SFD1128J"),
                    new District(7), new Availability("BUSY")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("SFD0921K"),
                   new District(3), new Availability("BUSY")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("SFD1108R"),
                    new District(22), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("GRB1256P"),
                    new District(27), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("JFJ5632Q"),
                    new District(18), new Availability("BUSY")),
            new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("KML5402E"),
                    new District(14), new Availability("AVAILABLE")),
            new Vehicle(new VehicleType("Patrol Car"), new VehicleNumber("KLM5306G"),
                    new District(8), new Availability("AVAILABLE")),
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
