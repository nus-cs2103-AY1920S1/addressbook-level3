package seedu.address.logic.commands.arguments;

import java.util.function.Consumer;

/**
 * Represents an object that builds arguments.
 * @param <T> the type of the argument to build
 */
public abstract class ArgumentBuilder<T> {

    private final String description;
    private final Consumer<T> promise;

    ArgumentBuilder(String description, Consumer<T> promise) {
        this.description = description;
        this.promise = promise;
    }

    /**
     * Builds the argument.
     * @return the built argument
     */
    public Argument<T> build() {
        return argumentBuild();
    }

    abstract Argument<T> argumentBuild();

    String getDescription() {
        return description;
    }

    Consumer<T> getPromise() {
        return promise;
    }
}
