package seedu.address.logic.commands.builders.arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a variable argument of a Command.
 * A variable argument can take in 0..* arguments, and it always has a defined argument type.
 * User input entered into this argument will be parsed to the defined argument type.
 * @param <T> the type of this variable argument
 */
public abstract class VariableArguments<T> {

    private final List<T> arguments;
    private final String description;
    private final Consumer<List<T>> builder;

    VariableArguments(String description, Consumer<List<T>> builder) {
        this.arguments = new ArrayList<>();
        this.description = description;
        this.builder = builder;
    }

    public void accept(String userInput) throws ParseException {
        this.arguments.add(this.parse(userInput));
    }

    abstract T parse(String userInput) throws ParseException;

    public void build() {
        this.builder.accept(this.arguments);
    }
}
