package seedu.tarence.testutil;

import static seedu.tarence.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.tarence.model.Application;
import seedu.tarence.model.builder.PersonBuilder;
import seedu.tarence.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withEmail("heinz@example.com").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withEmail("cornelia@example.com").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withEmail("werner@example.com").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withEmail("lydia@example.com").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withEmail("stefan@example.com").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withEmail("hans@example.com").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code Application} with all the typical persons.
     */
    public static Application getTypicalApplication() {
        Application ab = new Application();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
