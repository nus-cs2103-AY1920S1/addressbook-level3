package seedu.address.testutil.activity;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Phone;
import seedu.address.model.field.Address;
import seedu.address.model.field.Name;
import seedu.address.model.itineraryitem.activity.Activity;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.contact.ContactBuilder;

/**
 * A utility class to help with building Contact objects.
 */
public class ActivityBuilder {

    public static final String DEFAULT_ACTIVITY_NAME = "Visit Golden Pavillion";
    public static final String DEFAULT_ACTIVITY_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_CONTACT_PHONE = "91170081";
    public static final String DEFAULT_CONTACT_EMAIL = "sam1987@gmail.com";

    private Name name;
    private Contact contact;
    private Address address;
    private Set<Tag> tags;

    public ActivityBuilder() {
        name = new Name(DEFAULT_ACTIVITY_NAME);
        contact = new Contact(new Name(DEFAULT_ACTIVITY_NAME), new Phone(DEFAULT_CONTACT_PHONE),
                new Email(DEFAULT_CONTACT_EMAIL), new Address(DEFAULT_ACTIVITY_ADDRESS), new HashSet<>());
        address = new Address(DEFAULT_ACTIVITY_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ActivityBuilder with the data of {@code activityToCopy}.
     */
    public ActivityBuilder(Activity activityToCopy) {
        name = activityToCopy.getName();
        address = activityToCopy.getAddress();
        contact = activityToCopy.getContact().orElse(null);
        tags = new HashSet<>(activityToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Activity} that we are building.
     */
    public ActivityBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Contact} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withContact(String name, String phone, String email, String address, String... tags) {
        this.contact = new ContactBuilder().withName(name).withEmail(email)
                .withPhone(phone).withAddress(address).withTags(tags).build();
        return this;
    }

    /**
     * Sets the {@code Contact} of the {@code Activity} that we are building.
     * @param contact new contact to be set.
     * @return
     */
    public ActivityBuilder withContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public Activity build() {
        return new Activity(name, address, contact, tags);
    }

}
