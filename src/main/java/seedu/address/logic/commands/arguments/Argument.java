package seedu.address.logic.commands.arguments;

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
    private String userInput;

    private final String description;
    private final Consumer<T> promise;

    Argument(ArgumentBuilder<T> builder) {
        this.description = Objects.requireNonNull(builder.getDescription());
        this.promise = builder.getPromise();
    }

    /**
     * Accepts user input, cannot be null.
     * @param userInput the user input
     * @return this instance
     */
    public Argument<T> accept(String userInput) {
        this.userInput = Objects.requireNonNull(userInput);
        return this;
    }

    /**
     * Builds the argument value, checks if user input is valid and parses it into the defined type.
     * Passes the built value to a user defined promise.
     * @param required if the argument is required to have a value
     * @return the built value
     * @throws ArgumentException if the argument is required but user input is null
     * @throws ParseException if the user input is invalid
     */
    public T build(boolean required) throws ArgumentException, ParseException {
        if (this.userInput == null) {
            if (required) {
                throw new ArgumentException(String.format(MESSAGE_REQUIRED_COMMAND_ARGUMENT, this.description));
            }
        } else {
            this.value = this.parse(userInput);
        }
        promise.accept(this.value);
        return this.value;
    }

    abstract T parse(String userInput) throws ParseException;
}
