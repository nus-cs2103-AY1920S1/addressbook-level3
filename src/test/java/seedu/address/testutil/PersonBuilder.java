package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.medical.MedicalHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";

    private Name name;
    private Set<MedicalHistory> medicalHistories;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        medicalHistories = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
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
     * Parses the {@code medicalHistories} into a {@code Set<MedicalHistory>}
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.medicalHistories = SampleDataUtil.getTagSet(tags);
        return this;
    }


    public Person build() {
        return new Person(name, medicalHistories);
    }

}
