package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.MemeBook;
import seedu.address.model.Model;

/**
 * Clears the meme book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "weme has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMemeBook(new MemeBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
