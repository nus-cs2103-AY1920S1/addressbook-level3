package seedu.jarvis.storage.history.commands.address;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.address.JsonAdaptedPerson;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link AddAddressCommand}.
 */
public class JsonAdaptedAddAddressCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_PERSON = "Invalid person.";

    private final JsonAdaptedPerson toAdd;

    /**
     * Constructs a {@code JsonAdaptedAddAddressCommand} with the given {@code Person} to add.
     *
     * @param toAdd {@code Person} in Json format.
     */
    @JsonCreator
    public JsonAdaptedAddAddressCommand(@JsonProperty("person") JsonAdaptedPerson toAdd) {
        this.toAdd = toAdd;
    }

    /**
     * Converts a given {@code AddAddressCommand} into this class for Jackson use.
     *
     * @param addAddressCommand {@code AddAddressCommand} to be used to construct the
     * {@code JsonAdaptedAddAddressCommand}.
     */
    public JsonAdaptedAddAddressCommand(AddAddressCommand addAddressCommand) {
        toAdd = new JsonAdaptedPerson(addAddressCommand.getPersonToAdd());
    }

    /**
     * Converts this Jackson-friendly adapted {@code AddAddressCommand} object into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code AddAddressCommand}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code AddAddressCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (toAdd == null) {
            throw new IllegalValueException(MESSAGE_INVALID_PERSON);
        }
        return new AddAddressCommand(toAdd.toModelType());
    }

}
