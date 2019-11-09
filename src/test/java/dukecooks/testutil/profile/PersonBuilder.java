package dukecooks.testutil.profile;

import java.util.HashSet;
import java.util.Set;

import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.model.profile.person.BloodType;
import dukecooks.model.profile.person.DoB;
import dukecooks.model.profile.person.Gender;
import dukecooks.model.profile.person.Height;
import dukecooks.model.profile.person.Name;
import dukecooks.model.profile.person.Person;
import dukecooks.model.profile.person.Weight;
import dukecooks.model.util.SamplePersonDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_GENDER = "female";
    public static final String DEFAULT_DOB = "30/12/1985";
    public static final String DEFAULT_BLOODTYPE = "A+";
    public static final String DEFAULT_WEIGHT = "50";
    public static final String DEFAULT_HEIGHT = "165";
    public static final String DEFAULT_MEDICALHISTORY = "high blood pressure";

    private Name name;
    private Gender gender;
    private DoB dateOfBirth;
    private BloodType bloodGroup;
    private Weight weight;
    private Height height;
    private Set<MedicalHistory> medicalHistories;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        gender = new Gender(DEFAULT_GENDER);
        dateOfBirth = new DoB(DEFAULT_DOB);
        bloodGroup = new BloodType(DEFAULT_BLOODTYPE);
        weight = new Weight(DEFAULT_WEIGHT);
        height = new Height(DEFAULT_HEIGHT);
        medicalHistories = new HashSet<>();
        medicalHistories.add(new MedicalHistory(DEFAULT_MEDICALHISTORY));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        dateOfBirth = personToCopy.getDateOfBirth();
        gender = personToCopy.getGender();
        bloodGroup = personToCopy.getBloodType();
        weight = personToCopy.getWeight();
        height = personToCopy.getHeight();
        medicalHistories = new HashSet<>(personToCopy.getMedicalHistories());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Sets the {@code DoB} of the {@code Person} that we are building.
     */
    public PersonBuilder withDoB(String dateOfBirth) {
        this.dateOfBirth = new DoB(dateOfBirth);
        return this;
    }

    /**
     * Sets the {@code BloodType} of the {@code Person} that we are building.
     */
    public PersonBuilder withBloodType(String bloodGroup) {
        this.bloodGroup = new BloodType(bloodGroup);
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code Person} that we are building.
     */
    public PersonBuilder withWeight(String weight) {
        this.weight = new Weight(weight);
        return this;
    }

    /**
     * Sets the {@code height} of the {@code Person} that we are building.
     */
    public PersonBuilder withHeight(String height) {
        this.height = new Height(height);
        return this;
    }

    /**
     * Parses the {@code medicalHistories} into a {@code Set<MedicalHistory>}
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withMedicalHistories(String ... medicalHistories) {
        this.medicalHistories = SamplePersonDataUtil.getMedicalHistorySet(medicalHistories);
        return this;
    }

    /**
     * Generates a Person object for testing
     */
    public Person build() {
        return new Person(name, dateOfBirth, gender, bloodGroup,
                weight, height, medicalHistories);
    }

}
