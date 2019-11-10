package dukecooks.testutil.profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.profile.UserProfile;
import dukecooks.model.profile.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalProfiles {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withGender("female")
            .withDoB("30/12/1985")
            .withBloodType("A+")
            .withWeight("50")
            .withHeight("165")
            .withMedicalHistories("high blood pressure").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withGender("male")
            .withDoB("03/01/1965")
            .withBloodType("B-")
            .withWeight("75")
            .withHeight("175")
            .withMedicalHistories("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withGender("male")
            .withDoB("14/04/1995")
            .withBloodType("AB+")
            .withWeight("80")
            .withHeight("185")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(CommandTestUtil.VALID_NAME_AMY)
            .withBloodType(CommandTestUtil.VALID_BLOODTYPE).withGender(CommandTestUtil.VALID_GENDER)
            .withDoB(CommandTestUtil.VALID_DOB).withWeight(CommandTestUtil.VALID_WEIGHT)
            .withHeight(CommandTestUtil.VALID_HEIGHT)
            .withMedicalHistories(CommandTestUtil.VALID_HISTORY_STROKE).build();
    public static final Person BOB = new PersonBuilder().withName(CommandTestUtil.VALID_NAME_BOB)
            .withMedicalHistories(CommandTestUtil.VALID_HISTORY_DENGUE, CommandTestUtil.VALID_HISTORY_STROKE)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalProfiles() {} // prevents instantiation

    /**
     * Returns an {@code UserProfile} with all the typical persons.
     */
    public static UserProfile getTypicalProfiles() {
        UserProfile ab = new UserProfile();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE));
    }
}
