package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_CANNOT_REDO_COMMAND;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redoes previously undone {@code ReversibleCommand}.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redoes the most recent undone command. \n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Redone most recent undone command: \n";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!model.canRedoCommand()) {
            throw new CommandException(MESSAGE_CANNOT_REDO_COMMAND);
        }
        CommandResult commandResult = model.redoCommand();
        String msgSuccess = MESSAGE_SUCCESS + commandResult.getFeedbackToUser();

        if (commandResult.isDone()) {
            return CommandResult.commandResultDone(msgSuccess);
        }

        if (commandResult.isShowHelp()) {
            return CommandResult.commandResultHelp(msgSuccess);
        }

        if (commandResult.isExit()) {
            return CommandResult.commandResultExit(msgSuccess);
        }

        if (commandResult.isServe()) {
            return CommandResult.commandResultServe(msgSuccess);
        }

        if (commandResult.isToggleUi()) {
            return CommandResult.commandResultToggleUi(msgSuccess);
        }

        return new CommandResult(msgSuccess);
    }

    @Override
    public boolean equals(Object other) {
        return (other == this || other instanceof RedoCommand);
    }
}
