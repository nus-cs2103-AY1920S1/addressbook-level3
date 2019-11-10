package seedu.address.logic.commands.switches;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;

/**
 * Switches application to home mode.
 */
public class SwitchToHomeCommand extends SwitchCommand {
    public static final String COMMAND_WORD = "home";
    public static final String MESSAGE_HOME_ACKNOWLEDGEMENT = "Going home page as requested\n"
            + "Create or select a bank:\n"
            + "Eg. create mybank\n"
            + "Eg. select sample";;

    public ModeEnum getNewMode(ModeEnum old) {
        return ModeEnum.HOME;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_HOME_ACKNOWLEDGEMENT, false, false);
    }

}
