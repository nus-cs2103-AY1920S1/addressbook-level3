package seedu.jarvis.storage.history.commands.address;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.DeleteAddressCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.address.JsonAdaptedPerson;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link DeleteAddressCommand}.
 */
public class JsonAdaptedDeleteAddressCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_INDEX = "Invalid index.";

    private final JsonAdaptedIndex targetIndex;
    private final JsonAdaptedPerson deletedPerson;

    /**
     * Constructs a {@code JsonAdaptedDeleteAddressCommand} with the given {@code Index} of the person to delete, and
     * {@code Person} that was deleted.
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
     * Converts a given {@code DeleteAddressCommand} into this class for Jackson use.
     *
     * @param deleteAddressCommand {@code DeleteAddressCommand} to be used to construct the
     * {@code JsonAdaptedDeleteAddressCommand}.
     */
    public JsonAdaptedDeleteAddressCommand(DeleteAddressCommand deleteAddressCommand) {
        targetIndex = new JsonAdaptedIndex(deleteAddressCommand.getTargetIndex());
        deletedPerson = deleteAddressCommand.getDeletedPerson().map(JsonAdaptedPerson::new).orElse(null);
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
        if (targetIndex == null) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }

        return new DeleteAddressCommand(
                targetIndex.toModelType(),
                deletedPerson != null ? deletedPerson.toModelType() : null);
    }
}
