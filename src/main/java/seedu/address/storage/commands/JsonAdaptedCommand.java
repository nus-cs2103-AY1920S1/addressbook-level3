package seedu.address.storage.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.CommandWord;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.earnings.Module;

/**
 * Jackson-friendly version of {@link CommandObject}.
 */
public class JsonAdaptedCommand {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CommandObject's %s field is missing!";

    private final String commandWord;
    private final String commandAction;

    /**
     * Constructs a {@code JsonAdaptedCommand} with the given CommandObject details.
     */
    @JsonCreator
    public JsonAdaptedCommand(@JsonProperty("commandWord") String commandWord,
                               @JsonProperty("commandAction") String commandAction) {
        this.commandAction = commandAction;
        this.commandWord = commandWord;
    }

    /**
     * Converts a given {@code CommandObject} into this class for Jackson use.
     */
    public JsonAdaptedCommand(CommandObject source) {
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }
        if (!CommandAction.isValidAction(commandAction)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final CommandAction modelAction = new CommandAction(commandAction);

        if (commandWord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }
        if (!Module.isValidModuleName(commandWord)) {
            throw new IllegalValueException(Module.MESSAGE_CONSTRAINTS);
        }
        final CommandWord modelWord = new CommandWord(commandWord);
        return new CommandObject(modelWord, modelAction);
    }
}
