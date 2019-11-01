package seedu.sugarmummy.logic.commands;

import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    //TODO: make it abstract

    /**
     * Returns a {@code DisplayPaneType} that corresponds to a specific certain command.
     *
     * @return a {@code DisplayPaneType} based on the specific command
     */
    public DisplayPaneType getDisplayPaneType() {
        return null;
    }

    public boolean getNewPaneIsToBeCreated() {
        return false;
    }

}
