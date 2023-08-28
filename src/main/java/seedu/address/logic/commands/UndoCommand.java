package seedu.address.logic.commands;

import java.util.EmptyStackException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

//@@author ambervoong
/**
 * Undoes a command that was previously executed. An UndoCommand is not considered an UndoableCommand, as further undo
 * attempts by the user would add more UndoCommands to CommandHistory instead of undoing other past commands.
 * However, an UndoCommand can be undone using a RedoCommand.
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";
    public static final String SHORTCUT_WORD = "u";

    public static final String MESSAGE_UNDO_FAILURE = "There was an error undoing the previous command!";
    public static final String MESSAGE_EMPTY_UNDO_HISTORY = "There are no more commands to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        UndoableCommand command = null;
        try {
            command = model.getExecutedCommand();
            return command.undo(model);
        } catch (EmptyStackException | NullPointerException e) {
            throw new CommandException(MESSAGE_EMPTY_UNDO_HISTORY);
        } catch (CommandException e) {
            // Add the command back to history.
            model.addExecutedCommand(command);
            throw new CommandException(MESSAGE_UNDO_FAILURE);
        }
    }
}
