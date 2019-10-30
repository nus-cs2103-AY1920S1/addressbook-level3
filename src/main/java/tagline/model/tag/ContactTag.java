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
        super(TagIdGenerator.SHARED.getTagId(TagType.CONTACT_TAG, contactId.value.toString()));
        this.contactId = contactId;
    }

    /**
     * Constructs a {@code ContactTag} from storage.
     *
     * @param tagId     A valid tag id
     * @param contactId A valid contactId
     */
    public ContactTag(TagId tagId, ContactId contactId) {
        super(tagId);
        this.contactId = contactId;
    }

    public ContactId getContactId() {
        return contactId;
    }

    /**
     * Returns true if a tag matches the {@code tagType} and {@String content}.
     *
     * @param tagType Type of the tag.
     * @param content Content of the tag.
     * @return True if a tag matches the given parameters.
     */
    public boolean match(TagType tagType, String content) {
        return tagType == TagType.CONTACT_TAG
            && ContactId.isValidId(content)
            && contactId.equals(new ContactId(content));
    }

    @Override
    public String toString() {
        return TAG_PREFIX + contactId.toString();
    }
}
