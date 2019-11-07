package seedu.guilttrip.logic.commands.uicommands;

import static java.util.Objects.requireNonNull;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.model.Model;

/**
 * Lists all available fonts.
 */
public class ListFontCommand extends Command {

    public static final String COMMAND_WORD = "listFont";
    public static final String ONE_LINER_DESC = COMMAND_WORD + "Lists all available fonts.";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC;

    public static final String MESSAGE_SUCCESS = "Listed all fonts";

    public ListFontCommand() {
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);

        return new CommandResult(MESSAGE_SUCCESS, null, true, false);
    }
}
