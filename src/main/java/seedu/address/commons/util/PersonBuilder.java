package seedu.address.commons.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.policy.Policy;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "John Doe";
    public static final String DEFAULT_NRIC = "S0123456J";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "john@gmail.com";
    public static final String DEFAULT_DATE_OF_BIRTH = "3.3.1993";
    public static final String DEFAULT_GENDER = "Male";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Nric nric;
    private Phone phone;
    private Email email;
    private Address address;
    private DateOfBirth dateOfBirth;
    private Gender gender;
    private Set<Policy> policies;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        dateOfBirth = new DateOfBirth(DEFAULT_DATE_OF_BIRTH);
        gender = new Gender(DEFAULT_GENDER);
        policies = new HashSet<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        nric = personToCopy.getNric();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        dateOfBirth = personToCopy.getDateOfBirth();
        gender = personToCopy.getGender();
        policies = new HashSet<>(personToCopy.getPolicies());
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Initializes the PersonBuilder with the data of {@code policies and tags}.
     */
    public PersonBuilder(Set<Policy> policies, Set<Tag> tags) {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        dateOfBirth = new DateOfBirth(DEFAULT_DATE_OF_BIRTH);
        gender = new Gender(DEFAULT_GENDER);
        this.policies = new HashSet<>(policies);
        this.tags = new HashSet<>(tags);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
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
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Adds the {@code tags} to the {@code Person} that we are building.
     */
    public PersonBuilder addTags(List<Tag> tags) {
        this.tags.addAll(tags);
        return this;
    }

    /**
     * Removes the {@code tags} from the {@code Person} that we are building.
     */
    public PersonBuilder removeTags(List<Tag> tags) {
        this.tags.removeAll(tags);
        return this;
    }

    /**
     * Parses the {@code policies} into a {@code Set<Policy>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withPolicies(Policy ... policies) {
        this.policies = new HashSet<>(Arrays.asList(policies));
        return this;
    }

    /**
     * Parses the {@code policies} into a {@code Set<Tag>} and adds it to the {@code Person} that we are building.
     */
    public PersonBuilder addPolicies(Policy ... policies) {
        this.policies.addAll(Arrays.asList(policies));
        return this;
    }

    /**
     * Parses the {@code policies} into a {@code Set<Policy>} removes it from the policies of the {@code Person}
     * that we are building.
     */
    public PersonBuilder removePolicies(Policy ... policies) {
        this.policies.removeAll(Arrays.asList(policies));
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

    /**
     * Sets the {@code DateOfBirth} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = new DateOfBirth(dateOfBirth);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    public Person build() {
        return new Person(name, nric, phone, email, address, dateOfBirth, gender, policies, tags);
    }

}
