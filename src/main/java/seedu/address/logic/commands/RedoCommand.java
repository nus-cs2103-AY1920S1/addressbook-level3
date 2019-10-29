package seedu.address.logic.commands;

import java.util.EmptyStackException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author ambervoong
/**
 * Redoes a command that was previously undone. A RedoCommand is not considered an UndoableCommand.
 */
public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";
    public static final String SHORTCUT_WORD = "r";

    public static final String MESSAGE_REDO_FAILURE = "There was an error redoing the previous command!";
    public static final String MESSAGE_EMPTY_REDO_HISTORY = "There are no more commands to redo!";

    public RedoCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        UndoableCommand command = null;
        try {
            command = model.getUndoneCommand();
            return command.redo(model);
        } catch (EmptyStackException | NullPointerException e) {
            throw new CommandException(MESSAGE_EMPTY_REDO_HISTORY);
        } catch (CommandException e) {
            // Add the command back to history.
            model.addExecutedCommand(command);
            System.out.println(e.getMessage());
            throw new CommandException(MESSAGE_REDO_FAILURE);
        }
    }
}

