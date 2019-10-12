package seedu.address.testutil;

import seedu.address.model.person.Doctor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Type;

/**
 * A utility class to help with building doctor objects.
 */
public class DoctorBuilder extends PersonBuilder {

    public DoctorBuilder() {
        super();
        type = new Type("doctor");
    }

    /**
     * Initializes the DoctorBuilder with the data of {@code personToCopy}.
     */
    public DoctorBuilder(Doctor doctorToCopy) {
        super(doctorToCopy);
    }

    /**
     * Sets the {@code Nric} of the {@code Doctor} that we are building.
     */
    @Override
    public DoctorBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }
    /**
     * Sets the {@code Name} of the {@code Doctor} that we are building.
     */
    @Override
    public DoctorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Doctor} that we are building.
     */
    @Override
    public DoctorBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public Doctor build() {
        return new Doctor(type, nric, name, phone);
    }

}
