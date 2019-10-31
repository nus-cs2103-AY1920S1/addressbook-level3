package seedu.planner.storage.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.model.ContactManager;
import seedu.planner.model.ReadOnlyContact;
import seedu.planner.model.contact.Contact;

/**
 * An Immutable Contact that is serializable to JSON format.
 */
@JsonRootName(value = "contactManager")
public class JsonSerializableContact {

    public static final String MESSAGE_DUPLICATE_CONTACT = "Contacts list contains duplicate contacts(s).";

    private final List<JsonAdaptedContact> contacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableContact} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableContact(@JsonProperty("contacts") List<JsonAdaptedContact> contacts) {
        this.contacts.addAll(contacts);
    }

    /**
     * Converts a given {@code ReadOnlyContact} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableContact}.
     */
    public JsonSerializableContact(ReadOnlyContact source) {
        contacts.addAll(source.getContactList().stream().map(JsonAdaptedContact::new).collect(Collectors.toList()));
    }

    /**
     * Converts these contact data into the model's {@code ContactManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ContactManager toModelType() throws IllegalValueException {
        ContactManager contact = new ContactManager();
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            Contact con = jsonAdaptedContact.toModelType();
            if (contact.hasContact(con)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CONTACT);
            }
            contact.addContact(con);
        }
        return contact;
    }

}
