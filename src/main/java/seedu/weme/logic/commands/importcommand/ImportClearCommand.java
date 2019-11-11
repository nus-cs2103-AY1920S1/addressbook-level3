package seedu.weme.logic.commands.importcommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;

/**
 * Clears all the memes in Weme's import tab.
 */
public class ImportClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Import tab has been cleared!";
    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": clear all memes in the import tab.";
    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION;


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.clearImportList();
        CommandResult result = new CommandResult(MESSAGE_SUCCESS);

        return result;
    }
}
