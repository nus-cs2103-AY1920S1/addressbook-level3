package cs.f10.t1.nursetraverse.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Full command text input by the user that resulted in the creation of this {@code Command}.
     */
    private String commandText = null;

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Sets the command text.
     */
    public void setCommandText(String commandText) {
        requireNonNull(commandText);
        this.commandText = commandText;
    }

    /**
     * Returns an {@code Optional} containing the command text. In the case where the {@code Command} was created
     * programmatically (e.g. in tests), this may return an empty {@code Optional}.
     */
    public Optional<String> getCommandText() {
        return Optional.ofNullable(commandText);
    }
}
