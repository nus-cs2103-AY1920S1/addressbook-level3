package seedu.address.model.field;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Phone;

import java.util.function.Predicate;

public class ContactContainsNumberPredicate implements Predicate<Contact> {
    private final Phone phone;

    public ContactContainsNumberPredicate(Phone phone) {
        this.phone = phone;
    }

    @Override
    public boolean test(Contact contact) {
        return contact.getPhone().equals(phone);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactContainsNumberPredicate // instanceof handles nulls
                && phone.equals(((ContactContainsNumberPredicate) other).phone)); // state check
    }
}
