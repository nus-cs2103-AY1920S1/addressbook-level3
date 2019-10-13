package organice.testutil;

import organice.model.person.Name;
import organice.model.person.Nric;
import organice.model.person.Person;
import organice.model.person.Phone;
import organice.model.person.Type;

/**
 * A utility class to help with building person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_NRIC = "S1111111A";
    public static final String DEFAULT_TYPE = "doctor";

    protected Name name;
    protected Phone phone;
    protected Nric nric;
    protected Type type;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        nric = new Nric(DEFAULT_NRIC);
        type = new Type(DEFAULT_TYPE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        nric = personToCopy.getNric();
        type = personToCopy.getType();
    }

    /**
     * Sets the {@code Type} of the {@code Person} that we are building.
     */
    public PersonBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public Person build() {
        return new Person(type, nric, name, phone);
    }

}
