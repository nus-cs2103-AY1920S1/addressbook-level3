package seedu.jarvis.storage.history;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * A {@code HistoryManager} that is serializable to JSON format.
 */
@JsonRootName(value = "historymanager")
public class JsonSerializableHistoryManager implements JsonAdapter<HistoryManager> {

    public static final String MESSAGE_ERROR_CONVERTING_HISTORY_MANAGER = "Error converting HistoryManager";
    public static final String MESSAGE_INVALID_COMMAND = "Unknown Command";
    public static final String MESSAGE_COMMAND_MISMATCH = "Error mapping command";

    private final List<JsonAdaptedCommand> executedCommands = new ArrayList<>();
    private final List<JsonAdaptedCommand> inverselyExecutedCommands = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableHistoryManager} with the given executed and inversely executed commands.
     *
     * @param executedCommands {@code List} of executed Jackson-Friendly commands.
     * @param inverselyExecutedCommands {@code List} of inversely executed Jackson-Friendly commands
     */
    @JsonCreator
    public JsonSerializableHistoryManager(@JsonProperty("executedCommands") List<JsonAdaptedCommand> executedCommands,
                                          @JsonProperty("inverselyExecutedCommands")
                                                  List<JsonAdaptedCommand> inverselyExecutedCommands) {
        this.executedCommands.addAll(executedCommands);
        this.inverselyExecutedCommands.addAll(inverselyExecutedCommands);
    }

    /**
     * Converts a given {@code HistoryManager} into this class for Jackson use.
     *
     * @param historyManager {@code HistoryManager} to be converted for Jackson-Friendly use.
     * @throws IOException If there was an error in converting any commands into Jackson-Friendly
     * objects.
     */
    public JsonSerializableHistoryManager(HistoryManager historyManager) throws IOException {
        List<Command> executedCommands = historyManager.getExecutedCommands().getCommands();
        List<Command> inverselyExecutedCommands = historyManager.getInverselyExecutedCommands().getCommands();
        try {
            addCommands(executedCommands, inverselyExecutedCommands);
        } catch (InvalidCommandToJsonException e) {
            throw new IOException(MESSAGE_ERROR_CONVERTING_HISTORY_MANAGER);
        }
    }

    /**
     * Adds json adapted executed commands and inversely executed commands to the {@code executedCommands} and
     * {@code inverselyExecutedCommands} fields. The fields are cleared of any commands they are containing before
     * adding the commands.
     *
     * @param executedCommands {@code Command} objects that have been executed.
     * @param inverselyExecutedCommands {@code Command} that have been inversely executed.
     * @throws InvalidCommandToJsonException If there was an error converting commands into Jackson-friendly objects.
     */
    private void addCommands(List<Command> executedCommands, List<Command> inverselyExecutedCommands) throws
            InvalidCommandToJsonException {
        this.executedCommands.clear();
        this.inverselyExecutedCommands.clear();
        for (Command command : executedCommands) {
            this.executedCommands.add(command.adaptToJsonAdaptedCommand());
        }
        for (Command command : inverselyExecutedCommands) {
            this.inverselyExecutedCommands.add(command.adaptToJsonAdaptedCommand());
        }
    }

    /**
     * Converts this Jackson-friendly adapted {@code HistoryManager} object.
     *
     * @return {@code HistoryManager} of the Jackson-friendly adapted {@code JsonSerializableHistoryManager}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted {@code HistoryManager}.
     */
    @Override
    public HistoryManager toModelType() throws IllegalValueException {
        HistoryManager historyManager = new HistoryManager();
        for (JsonAdaptedCommand jsonAdaptedExecutedCommand : executedCommands) {
            historyManager.rememberExecutedCommand(jsonAdaptedExecutedCommand.toModelType());
        }
        for (JsonAdaptedCommand jsonAdaptedInverselyExecutedCommand : inverselyExecutedCommands) {
            historyManager.rememberInverselyExecutedCommand(jsonAdaptedInverselyExecutedCommand.toModelType());
        }
        return historyManager;
    }
}
