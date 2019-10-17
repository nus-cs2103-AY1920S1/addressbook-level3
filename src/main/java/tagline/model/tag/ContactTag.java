package tagline.model.tag;

import tagline.model.contact.ContactId;

/**
 * Tag a contact.
 */
public class ContactTag extends Tag {
    public static final String TAG_PREFIX = "@";

    private ContactId contactId;

    /**
     * Constructs a {@code ContactTag}.
     *
     * @param contactId A valid contactId
     */
    public ContactTag(ContactId contactId) {
        super(TagType.CONTACT_TAG);
        this.contactId = contactId;
    }

    /**
     * Constructs a {@code ContactTag} from storage.
     *
     * @param tagId     A valid tag id
     * @param contactId A valid contactId
     */
    public ContactTag(TagId tagId, ContactId contactId) {
        super(tagId, TagType.CONTACT_TAG);
        this.contactId = contactId;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other)
            && contactId.equals(((ContactTag) other).contactId);
    }

    @Override
    public String toString() {
        return TAG_PREFIX + contactId.toString();
    }
}
