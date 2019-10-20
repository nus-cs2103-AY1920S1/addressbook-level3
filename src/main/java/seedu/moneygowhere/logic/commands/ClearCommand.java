package seedu.moneygowhere.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.SpendingBook;

/**
 * Clears the MoneyGoWhere list.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "MoneyGoWhere has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setSpendingBook(new SpendingBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
