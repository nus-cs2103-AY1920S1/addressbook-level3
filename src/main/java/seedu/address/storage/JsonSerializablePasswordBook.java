package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PasswordBook;
import seedu.address.model.password.Password;


/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "passwordbook")
class JsonSerializablePasswordBook {
    private final List<JsonAdaptedPassword> passwords = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializablePasswordBook(@JsonProperty("passwords") List<JsonAdaptedPassword> passwords) {
        this.passwords.addAll(passwords);
    }

    /**
     * Converts a given {@code PasswordBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePasswordBook}.
     */
    public JsonSerializablePasswordBook(PasswordBook source) {
        passwords.addAll(source.getPasswordList().stream().map(JsonAdaptedPassword::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code PasswordBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PasswordBook toModelType() throws IllegalValueException {
        PasswordBook passwordBook = new PasswordBook();
        for (JsonAdaptedPassword jsonAdaptedPassword : passwords) {
            Password password = jsonAdaptedPassword.toModelType();
            //if (addressBook.hasPerson(person)) {
            //    throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            //}
            passwordBook.addPassword(password);
        }
        return passwordBook;
    }
}
