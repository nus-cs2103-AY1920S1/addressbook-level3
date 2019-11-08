package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAmount("200").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAmount("300").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withAmount("330").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withAmount("340").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withAmount("399").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withAmount("400").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withAmount("500").build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
