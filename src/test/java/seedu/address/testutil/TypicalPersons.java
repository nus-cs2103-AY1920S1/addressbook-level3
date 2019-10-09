package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POLICY_HEALTH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SMOKER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    // TODO: Add back policies (removed for now to ease debugging process)
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withNric("S000001J").withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withDateOfBirth("12.12.1992").withPolicies("Health Insurance")
            .withTags("diabetic").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withNric("S000002J").withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withDateOfBirth("12.12.1922")
            .withPolicies("Health Insurance", "Life Insurance").withTags("smoker", "disabled").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withNric("S000003J")
            .withPhone("95352563").withEmail("heinz@example.com").withAddress("wall street")
            .withDateOfBirth("6.6.1996").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withNric("S000004J")
            .withPhone("87652533").withEmail("cornelia@example.com").withAddress("10th street")
            .withDateOfBirth("14.2.2019").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withNric("S000005J")
            .withPhone("94822247").withEmail("werner@example.com").withAddress("michegan ave")
            .withDateOfBirth("17.5.2000").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withNric("S000006J")
            .withPhone("94824279").withEmail("lydia@example.com").withAddress("little tokyo")
            .withDateOfBirth("15.8.2008").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withNric("S000007J")
            .withPhone("94824425").withEmail("anna@example.com").withAddress("4th street")
            .withDateOfBirth("5.5.2015").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withNric("S012345H")
            .withPhone("84824248").withEmail("stefan@example.com").withAddress("little india")
            .withDateOfBirth("22.4.1988").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withNric("T098765H")
            .withPhone("84821318").withEmail("hans@example.com").withAddress("chicago ave")
            .withDateOfBirth("16.7.2017").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withDateOfBirth(VALID_DATE_OF_BIRTH_AMY).withPolicies(VALID_POLICY_HEALTH)
            .withTags(VALID_TAG_DIABETIC).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).withPolicies(VALID_POLICY_HEALTH)
            .withTags(VALID_TAG_DIABETIC, VALID_TAG_SMOKER)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
