package seedu.address.logic.commands.arguments;

import static seedu.address.commons.core.Messages.MESSAGE_REQUIRED_COMMAND_ARGUMENT;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.ArgumentException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents an argument in an Option.
 * It always has a defined type, and user input entered into this argument will be parsed to the defined type.
 * @param <T> the type of this argument
 */
public abstract class Argument<T> {

    private T value;
    private final String description;

    Argument(ArgumentBuilder<T> builder) {
        this.description = builder.getDescription();
    }

    /**
     * Accepts user input and parses it to the defined type.
     * @param userInput the user input
     * @throws ParseException if the user input is invalid
     */
    public void accept(String userInput) throws ParseException {
        Objects.requireNonNull(userInput);
        this.value = this.parse(userInput);
    }

    /**
     * Builds the argument, calls the builder consumer.
     * @param required if the argument is required to have a value
     * @throws ArgumentException if the argument is required but is null
     */
    public void build(boolean required) throws ArgumentException {
        if (required && this.value == null) {
            throw new ArgumentException(String.format(MESSAGE_REQUIRED_COMMAND_ARGUMENT, this.description));
        }
    }

    T getValue() {
        return value;
    }

    abstract T parse(String userInput) throws ParseException;
}
