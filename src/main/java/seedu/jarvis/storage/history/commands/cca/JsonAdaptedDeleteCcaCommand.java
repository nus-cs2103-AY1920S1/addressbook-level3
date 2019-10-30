package seedu.jarvis.storage.history.commands.cca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.cca.DeleteCcaCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.cca.JsonAdaptedCca;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link DeleteCcaCommand}.
 */
public class JsonAdaptedDeleteCcaCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_INDEX = "Invalid index.";

    private final JsonAdaptedIndex index;
    private final JsonAdaptedCca cca;

    /**
     * Constructs a {@code JsonAdaptedDeleteCcaCommand} with the given {@code Index} of the cca to delete, and
     * {@code cca} that was deleted.
     *
     * @param index {@code Index} of the {@code Cca} to be deleted.
     * @param cca {@code Cca} that was deleted, which may be null.
     */
    @JsonCreator
    public JsonAdaptedDeleteCcaCommand(@JsonProperty("index") JsonAdaptedIndex index,
                                       @JsonProperty("cca") JsonAdaptedCca cca) {
        this.index = index;
        this.cca = cca;
    }

    /**
     * Converts a given {@code DeleteCcaCommand} into this class for Jackson use.
     *
     * @param deleteCcaCommand {@code DeleteCcaCommand} to be used to construct the {@code JsonAdaptedDeleteCcaCommand}.
     */
    public JsonAdaptedDeleteCcaCommand(DeleteCcaCommand deleteCcaCommand) {
        index = new JsonAdaptedIndex(deleteCcaCommand.getTargetIndex());
        cca = deleteCcaCommand.getDeletedCca().map(JsonAdaptedCca::new).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedDeleteCcaCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (index == null) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return new DeleteCcaCommand(
                index.toModelType(),
                cca != null ? cca.toModelType() : null);
    }
}
