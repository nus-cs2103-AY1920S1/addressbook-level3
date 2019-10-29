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

    private final JsonAdaptedIndex index;
    private final JsonAdaptedCca cca;

    @JsonCreator
    public JsonAdaptedIncreaseProgressCommand(@JsonProperty("index") JsonAdaptedIndex index,
                                              @JsonProperty("cca") JsonAdaptedCca cca) {
        this.index = index;
        this.cca = cca;
    }

    public JsonAdaptedIncreaseProgressCommand(IncreaseProgressCommand increaseProgressCommand) {
        index = new JsonAdaptedIndex(increaseProgressCommand.getTargetIndex());
        cca = new JsonAdaptedCca(increaseProgressCommand.getTargetCca());
    }


    @Override
    public Command toModelType() throws IllegalValueException {
        return null;
    }
}
