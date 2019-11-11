package seedu.address.logic.commands.switches;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class SwitchToExitCommand extends SwitchCommand {
    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Dukemon says bye!";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

    @Override
    public ModeEnum getNewMode(ModeEnum old) {
        return ModeEnum.Exit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchToExitCommand); // instanceof handles nulls
    }
}
