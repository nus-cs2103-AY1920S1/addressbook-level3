package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.*;
import seedu.address.model.Contact;
import seedu.address.model.ReadOnlyContact;

/**
 * An Immutable Contact that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableFinSec {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate contact(s).";

    private final List<JsonAdaptedContact> contacts = new ArrayList<>();
    private final List<JsonAdaptedClaim> claims = new ArrayList<>();
    private final List<JsonAdaptedIncome> incomes = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFinSec} with the given arraylists
     */
    @JsonCreator
    public JsonSerializableFinSec(@JsonProperty("persons") List<JsonAdaptedContact> contacts, @JsonProperty("") List<JsonAdaptedClaim> claims,
                                  @JsonProperty("") List<JsonAdaptedIncome> incomes) {
        this.contacts.addAll(contacts);
        this.claims.addAll(claims);
        this.incomes.addAll(incomes);
    }

    /**
     * Converts a given {@code ReadOnlyContact} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFinSec}.
     */
    public JsonSerializableFinSec(ReadOnlyContact source, ReadOnlyClaim source2, ReadOnlyIncome source3) {
        contacts.addAll(source.getContactList().stream().map(JsonAdaptedContact::new).collect(Collectors.toList()));
        claims.addAll(source2.getClaimList().stream().map(JsonAdaptedClaim::new).collect(Collectors.toList()));
    }


    /**
     * Converts this finsec into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Contact toModelType() throws IllegalValueException {
        Contact finSec = new Contact();
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            seedu.address.model.contact.Contact contact = jsonAdaptedContact.toModelType();
            if (finSec.hasPerson(contact)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            finSec.addPerson(contact);
        }
        return finSec;
    }

}
