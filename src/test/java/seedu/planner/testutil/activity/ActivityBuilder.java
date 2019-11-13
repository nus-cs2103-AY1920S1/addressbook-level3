package seedu.planner.testutil.activity;

import java.util.HashSet;
import java.util.Set;

import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.Duration;
import seedu.planner.model.activity.Priority;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.contact.Email;
import seedu.planner.model.contact.Phone;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Cost;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;
import seedu.planner.model.util.SampleDataUtil;
import seedu.planner.testutil.contact.ContactBuilder;

/**
 * An utility class to help with building Activity objects.
 */
public class ActivityBuilder {

    public static final String DEFAULT_ACTIVITY_NAME = "Visit Golden Pavillion";
    public static final String DEFAULT_ACTIVITY_ADDRESS = "123, Jurong West Ave 6, #08-111";

    public static final String DEFAULT_CONTACT_NAME = "Sam Smith";
    public static final String DEFAULT_CONTACT_PHONE = "91170081";
    public static final String DEFAULT_CONTACT_EMAIL = "sam1987@gmail.com";
    public static final String DEFAULT_CONTACT_ADDRESS = "456, Bukit Batok st 53, #03-21";
    public static final Integer DEFAULT_DURATION = 120;
    public static final Integer DEFAULT_PRIORITY = 1;
    public static final String DEFAULT_COST = "1.00";

    private Name name;
    private Contact contact;
    private Address address;
    private Cost cost;
    private Set<Tag> tags;
    private Duration duration;
    private Priority priority;

    public ActivityBuilder() {
        name = new Name(DEFAULT_ACTIVITY_NAME);
        contact = new Contact(new Name(DEFAULT_CONTACT_NAME), new Phone(DEFAULT_CONTACT_PHONE),
                new Email(DEFAULT_CONTACT_EMAIL), new Address(DEFAULT_CONTACT_ADDRESS), new HashSet<>());
        address = new Address(DEFAULT_ACTIVITY_ADDRESS);
        cost = new Cost(DEFAULT_COST);
        tags = new HashSet<>();
        duration = new Duration(DEFAULT_DURATION);
        priority = new Priority(DEFAULT_PRIORITY);
    }

    /**
     * Initializes the ActivityBuilder with the data of {@code activityToCopy}.
     */
    public ActivityBuilder(Activity activityToCopy) {
        name = activityToCopy.getName();
        address = activityToCopy.getAddress();
        contact = activityToCopy.getContact().orElse(null);
        cost = activityToCopy.getCost().orElse(null);
        tags = new HashSet<>(activityToCopy.getTags());
        duration = activityToCopy.getDuration();
        priority = activityToCopy.getPriority();
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
     *
     * @param contact new contact to be set.
     * @return
     */
    public ActivityBuilder withContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    /**
     * Sets the {@code Cost} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withCost(String cost) {
        this.cost = new Cost(cost);
        return this;
    }

    /**
     * Sets the {@code duration} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withDuration(String duration) {
        this.duration = new Duration(Integer.parseInt(duration));
        return this;
    }

    /**
     * Sets the {@code priority} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withPriority(String priority) {
        this.priority = new Priority(Integer.parseInt(priority));
        return this;
    }

    public Activity build() {
        return new Activity(name, address, contact, cost, tags, duration, priority);
    }

}
