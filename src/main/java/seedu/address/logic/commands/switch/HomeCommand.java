package seedu.address.logic.commands.switchmode;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.ModeEnum;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class HomeCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_HOME_ACKNOWLEDGEMENT = "Going home as requested";


    @Override
    public ModeEnum getNewMode() {
        return ModeEnum.APP;
    }

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_HOME_ACKNOWLEDGEMENT, false, false);
    }

}
