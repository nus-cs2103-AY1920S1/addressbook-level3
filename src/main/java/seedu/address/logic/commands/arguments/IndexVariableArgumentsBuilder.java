package seedu.address.logic.commands.arguments;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a VariableArgumentsBuilder responsible for building {@link IndexVariableArguments}
 */
public class IndexVariableArgumentsBuilder extends VariableArgumentsBuilder<Integer> {

    IndexVariableArgumentsBuilder(String description, Consumer<List<Integer>> promise) {
        super(description, promise);
    }

    @Override
    VariableArguments<Integer> argumentBuild() {
        return new IndexVariableArguments(this);
    }
}
