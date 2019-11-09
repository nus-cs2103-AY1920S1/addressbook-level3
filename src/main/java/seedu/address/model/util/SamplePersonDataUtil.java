package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReferenceId;
import seedu.address.model.person.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.Phone;
import seedu.address.model.person.parameters.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SamplePersonDataUtil {

    /**
     * Parses the {@refId} as a patient's {@code ReferenceId}.
     */
    private static ReferenceId patientRefId(String refId) {
        try {
            return ParserUtil.issuePatientReferenceId(refId);
        } catch (ParseException ex) {
            throw new AssertionError("Error should be thrown from sample test data: " + ex.getMessage());
        }
    }

    /**
     * Parses the {@refId} as a staff's {@code ReferenceId}.
     */
    private static ReferenceId staffRefId(String refId) {
        try {
            return ParserUtil.issueStaffReferenceId(refId);
        } catch (ParseException ex) {
            throw new AssertionError("Error should be thrown from sample test data: " + ex.getMessage());
        }
    }

    public static Person[] getSamplePersons() {
        String[] firstName = {"Smith",
                "Johnson",
                "Williams",
                "Brown",
                "Jones",
                "Miller",
                "Davis",
                "Garcia",
                "Rodriguez",
                "Wilson",
                "Martinez",
                "Anderson",
                "Taylor",
                "Thomas",
                "Hernandez",
                "Moore",
                "Martin",
                "Jackson",
                "Thompson",
                "White",
                "Lopez",
                "Lee",
                "Gonzalez",
                "Harris",
                "Clark",
                "Lewis",
                "Robinson",
                "Walker",
                "Perez",
                "Hall",
                "Young",
                "Allen",
                "Sanchez",
                "Wright",
                "King",
                "Scott",
                "Green",
                "Baker",
                "Adams",
                "Nelson",
                "Hill",
                "Ramirez",
                "Campbell",
                "Mitchell",
                "Roberts",
                "Carter",
                "Phillips",
                "Evans",
                "Turner",
                "Torres",
                "Parker",
                "Collins",
                "Edwards",
                "Stewart",
                "Flores",
                "Morris",
                "Nguyen",
                "Murphy",
                "Rivera",
                "Cook",
                "Rogers",
                "Morgan",
                "Peterson",
                "Cooper",
                "Reed",
                "Bailey",
                "Bell",
                "Gomez",
                "Kelly",
                "Howard",
                "Ward",
                "Cox",
                "Diaz",
                "Richardson",
                "Wood",
                "Watson",
                "Brooks",
                "Bennett",
                "Gray",
                "James",
                "Reyes",
                "Cruz",
                "Hughes",
                "Price",
                "Myers",
                "Long",
                "Foster",
                "Sanders",
                "Ross",
                "Morales",
                "Powell",
                "Sullivan",
                "Russell",
                "Ortiz",
                "Jenkins",
                "Gutierrez",
                "Perry",
                "Butler",
                "Barnes",
                "Fisher"
        };
        String[] secondName = {"Mary",
                "Patricia",
                "Linda",
                "Barbara",
                "Elizabeth",
                "Jennifer",
                "Maria",
                "Susan",
                "Margaret",
                "Dorothy",
                "Lisa",
                "Nancy",
                "Karen",
                "Betty",
                "Helen",
                "Sandra",
                "Donna",
                "Carol",
                "Ruth",
                "Sharon",
                "Michelle",
                "Laura",
                "Sarah",
                "Kimberly",
                "Deborah",
                "Jessica",
                "Shirley",
                "Cynthia",
                "Angela",
                "Melissa",
                "Brenda",
                "Amy",
                "Anna",
                "Rebecca",
                "Virginia",
                "Kathleen",
                "Pamela",
                "Martha",
                "Debra",
                "Amanda",
                "Stephanie",
                "Carolyn",
                "Christine",
                "Marie",
                "Janet",
                "Catherine",
                "Frances",
                "Ann",
                "Joyce",
                "Diane",
                "Alice",
                "Julie",
                "Heather",
                "Teresa",
                "Doris",
                "Gloria",
                "Evelyn",
                "Jean",
                "Cheryl",
                "Mildred",
                "Katherine",
                "Joan",
                "Ashley",
                "Judith",
                "Rose",
                "Janice",
                "Kelly",
                "Nicole",
                "Judy",
                "Christina",
                "Kathy",
                "Theresa",
                "Beverly",
                "Denise",
                "Tammy",
                "Irene",
                "Jane",
                "Lori",
                "Rachel",
                "Marilyn",
                "Andrea",
                "Kathryn",
                "Louise",
                "Sara",
                "Anne",
                "Jacqueline",
                "Wanda",
                "Bonnie",
                "Julia",
                "Ruby",
                "Lois",
                "Tina",
                "Phyllis",
                "Norma",
                "Paula",
                "Diana",
                "Annie",
                "Lillian",
                "Emily",
                "Robin"
        };
        String[] thirdName = {"James",
                "John",
                "Robert",
                "Michael",
                "William",
                "David",
                "Richard",
                "Charles",
                "Joseph",
                "Thomas",
                "Christopher",
                "Daniel",
                "Paul",
                "Mark",
                "Donald",
                "George",
                "Kenneth",
                "Steven",
                "Edward",
                "Brian",
                "Ronald",
                "Anthony",
                "Kevin",
                "Jason",
                "Matthew",
                "Gary",
                "Timothy",
                "Jose",
                "Larry",
                "Jeffrey",
                "Frank",
                "Scott",
                "Eric",
                "Stephen",
                "Andrew",
                "Raymond",
                "Gregory",
                "Joshua",
                "Jerry",
                "Dennis",
                "Walter",
                "Patrick",
                "Peter",
                "Harold",
                "Douglas",
                "Henry",
                "Carl",
                "Arthur",
                "Ryan",
                "Roger",
                "Joe",
                "Juan",
                "Jack",
                "Albert",
                "Jonathan",
                "Justin",
                "Terry",
                "Gerald",
                "Keith",
                "Samuel",
                "Willie",
                "Ralph",
                "Lawrence",
                "Nicholas",
                "Roy",
                "Benjamin",
                "Bruce",
                "Brandon",
                "Adam",
                "Harry",
                "Fred",
                "Wayne",
                "Billy",
                "Steve",
                "Louis",
                "Jeremy",
                "Aaron",
                "Randy",
                "Howard",
                "Eugene",
                "Carlos",
                "Russell",
                "Bobby",
                "Victor",
                "Martin",
                "Ernest",
                "Phillip",
                "Todd",
                "Jesse",
                "Craig",
                "Alan",
                "Shawn",
                "Clarence",
                "Sean",
                "Philip",
                "Chris",
                "Johnny",
                "Earl",
                "Jimmy",
                "Antonio"
        };
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    names.add(firstName[i] + " " + secondName[j] + " " + thirdName[k]);
                }
            }
        }
        int count = 1000000;
        Person[] listOfPersons = new Person[count];
        for (int a = 0; a < count; a++) {
            if (a % 10 == 0) {
                listOfPersons[a] = new Person(patientRefId(String.format("%04d%s", a, "1A")),
                        new Name(names.get(a)), new Phone((new Random().nextInt(19999999) + 80000000 + "")),
                        new Email(names.get(a).replace(" ", "_") + "@example.com"),
                        new Address("Blk 1 Geylang Street " + (new Random().nextInt(99) + 1) + ", #06-12"),
                        getTagSet("diabetic"));
            } else if (a % 10 == 1) {
                listOfPersons[a] = new Person(patientRefId(String.format("%04d%s", a, "1A")),
                        new Name(names.get(a)), new Phone((new Random().nextInt(19999999) + 80000000 + "")),
                        new Email(names.get(a).replace(" ", "_") + "@example.com"),
                        new Address("Blk 2 Jurong Street " + (new Random().nextInt(99) + 1) + ", #06-10"),
                        getTagSet("asthma"));
            } else if (a % 10 == 2) {
                listOfPersons[a] = new Person(patientRefId(String.format("%04d%s", a, "1A")),
                        new Name(names.get(a)), new Phone((new Random().nextInt(19999999) + 80000000 + "")),
                        new Email(names.get(a).replace(" ", "_") + "@example.com"),
                        new Address("Blk 3 Raffles Street " + (new Random().nextInt(99) + 1) + ", #06-10"),
                        getTagSet("stroke"));
            } else if (a % 10 == 3) {
                listOfPersons[a] = new Person(patientRefId(String.format("%04d%s", a, "1A")),
                        new Name(names.get(a)), new Phone((new Random().nextInt(19999999) + 80000000 + "")),
                        new Email(names.get(a).replace(" ", "_") + "@example.com"),
                        new Address("Blk 4 Tiong Bharu Street " + (new Random().nextInt(99) + 1) + ", #06-10"),
                        getTagSet("kidneyfailure"));
            } else if (a % 10 == 4) {
                listOfPersons[a] = new Person(patientRefId(String.format("%04d%s", a, "1A")),
                        new Name(names.get(a)), new Phone((new Random().nextInt(19999999) + 80000000 + "")),
                        new Email(names.get(a).replace(" ", "_") + "@example.com"),
                        new Address("Blk 5 Red Hill Street " + (new Random().nextInt(99) + 1) + ", #06-10"),
                        getTagSet("highbloodpressure"));
            } else if (a % 10 == 5) {
                listOfPersons[a] = new Person(patientRefId(String.format("%04d%s", a, "1A")),
                        new Name(names.get(a)), new Phone((new Random().nextInt(19999999) + 80000000 + "")),
                        new Email(names.get(a).replace(" ", "_") + "@example.com"),
                        new Address("Blk 6 Clementi Street " + (new Random().nextInt(99) + 1) + ", #06-10"),
                        getTagSet("lowbloodsugar"));
            } else if (a % 10 == 6) {
                listOfPersons[a] = new Person(patientRefId(String.format("%04d%s", a, "1A")),
                        new Name(names.get(a)), new Phone((new Random().nextInt(19999999) + 80000000 + "")),
                        new Email(names.get(a).replace(" ", "_") + "@example.com"),
                        new Address("Blk 7 Changi Street " + (new Random().nextInt(99) + 1) + ", #06-10"),
                        getTagSet("dengue"));
            } else if (a % 10 == 7) {
                listOfPersons[a] = new Person(patientRefId(String.format("%04d%s", a, "1A")),
                        new Name(names.get(a)), new Phone((new Random().nextInt(19999999) + 80000000 + "")),
                        new Email(names.get(a).replace(" ", "_") + "@example.com"),
                        new Address("Blk 8 Boon Lay Street " + (new Random().nextInt(99) + 1) + ", #06-10"),
                        getTagSet("tuberculosis"));
            } else if (a % 10 == 8) {
                listOfPersons[a] = new Person(patientRefId(String.format("%04d%s", a, "1A")),
                        new Name(names.get(a)), new Phone((new Random().nextInt(19999999) + 80000000 + "")),
                        new Email(names.get(a).replace(" ", "_") + "@example.com"),
                        new Address("Blk 9 Hougang Street " + (new Random().nextInt(99) + 1) + ", #06-10"),
                        getTagSet("heart"));
            } else {
                listOfPersons[a] = new Person(patientRefId(String.format("%04d%s", a, "1A")),
                        new Name(names.get(a)), new Phone((new Random().nextInt(19999999) + 80000000 + "")),
                        new Email(names.get(a).replace(" ", "_") + "@example.com"),
                        new Address("Blk 10 Tampines Street " + (new Random().nextInt(99) + 1) + ", #06-10"),
                        getTagSet("cancer"));
            }
        }
        return listOfPersons;
    }

    public static Person[] getSampleStaffMember() {
        return new Person[]{
                new Person(staffRefId("S001A"), new Name("DR Alex Yeoh"), new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends")),
                new Person(staffRefId("S002B"), new Name("DR Bernice Yu"), new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends")),
                new Person(staffRefId("S003C"), new Name("DR Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours")),
                new Person(staffRefId("S004D"), new Name("DR David Li"), new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family")),
                new Person(staffRefId("S005E"), new Name("DR Irfan Ibrahim"), new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("classmates")),
                new Person(staffRefId("S006F"), new Name("DR Roy Balakrishnan"), new Phone("92624417"),
                        new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyAddressBook getSampleStaffAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSampleStaffMember()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }


    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::issueTag)
                .collect(Collectors.toUnmodifiableSet());
    }

}
