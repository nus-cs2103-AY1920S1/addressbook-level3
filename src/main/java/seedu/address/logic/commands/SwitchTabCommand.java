package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;

/**
 * Command to handle tab switches.
 */
public class SwitchTabCommand extends Command {
    public static final String COMMAND_WORD = "switch-tab";
    public static final String MESSAGE_SUCCESS = "Tabs switched!";
    public static final String MESSAGE_FAILURE = "No tabs to switch";
    public static final String MESSAGE_USAGE = "Switch tab command does not take in any other arguments!";

    public SwitchTabCommand() {
    }

    @Override
    public CommandResult execute(Model model) {

        ScheduleWindowDisplayType state = model.getState();
        if (state == ScheduleWindowDisplayType.HOME) {
            //model.updateSidePanelDisplay(SidePanelDisplayType.TABS);
            CommandResult commandResult =
                    new CommandResult(MESSAGE_SUCCESS, false, false, false);
            commandResult.setIsSwitchTabs(true);
            return commandResult;
        } else {
            return new CommandResult(MESSAGE_FAILURE, false, false, false);
        }

    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof SwitchTabCommand);
    }
}
