package seedu.address.testutil.accommodation;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.contact.Contact;
import seedu.address.model.field.Address;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.contact.ContactBuilder;

/**
 * A utility class to help with building Accommodation objects.
 */
public class AccommodationBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline Home";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    public static final String DEFAULT_ACCOMMODATION_PHONE = "91170081";
    public static final String DEFAULT_ACCOMMODATION_EMAIL = "sam1987@gmail.com";

    public static final String[] DEFAULT_ACCOMMODATION_TAGS = {"Jurong", "Cool", "Cheap"};

    private Name name;
    private Address address;
    private Contact contact;
    private Set<Tag> tags;

    public AccommodationBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        contact = new ContactBuilder().withName(DEFAULT_NAME).withPhone(DEFAULT_ACCOMMODATION_PHONE)
                .withEmail(DEFAULT_ACCOMMODATION_EMAIL).withAddress(DEFAULT_ADDRESS).build();
        tags = SampleDataUtil.getTagSet(DEFAULT_ACCOMMODATION_TAGS);
    }

    /**
     * Initializes the AccommodationBuilder with the data of {@code accommodationToCopy}.
     */
    public AccommodationBuilder(Accommodation accommodationToCopy) {
        name = accommodationToCopy.getName();
        address = accommodationToCopy.getAddress();
        contact = accommodationToCopy.getContact().orElse(null);
        tags = new HashSet<>(accommodationToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Accommodation} that we are building.
     */
    public AccommodationBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Accommodation} that we are building.
     */
    public AccommodationBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Contact} of the {@code Accommodation} that we are building.
     */
    public AccommodationBuilder withContact(String name, String phone, String email, String address, String... tags) {
        this.contact = new ContactBuilder().withName(name).withEmail(email)
                .withPhone(phone).withAddress(address).withTags(tags).build();
        return this;
    }

    /**
     * Sets the {@code Contact} of the {@code Accommodation} that we are building.
     * @param contact new contact to be set.
     * @return
     */
    public AccommodationBuilder withContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Accommodation} that we are building.
     */
    public AccommodationBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Accommodation build() {
        return new Accommodation(name, address, contact, tags);
    }

}
