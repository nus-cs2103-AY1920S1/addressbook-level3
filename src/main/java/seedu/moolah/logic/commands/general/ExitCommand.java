package seedu.moolah.logic.commands.general;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.model.Model;
import seedu.moolah.ui.panel.PanelName;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit" + CommandGroup.GENERAL;

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting MooLah as requested ...";

    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    @Override
    protected CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, PanelName.CURRENT);
    }

}
