package mams.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import mams.commons.exceptions.IllegalValueException;
import mams.logic.CommandHistory;
import mams.logic.InputOutput;
import mams.logic.ReadOnlyCommandHistory;

/**
 * An Immutable CommandHistory that is serializable to JSON format.
 */
@JsonRootName(value = "commandHistory")
class JsonSerializableCommandHistory {

    private final List<JsonAdaptedInputOutput> inputOutputs = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCommandHistory} with the given command inputs and outputs.
     */
    @JsonCreator
    public JsonSerializableCommandHistory(@JsonProperty("inputOutputs") List<JsonAdaptedInputOutput> inputOutputs) {
        this.inputOutputs.addAll(inputOutputs);
    }

    /**
     * Converts a given {@code ReadOnlyCommandHistory} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCommandHistory}.
     */
    public JsonSerializableCommandHistory(ReadOnlyCommandHistory source) {
        inputOutputs.addAll(source.getInputOutputHistory().stream().map(JsonAdaptedInputOutput::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this CommandHistory into the logic's {@code CommandHistory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CommandHistory toLogicType() throws IllegalValueException {
        CommandHistory commandHistory = new CommandHistory();

        for (JsonAdaptedInputOutput jsonAdaptedInputOutput: inputOutputs) {
            InputOutput inputOutput = jsonAdaptedInputOutput.toLogicType();
            commandHistory.add(inputOutput.getInput(), inputOutput.getOutput(),
                    inputOutput.checkSuccessful(), inputOutput.getTimeStamp());
        }

        return commandHistory;
    }

}
