//package seedu.address.testutil;
//
//import seedu.address.model.common.ReferenceId;
//
///**
// * A utility class to help with building Person objects.
// */
//public class EventBuilder {
//
//    public static final String REFERENCE_ID  = "001A";
//    public static final String DEFAULT_ID = "1234567A";
//    public static final String DEFAULT_ID = "1234567A";
//    public static final String DEFAULT_ID = "1234567A";
//
//
//
//    public static final String DEFAULT_ID = "1234567A";
//    public static final String DEFAULT_NAME = "Alice Pauline";
//    public static final String DEFAULT_PHONE = "85355255";
//    public static final String DEFAULT_EMAIL = "alice@gmail.com";
//    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
//
//    private ReferenceId id;
//    private Name name;
//    private Phone phone;
//    private Email email;
//    private Address address;
//    private Set<Tag> tags;
//
//    public PersonBuilder() {
//        id = new PatientReferenceId(DEFAULT_ID);
//        name = new Name(DEFAULT_NAME);
//        phone = new Phone(DEFAULT_PHONE);
//        email = new Email(DEFAULT_EMAIL);
//        address = new Address(DEFAULT_ADDRESS);
//        tags = new HashSet<>();
//    }
//
//    /**
//     * Initializes the PersonBuilder with the data of {@code personToCopy}.
//     */
//    public PersonBuilder(Person personToCopy) {
//        id = personToCopy.getReferenceId();
//        name = personToCopy.getName();
//        phone = personToCopy.getPhone();
//        email = personToCopy.getEmail();
//        address = personToCopy.getAddress();
//        tags = new HashSet<>(personToCopy.getTags());
//    }
//
//    /**
//     * Sets the {@code PatientReferenceId} of the {@code Person} that we are building.
//     */
//    public PersonBuilder withId(String id) {
//        this.id = new PatientReferenceId(id);
//        return this;
//    }
//    /**
//     * Sets the {@code Name} of the {@code Person} that we are building.
//     */
//    public PersonBuilder withName(String name) {
//        this.name = new Name(name);
//        return this;
//    }
//
//    /**
//     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
//     */
//    public PersonBuilder withTags(String ... tags) {
//        this.tags = SampleDataUtil.getTagSet(tags);
//        return this;
//    }
//
//    /**
//     * Sets the {@code Address} of the {@code Person} that we are building.
//     */
//    public PersonBuilder withAddress(String address) {
//        this.address = new Address(address);
//        return this;
//    }
//
//    /**
//     * Sets the {@code Phone} of the {@code Person} that we are building.
//     */
//    public PersonBuilder withPhone(String phone) {
//        this.phone = new Phone(phone);
//        return this;
//    }
//
//    /**
//     * Sets the {@code Email} of the {@code Person} that we are building.
//     */
//    public PersonBuilder withEmail(String email) {
//        this.email = new Email(email);
//        return this;
//    }
//
//    public Person build() {
//        return new Person(id, name, phone, email, address, tags);
//    }
//
//}
