//@@author wongsm7
package seedu.address.model.util;

import java.util.Arrays;
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
                "Antonio",
                "Willy"
        };
        int count = 1000000;
        /*
            @@author ajbrown
            Reused names from <a href="https://github.com/ajbrown/name-machine/tree/master/src/main/resources/org/
            ajbrown/namemachine"/> with several modifications.
        */
        Person[] listOfPersons = new Person[count];
        Random random = new Random();
        for (int idx = 0; idx < count; idx++) {

            String name = String.format("%s %s %s",
                    firstName[idx / (secondName.length * thirdName.length)],
                    secondName[(idx / thirdName.length) % secondName.length],
                    thirdName[idx % thirdName.length]);

            int randCount = idx % 9;
            if (randCount == 0) {
                listOfPersons[idx] = new Person(patientRefId(String.format("S%07d%s", idx + 1, "A")),
                        new Name(name), new Phone((random.nextInt(19999999) + 80000000 + "")),
                        new Email(name.replace(" ", "_") + "@example.com"),
                        new Address("Blk 1 Geylang Street " + (random.nextInt(99) + 1) + ", #06-12"),
                        getTagSet("diabetic"));
            } else if (randCount == 1) {
                listOfPersons[idx] = new Person(patientRefId(String.format("S%07d%s", idx + 1, "B")),
                        new Name(name), new Phone((random.nextInt(19999999) + 80000000 + "")),
                        new Email(name.replace(" ", "_") + "@example.com"),
                        new Address("Blk 2 Jurong Street " + (random.nextInt(99) + 1) + ", #06-10"),
                        getTagSet("asthma"));
            } else if (randCount == 2) {
                listOfPersons[idx] = new Person(patientRefId(String.format("S%07d%s", idx + 1, "C")),
                        new Name(name), new Phone((random.nextInt(19999999) + 80000000 + "")),
                        new Email(name.replace(" ", "_") + "@example.com"),
                        new Address("Blk 3 Raffles Street " + (random.nextInt(99) + 1) + ", #06-10"),
                        getTagSet());
            } else if (randCount == 3) {
                listOfPersons[idx] = new Person(patientRefId(String.format("S%07d%s", idx + 1, "D")),
                        new Name(name), new Phone((random.nextInt(19999999) + 80000000 + "")),
                        new Email(name.replace(" ", "_") + "@example.com"),
                        new Address("Blk 4 Tiong Bharu Street " + (random.nextInt(99) + 1) + ", #06-10"),
                        getTagSet());
            } else if (randCount == 4) {
                listOfPersons[idx] = new Person(patientRefId(String.format("S%07d%s", idx + 1, "E")),
                        new Name(name), new Phone((random.nextInt(19999999) + 80000000 + "")),
                        new Email(name.replace(" ", "_") + "@example.com"),
                        new Address("Blk 5 Red Hill Street " + (random.nextInt(99) + 1) + ", #06-10"),
                        getTagSet("highbloodpressure"));
            } else if (randCount == 5) {
                listOfPersons[idx] = new Person(patientRefId(String.format("S%07d%s", idx + 1, "F")),
                        new Name(name), new Phone((random.nextInt(19999999) + 80000000 + "")),
                        new Email(name.replace(" ", "_") + "@example.com"),
                        new Address("Blk 6 Clementi Street " + (random.nextInt(99) + 1) + ", #06-10"),
                        getTagSet("lowbloodsugar"));
            } else if (randCount == 6) {
                listOfPersons[idx] = new Person(patientRefId(String.format("S%07d%s", idx + 1, "G")),
                        new Name(name), new Phone((random.nextInt(19999999) + 80000000 + "")),
                        new Email(name.replace(" ", "_") + "@example.com"),
                        new Address("Blk 7 Changi Street " + (random.nextInt(99) + 1) + ", #06-10"),
                        getTagSet());
            } else if (randCount == 7) {
                listOfPersons[idx] = new Person(patientRefId(String.format("S%07d%s", idx + 1, "H")),
                        new Name(name), new Phone((random.nextInt(19999999) + 80000000 + "")),
                        new Email(name.replace(" ", "_") + "@example.com"),
                        new Address("Blk 8 Boon Lay Street " + (random.nextInt(99) + 1) + ", #06-10"),
                        getTagSet("tuberculosis"));
            } else if (randCount == 8) {
                listOfPersons[idx] = new Person(patientRefId(String.format("S%07d%s", idx + 1, "I")),
                        new Name(name), new Phone((random.nextInt(19999999) + 80000000 + "")),
                        new Email(name.replace(" ", "_") + "@example.com"),
                        new Address("Blk 9 Hougang Street " + (random.nextInt(99) + 1) + ", #06-10"),
                        getTagSet());
            } else {
                listOfPersons[idx] = new Person(patientRefId(String.format("S%07d%s", idx + 1, "J")),
                        new Name(name), new Phone((random.nextInt(19999999) + 80000000 + "")),
                        new Email(name.replace(" ", "_") + "@example.com"),
                        new Address("Blk 10 Tampines Street " + (random.nextInt(99) + 1) + ", #06-10"),
                        getTagSet());
            }
        }
        return listOfPersons;
    }

    public static Person[] getSampleStaffMember() {
        return new Person[]{
                new Person(staffRefId("S0111111A"), new Name("DR Alex Yeoh"), new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends")),
                new Person(staffRefId("S0222222B"), new Name("DR Bernice Yu"), new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends")),
                new Person(staffRefId("S0333333C"), new Name("DR Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours")),
                new Person(staffRefId("S0444444D"), new Name("DR David Li"), new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family")),
                new Person(staffRefId("S0555555E"), new Name("DR Irfan Ibrahim"), new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("classmates")),
                new Person(staffRefId("S0666666F"), new Name("DR Roy Balakrishnan"), new Phone("92624417"),
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
