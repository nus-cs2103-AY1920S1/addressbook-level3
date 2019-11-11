// @@author yehezkiel01

package tagline.model.contact;

import java.util.function.Predicate;

/**
 * Tests that a {@code Contact}'s {@code ContactId} matches the {@code ContactId} given.
 */
public class ContactIdEqualsSearchIdPredicate implements Predicate<Contact> {

    private final ContactId contactId;

    public ContactIdEqualsSearchIdPredicate(ContactId contactId) {
        this.contactId = contactId;
    }

    @Override
    public boolean test(Contact contact) {
        return contact.getContactId().equals(contactId);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ContactIdEqualsSearchIdPredicate // instanceof handles nulls
            && contactId.equals(((ContactIdEqualsSearchIdPredicate) other).contactId)); // state check
    }
}
