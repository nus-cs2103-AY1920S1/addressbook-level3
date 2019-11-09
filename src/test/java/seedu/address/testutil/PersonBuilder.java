package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReferenceId;
import seedu.address.model.person.Person;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.PersonReferenceId;
import seedu.address.model.person.parameters.Phone;
import seedu.address.model.person.parameters.Tag;
import seedu.address.model.util.SamplePersonDataUtil;

/**
 * A utility class to help with building Person objects.
 * Uses a patient's {@code ReferenceId} by default
 */
public class PersonBuilder {

    public static final String DEFAULT_ID = "S1234567A";
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private ReferenceId id;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public PersonBuilder() {
        withPatientId(DEFAULT_ID);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        id = personToCopy.getReferenceId();
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the patient's {@code ReferenceId} of the {@code Person} that we are building.
     */
    public PersonBuilder withPatientId(String id) {
        try {
            this.id = PersonReferenceId.issuePatientReferenceId(id);
            return this;
        } catch (ParseException ex) {
            throw new AssertionError("Execution of command should not fail. " + ex.getMessage());
        }
    }

    /**
     * Sets the patient's {@code ReferenceId} of the {@code Person} that we are building.
     */
    public PersonBuilder withStaffId(String id) {
        try {
            this.id = PersonReferenceId.issueStaffReferenceId(id);
            return this;
        } catch (ParseException ex) {
            throw new AssertionError("Execution of command should not fail. " + ex.getMessage());
        }
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SamplePersonDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(id, name, phone, email, address, tags);
    }

}
