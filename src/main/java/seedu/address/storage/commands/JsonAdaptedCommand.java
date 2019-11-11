package seedu.address.storage.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.CommandWord;

/**
 * Jackson-friendly version of {@link CommandObject}.
 */
public class JsonAdaptedCommand {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CommandObject's %s field is missing!";
    public static final String COMMAND_WORD = "command word";
    public static final String COMMAND_ACTION = "command action";

    private final String commandWord;
    private final String commandAction;

    /**
     * Constructs a {@code JsonAdaptedCommand} with the given CommandObject details.
     */
    @JsonCreator
    public JsonAdaptedCommand(@JsonProperty("commandWord") String commandWord,
                               @JsonProperty("commandAction") String commandAction) {
        this.commandWord = commandWord;
        this.commandAction = commandAction;
    }

    /**
     * Converts a given {@code CommandObject} into this class for Jackson use.
     */
    public JsonAdaptedCommand(CommandObject source) {
        assert source != null : "can't store a null object to file";
        commandAction = source.getCommandAction().action;
        commandWord = source.getCommandWord().word;
    }

    /**
     * Converts this Jackson-friendly adapted CommandObject object into the model's {@code CommandObject} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public CommandObject toModelType() throws IllegalValueException {

        if (commandAction == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, COMMAND_ACTION));
        }
        if (!CommandAction.isValidAction(commandAction)) {
            throw new IllegalValueException(CommandAction.MESSAGE_CONSTRAINTS);
        }
        final CommandAction modelAction = new CommandAction(commandAction);

        if (commandWord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, COMMAND_WORD));
        }
        if (!CommandWord.isValidWord(commandWord)) {
            throw new IllegalValueException(CommandWord.MESSAGE_CONSTRAINTS);
        }
        final CommandWord modelWord = new CommandWord(commandWord);
        return new CommandObject(modelWord, modelAction);
    }
}
