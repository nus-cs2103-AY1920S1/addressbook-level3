package seedu.address.model.person;

import static seedu.address.model.person.EmailType.NUS;
import static seedu.address.model.person.EmailType.PERSONAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a group of emails, possibly categorised into different types.
 */
public class Emails {

    private final HashMap<EmailType, List<Email>> emails;

    public Emails() {
        this.emails = new HashMap<>();
    }

    public Emails(HashMap<EmailType, List<Email>> emails) {
        this();
        this.emails.putAll(emails);
    }

    /**
     * Adds a new personal email address.
     * @return the same Emails instance.
     */
    public Emails addPersonalEmail(Email email) {
        if (!emails.containsKey(PERSONAL)) {
            // list doesn't exist, create and add
            emails.put(PERSONAL, new ArrayList<>());
        }
        emails.get(PERSONAL).add(email);
        return this;
    }

    /**
     * Adds a new Nus email address.
     * @return the same Emails instance.
     */
    public Emails addNusEmail(Email email) {
        if (!emails.containsKey(NUS)) {
            // list doesn't exist, create and add
            emails.put(NUS, new ArrayList<>());
        }
        emails.get(NUS).add(email);
        return this;
    }

    /**
     * Gets a list of emails of specified type.
     *
     * @param type the type of emails to get.
     * @return null if the emails don't exist, the {@code Email} {@code List} otherwise.
     */
    public List<Email> getEmailsOfType(EmailType type) {
        return emails.get(type);
    }

    public HashMap<EmailType, List<Email>> getAllEmails() {
        return emails;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", PERSONAL, getEmailsOfType(PERSONAL), NUS, getEmailsOfType(NUS));
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
