package budgetbuddy.logic.commands;

import java.nio.file.Path;
import java.util.Objects;

import budgetbuddy.logic.commands.exceptions.CommandException;

/**
 * Represents a command continuation, requiring some further input from the user to complete the command.
 */
public class CommandContinuation<T> {
    /**
     * Represents the type of the continuation.
     */
    public enum Type {
        SHOW_HELP,
        EXIT,
        SHOW_FILE_PICKER;
    }

    /**
     * Represents a callback to continue command execution.
     */
    @FunctionalInterface
    public interface Callback<T> {
        public CommandResult callback(T value) throws CommandException;
    }

    private final Type type;
    private final Callback<T> callback;

    private CommandContinuation(Type type) {
        this(type, null);
    }

    private CommandContinuation(Type type, Callback<T> callback) {
        this.type = type;
        this.callback = callback;
    }

    public Type getType() {
        return type;
    }

    /**
     * Continues the command with the given result.
     */
    public CommandResult continueCommand(T value) throws CommandException {
        if (callback == null) {
            return null;
        }
        return callback.callback(value);
    }

    /**
     * Returns this continuation as a {@code CommandContinuation<Path>} if this continuation
     * is of the type {@link Type#SHOW_FILE_PICKER}.
     */
    @SuppressWarnings("unchecked")
    public CommandContinuation<Path> asShowFilePicker() {
        // I really, really hate Java's nearly useless type system
        // Come on!!!!!!!!!! It's 2019!!!!! WE CAN HAVE REIFIED GENERICS AT LEAST.
        if (this.type == Type.SHOW_FILE_PICKER) {
            return (CommandContinuation<Path>) this;
        }

        return null;
    }

    public static CommandContinuation<Void> showHelp() {
        return new CommandContinuation<>(Type.SHOW_HELP);
    }

    public static CommandContinuation<Void> exit() {
        return new CommandContinuation<>(Type.EXIT);
    }

    public static CommandContinuation<Path> showFilePicker(Callback<Path> callback) {
        return new CommandContinuation<>(Type.SHOW_FILE_PICKER, callback);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandContinuation<?> that = (CommandContinuation<?>) o;
        return type == that.type && Objects.equals(callback, that.callback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, callback);
    }
}
