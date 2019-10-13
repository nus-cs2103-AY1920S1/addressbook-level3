package seedu.jarvis.storage.history.commands;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;

public abstract class JsonAdaptedCommand {
    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted command.
     */
    public abstract Command toModelType() throws IllegalValueException;
}
