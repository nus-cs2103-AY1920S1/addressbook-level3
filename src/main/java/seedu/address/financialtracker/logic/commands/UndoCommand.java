package seedu.address.financialtracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.financialtracker.model.Model;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Undo previous action.
 */
public class UndoCommand extends Command<Model> {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo your previous action on this expense list";
    public static final String MESSAGE_SUCCESS = "Done!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.undo();
        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }
}
