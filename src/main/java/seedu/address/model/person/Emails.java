package seedu.address.model.person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a group of emails, possibly categorised into different types.
 */
public class Emails {

    private final HashMap<EmailType, List<Email>> emails = new HashMap<>();

    /**
     * Adds a new email.
     *
     * @param type the type of email to add.
     * @param email the email to add.
     * @return the email added.
     */
    public Email addEmail(EmailType type, Email email) {
        if (!emails.containsKey(type)) {
            // list doesn't exist, create and add
            emails.put(type, new ArrayList<>());
        }
        emails.get(type).add(email);
        return email;
    }

    /**
     * Gets a list of emails of specified type.
     *
     * @param type the type of emails to get.
     * @return null if the emails don't exist, the {@code Email} {@code List} otherwise.
     */
    public List<Email> getEmails(EmailType type) {
        return emails.get(type);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Emails // instanceof handles nulls
                && emails.equals(((Emails) other).emails)); // state check
    }

    @Override
    public int hashCode() {
        return emails.hashCode();
    }

}
