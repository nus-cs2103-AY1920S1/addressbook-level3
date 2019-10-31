package seedu.address.logic.commands.arguments;

import java.util.List;
import java.util.function.Consumer;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a variable argument of Strings.
 */
public class StringVariableArguments extends VariableArguments<String> {

    StringVariableArguments(VariableArgumentsBuilder<String> builder) {
        super(builder);
    }

    public static StringVariableArgumentsBuilder newBuilder(String description, Consumer<List<String>> promise) {
        return new StringVariableArgumentsBuilder(description, promise);
    }

    @Override
    String parse(String userInput) throws ParseException {
        return userInput;
    }
}
