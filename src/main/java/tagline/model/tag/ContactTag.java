//@@author stevenwjy
package tagline.model.tag;

import java.util.Objects;

import tagline.model.Model;
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
        super();
        this.contactId = contactId;
    }

    public ContactId getContactId() {
        return contactId;
    }

    @Override
    public boolean isValidInModel(Model model) {
        return model.findContact(contactId).isPresent();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || ((other instanceof ContactTag) // instanceof handles nulls
            && contactId.equals(((ContactTag) other).getContactId()));
    }

    @Override
    public String toString() {
        return TAG_PREFIX + contactId.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId);
    }
}
