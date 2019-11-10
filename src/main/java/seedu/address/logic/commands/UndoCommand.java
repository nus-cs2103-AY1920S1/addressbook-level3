package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_CANNOT_UNDO_COMMAND;

import javafx.util.Pair;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes most recent {@code ReversibleCommand}.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes the most recent reversible command. \n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Undone most recent command: \n";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!model.canUndoCommand()) {
            throw new CommandException(MESSAGE_CANNOT_UNDO_COMMAND);
        }
        Pair<CommandResult, CommandResult> commandResults = model.undoCommand();
        CommandResult actualResult = commandResults.getKey();
        CommandResult resultOfUndoneCommand = commandResults.getValue();

        String msgSuccess = MESSAGE_SUCCESS + resultOfUndoneCommand.getFeedbackToUser();

        if (actualResult.isDone()) {
            return CommandResult.commandResultDone(msgSuccess);
        }

        if (actualResult.isShowHelp()) {
            return CommandResult.commandResultHelp(msgSuccess);
        }

        if (actualResult.isExit()) {
            return CommandResult.commandResultExit(msgSuccess);
        }

        if (actualResult.isServe()) {
            return CommandResult.commandResultServe(msgSuccess);
        }

        if (actualResult.isToggleUi()) {
            return CommandResult.commandResultToggleUi(msgSuccess);
        }

        return new CommandResult(msgSuccess);
    }

    @Override
    public boolean equals(Object other) {
        return (other == this || other instanceof UndoCommand);
    }
}
