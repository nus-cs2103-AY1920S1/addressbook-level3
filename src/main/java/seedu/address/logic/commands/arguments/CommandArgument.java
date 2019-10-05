package seedu.address.logic.commands.arguments;

import static seedu.address.commons.core.Messages.MESSAGE_REQUIRED_COMMAND_ARGUMENT;

import seedu.address.logic.commands.exceptions.ArgumentException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents an argument of a Command.
 * An argument can either be required or optional, and it always has a defined type.
 * User input entered into this argument will be parsed to the defined type.
 * @param <T> the type of this argument
 */
public abstract class CommandArgument<T> {

    private T value;
    private final String description;
    private final boolean required;

    CommandArgument(String description, boolean required) {
        this.description = description;
        this.required = required;
    }

    public void setValue(String userInput) throws ParseException {
        this.value = parse(userInput);
    }

    public abstract T parse(String userInput) throws ParseException;

    public T getValue() throws ArgumentException {
        if (this.required && this.value == null) {
            throw new ArgumentException(String.format(MESSAGE_REQUIRED_COMMAND_ARGUMENT, this.description));
        }
        return this.value;
    }
}
