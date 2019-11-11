package seedu.address.testutil;

import static seedu.address.commons.util.TimeUtil.ABOVE_SIXTYFIVE;
import static seedu.address.commons.util.TimeUtil.BELOW_TWENTY;
import static seedu.address.commons.util.TimeUtil.TWENTY_TO_SIXTYFOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SMOKER;
import static seedu.address.model.person.Gender.FEMALE;
import static seedu.address.model.person.Gender.MALE;
import static seedu.address.testutil.TypicalPolicy.FIRE_INSURANCE;
import static seedu.address.testutil.TypicalPolicy.HEALTH_INSURANCE;
import static seedu.address.testutil.TypicalPolicy.LIFE_INSURANCE;
import static seedu.address.testutil.TypicalPolicy.SENIOR_INSURANCE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.commons.util.PersonBuilder;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    // TODO: Change tag of alice and bension to meet policy req
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
        .withNric("S0000001J").withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
        .withPhone("94351253").withDateOfBirth("12.12.1982").withGender("Female")
        .withPolicies(HEALTH_INSURANCE, FIRE_INSURANCE)
        .withTags("diabetic", "high blood pressure", "public housing").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
        .withNric("S0000002J").withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432").withDateOfBirth("12.12.1942").withGender("Male")
        .withPolicies(LIFE_INSURANCE).withTags("smoker", "disabled", "high blood pressure").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withNric("S0000003J")
        .withPhone("95352563").withEmail("heinz@example.com").withAddress("wall street")
        .withDateOfBirth("6.6.1996").withGender("Male").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withNric("S0000004J")
        .withPhone("87652533").withEmail("cornelia@example.com").withAddress("10th street")
        .withDateOfBirth("14.2.2019").withGender("Male").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withNric("S0000005J")
        .withPhone("94822247").withEmail("werner@example.com").withAddress("michegan ave")
        .withDateOfBirth("17.5.2000").withGender("Male").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withNric("S0000006J")
        .withPhone("94824279").withEmail("lydia@example.com").withAddress("little tokyo")
        .withDateOfBirth("15.8.2008").withGender("Female").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withNric("S0000007J")
        .withPhone("94824425").withEmail("anna@example.com").withAddress("4th street")
        .withDateOfBirth("5.5.2015").withGender("Male").build();
    public static final Person ASYRAF = new PersonBuilder().withName("Asyraf")
        .withNric("S0000008J").withAddress("123, Jurong West Ave 6, #08-111").withEmail("asyraf@example.com")
        .withPhone("94351253").withDateOfBirth("12.12.1992").withGender("Male")
        .withPolicies(HEALTH_INSURANCE, FIRE_INSURANCE)
        .withTags("smoker", "diabetic", "public housing", "high blood pressure").build();
    public static final Person KEITH = new PersonBuilder().withName("Keith Tan")
        .withNric("S0000009J").withAddress("123, Jurong West Ave 6, #08-111").withEmail("asyraf@example.com")
        .withPhone("94351253").withDateOfBirth("12.12.1992").withGender("Male")
        .withPolicies(HEALTH_INSURANCE, FIRE_INSURANCE)
        .withTags("smoker", "diabetic", "public housing", "high blood pressure").build();
    public static final Person ROBIN = new PersonBuilder().withName("Robin")
        .withNric("S0000010J").withAddress("123, Jurong West Ave 6, #08-111").withEmail("asyraf@example.com")
        .withPhone("94351253").withDateOfBirth("12.12.1992").withGender("Male")
        .withPolicies(HEALTH_INSURANCE, FIRE_INSURANCE)
        .withTags("smoker", "diabetic", "public housing", "high blood pressure").build();
    public static final Person TAYYANG = new PersonBuilder().withName("Tay Yang")
        .withNric("S0000011J").withAddress("123, Jurong West Ave 6, #08-111").withEmail("asyraf@example.com")
        .withPhone("94351253").withDateOfBirth("12.12.1992").withGender("Male")
        .withPolicies(HEALTH_INSURANCE, FIRE_INSURANCE)
        .withTags("smoker", "diabetic", "public housing", "high blood pressure").build();
    public static final Person CHLOE = new PersonBuilder().withName("Chloe")
        .withNric("S0000012J").withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432").withDateOfBirth("12.12.1952").withGender("Female")
        .withPolicies(LIFE_INSURANCE).withTags("high blood pressure").build();
    public static final Person VICTORIA = new PersonBuilder().withName("Victoria")
        .withNric("S0000013J").withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432").withDateOfBirth("12.12.1952").withGender("Female")
        .withPolicies(LIFE_INSURANCE).withTags("high blood pressure").build();
    public static final Person NATASHA = new PersonBuilder().withName("Natasha")
        .withNric("S0000014J").withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432").withDateOfBirth("12.12.1952").withGender("Female")
        .withPolicies(LIFE_INSURANCE).withTags("high blood pressure").build();
    public static final Person RACHEL = new PersonBuilder().withName("Rachel")
        .withNric("S0000015J").withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432").withDateOfBirth("12.12.1932").withGender("Female")
        .withPolicies(SENIOR_INSURANCE).withTags("high blood pressure", "diabetic").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withNric("S0123456H")
        .withPhone("84824248").withEmail("stefan@example.com").withAddress("little india")
        .withDateOfBirth("22.4.1988").withGender("Female").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withNric("T0987656H")
        .withPhone("84821318").withEmail("hans@example.com").withAddress("chicago ave")
        .withDateOfBirth("16.7.2017").withGender("Male").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
        .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
        .withDateOfBirth(VALID_DATE_OF_BIRTH_AMY).withGender(VALID_GENDER_AMY)
        .withTags(VALID_TAG_DIABETIC).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB)
        .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
        .withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).withGender(VALID_GENDER_BOB)
        .withTags(VALID_TAG_DIABETIC, VALID_TAG_SMOKER)
        .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL,
            ELLE, FIONA, GEORGE, ASYRAF, KEITH, ROBIN, TAYYANG, CHLOE, VICTORIA, NATASHA, RACHEL));
    }

    public static Person getSinglePerson() {
        return ALICE;
    }

    public static ObservableMap<String, Integer> getTypicalPolicyPopularityBreakdown() {
        ObservableMap<String, Integer> result = FXCollections.observableHashMap();
        int totalTypicalHealthInsurance = 5;
        int totalTypicalLifeInsurance = 4;
        int totalTypicalFireInsurance = 5;
        int totalTypicalSeniorInsurance = 1;
        result.put(HEALTH_INSURANCE.getName().toString(), totalTypicalHealthInsurance);
        result.put(LIFE_INSURANCE.getName().toString(), totalTypicalLifeInsurance);
        result.put(FIRE_INSURANCE.getName().toString(), totalTypicalFireInsurance);
        result.put(SENIOR_INSURANCE.getName().toString(), totalTypicalSeniorInsurance);
        return result;
    }

    public static ObservableMap<String, Integer> getTypicalAgeGroupBreakdown() {
        ObservableMap<String, Integer> result = FXCollections.observableHashMap();
        int totalTypicalBelowTwenty = 4;
        int totalTypicalTwentyToSixtyFive = 6;
        int totalTypicalAboveSixtyFive = 5;
        result.put(BELOW_TWENTY, totalTypicalBelowTwenty);
        result.put(TWENTY_TO_SIXTYFOUR, totalTypicalTwentyToSixtyFive);
        result.put(ABOVE_SIXTYFIVE, totalTypicalAboveSixtyFive);
        return result;
    }

    public static ObservableMap<String, Integer> getTypicalGenderBreakdown() {
        ObservableMap<String, Integer> result = FXCollections.observableHashMap();
        int totalTypicalMale = 9;
        int totalTypicalFemale = 6;
        result.put(MALE, totalTypicalMale);
        result.put(FEMALE, totalTypicalFemale);
        return result;
    }
}
