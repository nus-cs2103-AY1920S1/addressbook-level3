package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.answerable.Answerable;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_ANSWERABLE = "Answerables list contains duplicate answerable(s).";

    private final List<JsonAdaptedAnswerable> answerables = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given answerables.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("answerables") List<JsonAdaptedAnswerable> answerables) {
        this.answerables.addAll(answerables);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        answerables.addAll(source.getAnswerableList().stream().map(JsonAdaptedAnswerable::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedAnswerable jsonAdaptedAnswerable : answerables) {
            Answerable answerable = jsonAdaptedAnswerable.toModelType();
            if (addressBook.hasAnswerable(answerable)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ANSWERABLE);
            }
            addressBook.addAnswerable(answerable);
        }
        return addressBook;
    }

}
