package seedu.system.testutil;
import static seedu.system.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.system.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.system.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.system.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.system.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.system.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.system.model.Data;
import seedu.system.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withDateOfBirth("01/01/2019").withGender("female").build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withDateOfBirth("01/02/2018").withGender("male").build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
        .withDateOfBirth("05/01/1992").withGender("male").build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
        .withDateOfBirth("17/11/1982").withGender("male").build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
        .withDateOfBirth("03/05/2010").withGender("female").build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
        .withDateOfBirth("01/10/2010").withGender("female").build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best")
        .withDateOfBirth("19/06/2019").withGender("male").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withDateOfBirth("07/07/2007").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withDateOfBirth("08/08/2008").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withDateOfBirth(VALID_DOB_AMY)
            .withGender(VALID_GENDER_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withDateOfBirth(VALID_DOB_BOB)
            .withGender(VALID_GENDER_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code Data} with all the typical persons.
     */
    public static Data<Person> getTypicalPersonData() {
        Data ab = new Data();
        for (Person person : getTypicalPersons()) {
            ab.addUniqueElement(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
