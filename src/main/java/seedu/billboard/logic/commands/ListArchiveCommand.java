package seedu.billboard.logic.commands;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;

import static java.util.Objects.requireNonNull;

public class ListArchiveCommand extends Command {

    public static final String COMMAND_WORD = "listarc";

    public static final String MESSAGE_SUCCESS = "Listed all archive expenses";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredArchiveExpenses(Model.PREDICATE_SHOW_ALL_EXPENSES);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false);
    }
}
