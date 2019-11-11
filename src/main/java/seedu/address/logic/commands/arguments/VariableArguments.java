package seedu.address.logic.commands.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a variable argument of a Command.
 * A variable argument can take in 0..* arguments, and it always has a defined argument type.
 * User input entered into this argument will be parsed to the defined argument type.
 * @param <T> the type of this variable argument
 */
public abstract class VariableArguments<T> {

    private final List<T> values;
    private final List<String> userInput;

    private final String description;
    private final Consumer<List<T>> promise;

    VariableArguments(VariableArgumentsBuilder<T> builder) {
        this.values = new ArrayList<>();
        this.userInput = new ArrayList<>();
        this.description = builder.getDescription();
        this.promise = builder.getPromise();
    }

    /**
     * Accepts user input.
     * @param userInput the user input
     * @return this instance
     */
    public VariableArguments<T> accept(String userInput) {
        Objects.requireNonNull(userInput);
        this.userInput.add(userInput);
        return this;
    }

    /**
     * Builds the variable arguments by parsing user input from accept().
     * Passes the built values to a user defined promise.
     * @return the built variable arguments values
     * @throws ParseException if the user input is invalid
     */
    public List<T> build() throws ParseException {
        for (String input : this.userInput) {
            this.values.add(this.parse(input));
        }
        this.promise.accept(this.values);
        return this.values;
    }

    abstract T parse(String userInput) throws ParseException;
}
