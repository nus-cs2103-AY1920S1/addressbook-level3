package seedu.billboard.logic.commands;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.undo.UndoList;

/**
 * Undo the previous edit action.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        String undoCmd = UndoList.getCmd();
        Model undoModel = UndoList.getModel();
        model.setModel(undoModel);
        CommandResult cmdResult = new CommandResult(COMMAND_WORD + ": " + undoCmd);
        return cmdResult;
    }
}
