package seedu.weme.logic.commands.exportcommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;

/**
 * Clears all the memes in Weme's export staging area.
 */
public class ExportClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Export staging area has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.clearExportList();
        CommandResult result = new CommandResult(MESSAGE_SUCCESS);

        return result;
    }
}
