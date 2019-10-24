package seedu.address.logic.commands.arguments;

import java.util.function.Consumer;

/**
 * Represents an ArgumentBuilder responsible for building {@link StringArgument}
 */
public class StringArgumentBuilder extends ArgumentBuilder<String> {

    StringArgumentBuilder(String description, Consumer<String> promise) {
        super(description, promise);
    }

    @Override
    Argument<String> argumentBuild() {
        return new StringArgument(this);
    }
}
