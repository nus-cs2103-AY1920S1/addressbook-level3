package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.weme.model.MemeBook;
import seedu.weme.model.Model;

/**
 * Clears the meme book.
 */
public class MemeClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "weme has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMemeBook(new MemeBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
