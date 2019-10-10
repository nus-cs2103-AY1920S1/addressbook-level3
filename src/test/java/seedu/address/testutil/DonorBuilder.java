package seedu.address.testutil;

import seedu.address.model.person.Age;
import seedu.address.model.person.Donor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Type;

/**
 * A utility class to build Donor objects.
 */
public class DonorBuilder extends PersonBuilder {
    public static final String DEFAULT_AGE = "60";

    private Age age;

    public DonorBuilder() {
        super();
        age = new Age(DEFAULT_AGE);
        type = new Type("donor");
    }

    /**
     * Initialises the DonorBuilder with the data of {@code donorToCopy}
     */
    public DonorBuilder(Donor donorToCopy) {
        super(donorToCopy);
        age = donorToCopy.getAge();
    }

    /**
     * Sets the {@code Age} of the {@code Donor} we are building
     */
    public DonorBuilder withAge(String age) {
        this.age = new Age(age);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Donor} that we are building.
     */
    @Override
    public DonorBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }
    /**
     * Sets the {@code Name} of the {@code Donor} that we are building.
     */
    @Override
    public DonorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Donor} that we are building.
     */
    @Override
    public DonorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public Donor build() {
        return new Donor(type, nric, name, phone, age);
    }
}
