package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Itinerary;
import seedu.address.model.ReadOnlyItinerary;
import seedu.address.model.contact.Contact;

/**
 * An Immutable Itinerary that is serializable to JSON format.
 */
@JsonRootName(value = "itinerary")
class JsonSerializableItinerary {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate contacts(s).";

    private final List<JsonAdaptedContact> contacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableItinerary(@JsonProperty("contacts") List<JsonAdaptedContact> contacts) {
        this.contacts.addAll(contacts);
    }

    /**
     * Converts a given {@code ReadOnlyItinerary} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableItinerary(ReadOnlyItinerary source) {
        contacts.addAll(source.getContactList().stream().map(JsonAdaptedContact::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Itinerary} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Itinerary toModelType() throws IllegalValueException {
        Itinerary itinerary = new Itinerary();
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            Contact contact = jsonAdaptedContact.toModelType();
            if (itinerary.hasContact(contact)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            itinerary.addContact(contact);
        }
        return itinerary;
    }

}
