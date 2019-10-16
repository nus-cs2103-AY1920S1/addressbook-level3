package seedu.jarvis.storage.history.commands.address;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.EditAddressCommand;
import seedu.jarvis.storage.address.JsonAdaptedPerson;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Jackson-friendly version of {@link EditAddressCommand}.
 */
public class JsonAdaptedEditAddressCommand extends JsonAdaptedCommand {
    public static final String MESSAGE_INVALID_COMMAND = "This command is not a EditAddressCommand";
    public static final String MESSAGE_INVALID_FIELDS = "Incomplete data on the changes to person";
    private final JsonAdaptedIndex index;
    private final JsonAdaptedEditPersonDescriptor editPersonDescriptor;
    private final JsonAdaptedPerson originalPerson;
    private final JsonAdaptedPerson editedPerson;

    /**
     * Constructs a {@code JsonAdaptedEditAddressCommand} with the given {@code Index} of the person to edit, the
     * {@code EditPersonDescriptor} to apply the changes to, the {@code Person} that was edited to, and the original
     * {@code Person}.
     *
     * @param index {@code Index} of the person to be edited.
     * @param editPersonDescriptor {@code EditPersonDescriptor} that contains the changes.
     * @param originalPerson Original {@code Person}, which may be null.
     * @param editedPerson Edited {@code Person}, which may be null.
     */
    @JsonCreator
    public JsonAdaptedEditAddressCommand(@JsonProperty("index") JsonAdaptedIndex index,
                                         @JsonProperty("editPersonDescriptor") JsonAdaptedEditPersonDescriptor
                                                 editPersonDescriptor,
                                         @JsonProperty("originalPerson") JsonAdaptedPerson originalPerson,
                                         @JsonProperty("editedPerson") JsonAdaptedPerson editedPerson) {
        this.index = index;
        this.editPersonDescriptor = editPersonDescriptor;
        this.originalPerson = originalPerson;
        this.editedPerson = editedPerson;
    }

    /**
     * Converts a given {@code Command} into this class for Jackson use.
     * {@code Command} should be a {@code EditAddressCommand}.
     *
     * @param command {@code Command} to be used to construct the {@code JsonAdaptedEditAddressCommand}.
     * @throws InvalidCommandToJsonException If {@code Command} is not a {@code EditAddressCommand}.
     */
    public JsonAdaptedEditAddressCommand(Command command) throws InvalidCommandToJsonException {
        if (!(command instanceof EditAddressCommand)) {
            throw new InvalidCommandToJsonException(MESSAGE_INVALID_COMMAND);
        }
        EditAddressCommand editAddressCommand = (EditAddressCommand) command;
        index = new JsonAdaptedIndex(editAddressCommand.getIndex());
        editPersonDescriptor = new JsonAdaptedEditPersonDescriptor(editAddressCommand.getEditPersonDescriptor());
        originalPerson = editAddressCommand.getOriginalPerson().map(JsonAdaptedPerson::new).orElse(null);
        editedPerson = editAddressCommand.getEditedPerson().map(JsonAdaptedPerson::new).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedEditAddressCommand}.
     */
    public Command toModelType() throws IllegalValueException {
        if (originalPerson == null && editedPerson == null) {
            return new EditAddressCommand(index.toModelType(), editPersonDescriptor.toModelType());
        }
        if (originalPerson == null || editedPerson == null) {
            throw new IllegalValueException(MESSAGE_INVALID_FIELDS);
        }
        return new EditAddressCommand(index.toModelType(), editPersonDescriptor.toModelType(),
                originalPerson.toModelType(), editedPerson.toModelType());
    }
}
