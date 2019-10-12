package seedu.jarvis.storage.history.commands.address;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.storage.address.JsonAdaptedPerson;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Jackson-friendly version of {@link AddAddressCommand}.
 */
public class JsonAdaptedAddAddressCommand {

    public static final String MESSAGE_INVALID_COMMAND = "This command is not an AddAddressCommand.";

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
     * Converts a given {@code Command} into this class for Jackson use.
     * {@code Command} should be a {@code AddAddressCommand}.
     *
     * @param command {@code Command} to be used to construct the {@code JsonAdaptedAddAddressCommand}.
     * @throws InvalidCommandToJsonException If {@code Command} is not a {@code AddAddressCommand}.
     */
    public JsonAdaptedAddAddressCommand(Command command) throws InvalidCommandToJsonException {
        if (!(command instanceof AddAddressCommand)) {
            throw new InvalidCommandToJsonException(MESSAGE_INVALID_COMMAND);
        }
        AddAddressCommand addAddressCommand = (AddAddressCommand) command;
        toAdd = new JsonAdaptedPerson(addAddressCommand.getPersonToAdd());
    }

    /**
     * Converts this Jackson-friendly adapted {@code AddAddressCommand} object into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code AddAddressCommand}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code AddAddressCommand}.
     */
    public Command toModelType() throws IllegalValueException {
        return new AddAddressCommand(toAdd.toModelType());
    }

}
