package tagline.model.contact;

/**
 * A utility class to help with building Contact objects.
 */
public class ContactBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DESCRIPTION = "";
    public static final int DEFAULT_ID = 0;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Description description;
    private ContactId contactId;

    public ContactBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        description = new Description(DEFAULT_DESCRIPTION);
        contactId = new ContactId(DEFAULT_ID);
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ContactBuilder(Contact contactToCopy) {
        name = contactToCopy.getName();
        phone = contactToCopy.getPhone();
        email = contactToCopy.getEmail();
        address = contactToCopy.getAddress();
        description = contactToCopy.getDescription();
        contactId = contactToCopy.getContactId();
    }

    /**
     * Sets the {@code Name} of the {@code Contact} that we are building.
     */
    public ContactBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Contact} that we are building.
     */
    public ContactBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Contact} that we are building.
     */
    public ContactBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Contact} that we are building.
     */
    public ContactBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Contact} that we are building.
     */
    public ContactBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code ContactId} of the {@code Contact} that we are building.
     */
    public ContactBuilder withId(long id) {
        this.contactId = new ContactId(id);
        return this;
    }

    /**
     * Creates a contact
     *
     * @return new Contact
     */
    public Contact build() {
        Contact contact = new Contact(name, phone, email, address, description);
        contact.setContactId(contactId);
        return contact;
    }

}
