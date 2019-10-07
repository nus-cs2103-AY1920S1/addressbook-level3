package seedu.address.logic.commands.arguments;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a variable argument of a Command.
 * A variable argument can take in 0..* arguments, and it always has a defined argument type.
 * User input entered into this argument will be parsed to the defined argument type.
 * @param <T> the type of this variable argument
 */
public abstract class VariableArguments<T> {

    private final List<T> values;
    private final String description;

    VariableArguments(VariableArgumentsBuilder<T> builder) {
        this.values = new ArrayList<>();
        this.description = builder.getDescription();
    }

    public void accept(String userInput) throws ParseException {
        this.values.add(this.parse(userInput));
    }

    abstract T parse(String userInput) throws ParseException;

    List<T> getValues() {
        return this.values;
    }
}
