package seedu.jarvis.storage.history.commands.address;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.DeleteAddressCommand;
import seedu.jarvis.storage.address.JsonAdaptedPerson;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Jackson-friendly version of {@link DeleteAddressCommand}.
 */
public class JsonAdaptedDeleteAddressCommand extends JsonAdaptedCommand {
    public static final String MESSAGE_INVALID_COMMAND = "This command is not a DeleteAddressCommand.";
    private final JsonAdaptedIndex targetIndex;
    private final JsonAdaptedPerson deletedPerson;

    /**
     * Constructs a {@code JsonAdaptedDeleteAddressCommand} with the given {@code Index} of the person to delete, and
     * {@code JsonAdaptedPerson} that was deleted.
     *
     * @param targetIndex {@code Index} of the {@code Person} to be deleted.
     * @param deletedPerson {@code Person} that was deleted, which may be null.
     */
    @JsonCreator
    public JsonAdaptedDeleteAddressCommand(@JsonProperty("targetIndex") JsonAdaptedIndex targetIndex,
                                           @JsonProperty("deletedPerson") JsonAdaptedPerson deletedPerson) {
        this.targetIndex = targetIndex;
        this.deletedPerson = deletedPerson;
    }

    /**
     * Converts a given {@code Command} into this class for Jackson use.
     * {@code Command} should be a {@code DeleteAddressCommand}.
     *
     * @param command {@code Command} to be used to construct the {@code JsonAdaptedDeleteAddressCommand}.
     * @throws InvalidCommandToJsonException If {@code Command} is not a {@code DeleteAddressCommand}.
     */
    public JsonAdaptedDeleteAddressCommand(Command command) throws InvalidCommandToJsonException {
        if (!(command instanceof DeleteAddressCommand)) {
            throw new InvalidCommandToJsonException(MESSAGE_INVALID_COMMAND);
        }
        DeleteAddressCommand deleteAddressCommand = (DeleteAddressCommand) command;
        targetIndex = new JsonAdaptedIndex(deleteAddressCommand.getTargetIndex());
        deletedPerson = deleteAddressCommand.getDeletedPerson() != null
                ? new JsonAdaptedPerson(deleteAddressCommand.getDeletedPerson())
                : null;
    }

    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedDeleteAddressCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        return deletedPerson != null
                ? new DeleteAddressCommand(targetIndex.toModelType(), deletedPerson.toModelType())
                : new DeleteAddressCommand(targetIndex.toModelType());
    }
}
