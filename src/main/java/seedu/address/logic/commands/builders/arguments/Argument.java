package seedu.address.logic.commands.builders.arguments;

import static seedu.address.commons.core.Messages.MESSAGE_REQUIRED_COMMAND_ARGUMENT;

import java.util.Objects;
import java.util.function.Consumer;

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
    private final Consumer<T> builder;

    Argument(String description, Consumer<T> builder) {
        this.description = description;
        this.builder = builder;
    }

    public void accept(String userInput) throws ParseException {
        Objects.requireNonNull(userInput);
        this.value = this.parse(userInput);
    }

    abstract T parse(String userInput) throws ParseException;

    public void build(boolean required) throws ArgumentException {
        if (required && this.value == null) {
            throw new ArgumentException(String.format(MESSAGE_REQUIRED_COMMAND_ARGUMENT, this.description));
        }
        builder.accept(this.value);
    }
}
