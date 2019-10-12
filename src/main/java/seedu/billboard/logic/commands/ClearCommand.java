package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setBillboardExpenses(new Billboard());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
