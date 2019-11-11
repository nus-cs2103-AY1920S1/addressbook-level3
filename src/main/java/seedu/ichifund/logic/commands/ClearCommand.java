package seedu.ichifund.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Ichifund has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFundBook(new FundBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
