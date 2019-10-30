package seedu.jarvis.storage.history.commands.cca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.cca.EditCcaCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.cca.JsonAdaptedCca;
import seedu.jarvis.storage.cca.JsonAdaptedEditCcaDescriptor;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link EditCcaCommand}.
 */
public class JsonAdaptedEditCcaCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_ATTRIBUTES = "Invalid attributes.";

    private final JsonAdaptedIndex index;
    private final JsonAdaptedEditCcaDescriptor descriptor;
    private final JsonAdaptedCca originalCca;
    private final JsonAdaptedCca editedCca;

    /**
     * Constructs a {@code JsonAdaptedEditCcaCommand} with the given {@code Index} of the cca to edit, the
     * {@code EditCcaDescriptor} to apply the changes to, the {@code Cca} that was edited to, and the original
     * {@code Cca}.
     *
     * @param index {@code Index} of the cca to be edited.
     * @param descriptor {@code EditCcaDescriptor} that contains the changes.
     * @param originalCca Original {@code Cca}, which may be null.
     * @param editedCca Edited {@code Cca}, which may be null.
     */
    @JsonCreator
    public JsonAdaptedEditCcaCommand(@JsonProperty("index") JsonAdaptedIndex index,
                                     @JsonProperty("descriptor") JsonAdaptedEditCcaDescriptor descriptor,
                                     @JsonProperty("originalCca") JsonAdaptedCca originalCca,
                                     @JsonProperty("editedCca") JsonAdaptedCca editedCca) {
        this.index = index;
        this.descriptor = descriptor;
        this.originalCca = originalCca;
        this.editedCca = editedCca;
    }

    /**
     * Converts a given {@code EditCcaCommand} into this class for Jackson use.
     *
     * @param editCcaCommand {@code EditCcaCommand} to be used to construct the {@code JsonAdaptedEditCcaCommand}.
     */
    public JsonAdaptedEditCcaCommand(EditCcaCommand editCcaCommand) {
        index = new JsonAdaptedIndex(editCcaCommand.getTargetIndex());
        descriptor = new JsonAdaptedEditCcaDescriptor(editCcaCommand.getEditCcaDescriptor());
        originalCca = editCcaCommand.getOriginalCca().map(JsonAdaptedCca::new).orElse(null);
        editedCca = editCcaCommand.getEditedCca().map(JsonAdaptedCca::new).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedEditCcaCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (index == null || descriptor == null) {
            throw new IllegalValueException(MESSAGE_INVALID_ATTRIBUTES);
        }
        return new EditCcaCommand(
                index.toModelType(),
                descriptor.toModelType(),
                originalCca != null ? originalCca.toModelType() : null,
                editedCca != null ? editedCca.toModelType() : null);
    }
}
