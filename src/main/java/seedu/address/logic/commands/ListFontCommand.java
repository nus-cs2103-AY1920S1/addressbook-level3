package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

public class ListFontCommand extends Command {

    public static final String COMMAND_WORD = "listFont";

    public static final String MESSAGE_SUCCESS = "Listed all fonts";

    //private final FontName fontName;

    public ListFontCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);

        return new CommandResult(MESSAGE_SUCCESS, null, true, false);
    }
}
