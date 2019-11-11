package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DataBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.phone.Phone;

/**
 * An Immutable Phone DataBook that is serializable to JSON format.
 */
@JsonRootName(value = "phonebook")
class JsonSerializablePhoneBook {

    public static final String MESSAGE_DUPLICATE_PHONE = "phones list contains duplicate phone(s).";

    private final List<JsonAdaptedPhone> phones = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePhoneBook} with the given phones.
     */
    @JsonCreator
    public JsonSerializablePhoneBook(@JsonProperty("phones") List<JsonAdaptedPhone> phones) {
        this.phones.addAll(phones);
    }

    /**
     * Converts a given {@code ReadOnlyPhoneBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePhoneBook}.
     */
    public JsonSerializablePhoneBook(ReadOnlyDataBook<Phone> source) {
        phones.addAll(source.getList().stream().map(JsonAdaptedPhone::new).collect(Collectors.toList()));
    }

    /**
     * Converts this phone book into the model's {@code DataBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DataBook<Phone> toModelType() throws IllegalValueException {
        DataBook<Phone> phoneBook = new DataBook<>();
        for (JsonAdaptedPhone jsonAdaptedPhone : phones) {
            Phone phone = jsonAdaptedPhone.toModelType();
            if (phoneBook.has(phone)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PHONE);
            }
            phoneBook.add(phone);
        }
        return phoneBook;
    }

}
