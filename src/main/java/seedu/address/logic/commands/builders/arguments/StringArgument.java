package seedu.address.logic.commands.builders.arguments;

import java.util.function.Consumer;

/**
 * Represents a command argument of type String.
 */
public class StringArgument extends Argument<String> {

    public StringArgument(String description, Consumer<String> builder) {
        super(description, builder);
    }

    @Override
    String parse(String userInput) {
        return userInput;
    }
}
