package seedu.address.logic.commands.arguments;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a ArgumentBuilder responsible for building {@link StringVariableArguments}
 */
public class StringVariableArgumentsBuilder extends VariableArgumentsBuilder<String> {

    StringVariableArgumentsBuilder(String description, Consumer<List<String>> promise) {
        super(description, promise);
    }

    @Override
    VariableArguments<String> argumentBuild() {
        return new StringVariableArguments(this);
    }
}
