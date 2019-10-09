package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParseExceptionHandler {

    public static final String CONTACT_SUGGESTION_HEADER = "You may want to contact ";

    /**
     * Returns an error message to inform the user of an invalid NRIC.
     * The contact number and/or email address is returned if present.
     * @param name name
     * @param phone phone
     * @param email email
     * @throws ParseException
     */
    public static void handleNricException(Name name, Phone phone, Email email) throws ParseException {
        StringBuilder exceptionMessage = new StringBuilder();
        StringBuilder suggestionMessage = new StringBuilder();
        exceptionMessage.append(Nric.MESSAGE_CONSTRAINTS);
        boolean isValidPhone = phone != null;
        boolean isValidEmail = email != null;
        if (isValidPhone) {
            suggestionMessage.append("Phone Number: " + phone.toString());
        }
        if (isValidEmail) {
            suggestionMessage.append("Email: " + email.toString());
        }
        boolean hasNoValidContact = suggestionMessage.length() == 0;
        if (hasNoValidContact) {
            throw new ParseException(exceptionMessage.toString());
        } else {
            String personName = name.fullName;
            throw new ParseException(exceptionMessage.toString() + CONTACT_SUGGESTION_HEADER + personName + "\n"
                    + suggestionMessage.toString());
        }
    }

    /**
     * Returns an error message to inform the user of an invalid contact number.
     * The email address is returned if present.
     * @param name name
     * @param email email
     * @throws ParseException parse exception
     */
    public static void handlePhoneException(Name name, Email email) throws ParseException {
        StringBuilder exceptionMessage = new StringBuilder();
        StringBuilder suggestionMessage = new StringBuilder();
        exceptionMessage.append(Phone.MESSAGE_CONSTRAINTS);
        boolean isValidEmail = email != null;
        if (isValidEmail) {
            suggestionMessage.append("Email: " + email.toString());
        }
        boolean hasNoValidContact = suggestionMessage.length() == 0;
        if (hasNoValidContact) {
            throw new ParseException(exceptionMessage.toString());
        } else {
            String personName = name.fullName;
            throw new ParseException(exceptionMessage.toString() + CONTACT_SUGGESTION_HEADER + personName + "\n"
                    + suggestionMessage.toString());
        }
    }

    /**
     * Returns an error message to inform the user of an invalid email address. The phone number is returned if present.
     * @param name name
     * @param phone phone
     * @throws ParseException parse exception
     */
    public static void handleEmailException(Name name, Phone phone) throws ParseException {
        StringBuilder exceptionMessage = new StringBuilder();
        StringBuilder suggestionMessage = new StringBuilder();
        exceptionMessage.append(Email.MESSAGE_CONSTRAINTS);
        boolean isValidPhone = phone != null;
        if (isValidPhone) {
            suggestionMessage.append("Phone: " + phone.toString());
        }
        boolean hasNoValidContact = suggestionMessage.length() == 0;
        if (hasNoValidContact) {
            throw new ParseException(exceptionMessage.toString());
        } else {
            String personName = name.fullName;
            throw new ParseException(exceptionMessage.toString() + CONTACT_SUGGESTION_HEADER + personName + "\n"
                    + suggestionMessage.toString());
        }
    }

}
