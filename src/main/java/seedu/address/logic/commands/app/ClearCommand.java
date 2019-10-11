package seedu.address.logic.commands.app;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.AppCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.wordbank.WordBank;

/**
 * Clears the address book.
 */
public class ClearCommand extends AppCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setWordBank(new WordBank());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
