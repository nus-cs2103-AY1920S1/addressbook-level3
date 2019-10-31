package tagline.testutil.tag;

import tagline.model.contact.ContactId;
import tagline.model.tag.ContactTag;

/**
 * A utility class to help with building Tag objects.
 */
public class ContactTagBuilder {

    public static final ContactId DEFAULT_CONTACT_ID = new ContactId("12345");

    private ContactId contactId;

    public ContactTagBuilder() {
        this.contactId = DEFAULT_CONTACT_ID;
    }

    /**
     * Sets the {@code ContactId} of {@code Tag} to be that we are building.
     */
    public ContactTagBuilder withContactId(String contactId) {
        this.contactId = new ContactId(contactId);
        return this;
    }

    public ContactTag build() {
        return new ContactTag(contactId);
    }
}
