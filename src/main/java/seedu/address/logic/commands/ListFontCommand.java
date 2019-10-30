package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all available fonts.
 */
public class ListFontCommand extends Command {

    public static final String COMMAND_WORD = "listFont";

    public static final String MESSAGE_SUCCESS = "Listed all fonts";

    public ListFontCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);

        return new CommandResult(MESSAGE_SUCCESS, null, true, false);
    }
}
