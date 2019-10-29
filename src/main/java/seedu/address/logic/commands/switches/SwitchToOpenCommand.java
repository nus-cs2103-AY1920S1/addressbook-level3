package seedu.address.logic.commands.switches;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class SwitchToOpenCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "open";

    public static final String MESSAGE_HOME_ACKNOWLEDGEMENT = "Opening bank";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_HOME_ACKNOWLEDGEMENT, false, false);
    }

    public ModeEnum getNewMode(ModeEnum old) {
        return ModeEnum.OPEN;
    }
}
