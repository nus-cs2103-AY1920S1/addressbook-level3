package seedu.address.logic.commands.arguments;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents an object that builds variable arguments.
 * @param <T> the type of variable arguments to build
 */
public abstract class VariableArgumentsBuilder<T> {

    private final String description;
    private final Consumer<List<T>> promise;

    VariableArgumentsBuilder(String description, Consumer<List<T>> promise) {
        this.description = description;
        this.promise = promise;
    }

    /**
     * Builds the variable arguments.
     * @return the built variable arguments
     */
    public VariableArguments<T> build() {
        return argumentBuild();
    }

    abstract VariableArguments<T> argumentBuild();

    String getDescription() {
        return description;
    }

    Consumer<List<T>> getPromise() {
        return promise;
    }
}
