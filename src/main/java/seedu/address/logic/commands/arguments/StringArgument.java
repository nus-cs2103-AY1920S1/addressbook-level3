package seedu.address.logic.commands.arguments;

import java.util.function.Consumer;

/**
 * Represents a command argument of type String.
 */
public class StringArgument extends Argument<String> {

    StringArgument(ArgumentBuilder<String> builder) {
        super(builder);
    }

    public static StringArgumentBuilder newBuilder(String description, Consumer<String> promise) {
        return new StringArgumentBuilder(description, promise);
    }

    @Override
    String parse(String userInput) {
        return userInput;
    }
}
