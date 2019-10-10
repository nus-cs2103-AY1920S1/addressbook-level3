package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_IRENE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_JOHN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Donor;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withType("doctor").withNric("S1532142A")
            .withName("Alice Pauline").withPhone("94351253").build();
    public static final Person BENSON = new PersonBuilder().withType("doctor").withNric("T5231426Q")
            .withName("Benson Meier").withPhone("98765432").build();
    public static final Patient CARL = new PatientBuilder().withAge("22").withNric("G5642431P")
            .withName("Carl Kurz").withPhone("95352563").build();
    public static final Patient DANIEL = new PatientBuilder().withAge("34").withNric("F6423467F")
            .withName("Daniel Meier").withPhone("87652533").build();
    public static final Donor ELLE = new DonorBuilder().withAge("13").withNric("S9374923S")
            .withName("Elle Meyer").withPhone("9482224").build();
    public static final Donor FIONA = new DonorBuilder().withAge("25").withNric("F9183156L")
            .withName("Fiona Kunz").withPhone("9482427").build();
    public static final Donor GEORGE = new DonorBuilder().withAge("44").withNric("S1234567A")
            .withName("George Best").withPhone("9482442").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withType("doctor").withNric("G1245325A")
            .withName("Hoon Meier").withPhone("8482424").build();
    public static final Person IDA = new PersonBuilder().withType("doctor").withNric("T1125125L")
            .withName("Ida Mueller").withPhone("8482131").build();

    //Sample Patients
    public static final Patient IRENE = new PatientBuilder().withAge(VALID_AGE_IRENE).withName(VALID_NAME_IRENE)
        .withNric(VALID_NRIC_IRENE).withPhone(VALID_PHONE_IRENE).build();
    //Sample Donors
    public static final Donor JOHN = new DonorBuilder().withAge(VALID_AGE_JOHN).withName(VALID_NAME_JOHN)
            .withNric(VALID_NRIC_JOHN).withPhone(VALID_PHONE_JOHN).build();

    // Manually added - person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withType(VALID_TYPE_AMY).withNric(VALID_NRIC_AMY)
            .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).build();
    public static final Person BOB = new PersonBuilder().withType(VALID_TYPE_BOB).withNric(VALID_NRIC_BOB)
            .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

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
