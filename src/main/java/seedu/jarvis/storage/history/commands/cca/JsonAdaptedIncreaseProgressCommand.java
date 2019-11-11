package seedu.jarvis.storage.history.commands.cca;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.cca.IncreaseProgressCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.cca.JsonAdaptedCca;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;

/**
 * Jackson-friendly version of {@link IncreaseProgressCommand}
 */
public class JsonAdaptedIncreaseProgressCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_INDEX = "Invalid index.";

    private final JsonAdaptedIndex index;
    private final JsonAdaptedCca cca;

    /**
     * Constructs a {@code JsonAdaptedIncreaseProgressCommand} with the given {@code Index}.
     *
     * @param index {@code Index} of the cca.
     * @param cca {@code Cca} that was updated.
     */
    @JsonCreator
    public JsonAdaptedIncreaseProgressCommand(@JsonProperty("index") JsonAdaptedIndex index,
                                              @JsonProperty("cca") JsonAdaptedCca cca) {
        this.index = index;
        this.cca = cca;
    }

    /**
     * Converts a given {@code IncreaseProgressCommand} into this class for Jackson use.
     *
     * @param increaseProgressCommand {@code IncreaseProgressCommand} to be used to construct the
     * {@code JsonAdaptedIncreaseProgressCommand}.
     */
    public JsonAdaptedIncreaseProgressCommand(IncreaseProgressCommand increaseProgressCommand) {
        index = new JsonAdaptedIndex(increaseProgressCommand.getTargetIndex());
        cca = increaseProgressCommand.getTargetCca().map(JsonAdaptedCca::new).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedIncreaseProgressCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (index == null) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return new IncreaseProgressCommand(
                index.toModelType(),
                cca != null ? cca.toModelType() : null);
    }
}
