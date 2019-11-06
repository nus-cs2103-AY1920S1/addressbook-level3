package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.commanditem.CommandTask;
import seedu.address.model.commanditem.CommandWord;

/**
 * Jackson-friendly version of {@link CommandItem}.
 */
public class JsonAdaptedCommandItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CommandObject's %s field is missing!";

    private final String commandWord;
    private final String commandTask;

    /**
     * Constructs a {@code JsonAdaptedCommand} with the given CommandObject details.
     */
    @JsonCreator
    public JsonAdaptedCommandItem(@JsonProperty("commandWord") String commandWord,
                              @JsonProperty("commandTask") String commandAction) {
        this.commandTask = commandAction;
        this.commandWord = commandWord;
    }

    /**
     * Converts a given {@code CommandObject} into this class for Jackson use.
     */
    public JsonAdaptedCommandItem(CommandItem source) {
        commandTask = source.getCommandTask().task;
        commandWord = source.getCommandWord().word;
    }

    /**
     * Converts this Jackson-friendly adapted commanditem object into the model's {@code commanditem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public CommandItem toModelType() throws IllegalValueException {

        if (commandTask == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }
        if (!CommandTask.isValidTask(commandTask)) {
            throw new IllegalValueException(CommandTask.MESSAGE_CONSTRAINTS);
        }
        final CommandTask modelTask = new CommandTask(commandTask);

        if (commandWord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT));
        }
        if (!CommandWord.isValidWord(commandWord)) {
            throw new IllegalValueException(CommandWord.MESSAGE_CONSTRAINTS);
        }
        final CommandWord modelWord = new CommandWord(commandWord);
        return new CommandItem(modelWord, modelTask);
    }
}
