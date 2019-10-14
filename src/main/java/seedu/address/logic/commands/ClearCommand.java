package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Displays all books - clears the search filter
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Catalog has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetFilteredBookList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
