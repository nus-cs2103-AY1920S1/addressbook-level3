package seedu.exercise.logic.commands;

/**
 * Interface for commands which uses the same command word but carry out different operations.
 */
public interface TypeDependentCommand {

    /**
     * Returns the unique identifier of the command.
     *
     * @return the name of the identifier (E.g. "exercise" or "regime")
     */
    String getCommandTypeIdentifier();
}
