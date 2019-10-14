package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_DENGUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HISTORY_STROKE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.profile.DukeCooks;
import seedu.address.profile.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withGender("female")
            .withDoB("30/12/1985")
            .withBloodType("A+")
            .withWeight("50", "30/08/2019 1200")
            .withHeight("165", "30/08/2019 1200")
            .withMedicalHistories("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withGender("male")
            .withDoB("03/01/1965")
            .withBloodType("B-")
            .withWeight("75", "30/08/2019 1200")
            .withHeight("175", "30/08/2019 1200")
            .withMedicalHistories("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withGender("male")
            .withDoB("14/04/1995")
            .withBloodType("AB+")
            .withWeight("80", "30/08/2019 1200")
            .withHeight("185", "30/08/2019 1200")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withMedicalHistories(VALID_HISTORY_STROKE).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withMedicalHistories(VALID_HISTORY_DENGUE, VALID_HISTORY_STROKE)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code DukeCooks} with all the typical persons.
     */
    public static DukeCooks getTypicalDukeCooks() {
        DukeCooks ab = new DukeCooks();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL));
    }
}
