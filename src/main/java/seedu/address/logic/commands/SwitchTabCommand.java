package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplay;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;

/**
 * Command to handle tab switches.
 */
public class SwitchTabCommand extends Command {
    public static final String COMMAND_WORD = "switch-tab";
    public static final String MESSAGE_SUCCESS = "Tabs switched!";
    public static final String MESSAGE_USAGE = "Switch tab command does not take in any other arguments!";

    public SwitchTabCommand() {
    }

    @Override
    public CommandResult execute(Model model) {
        ScheduleWindowDisplay nextDetailDisplay = new ScheduleWindowDisplay();
        if (model.getSidePanelDisplay() == null) {
            model.updateSidePanelDisplay(SidePanelDisplayType.TABS);
        }
        model.updateScheduleWindowDisplay(nextDetailDisplay);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false);
    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof SwitchTabCommand);
    }
}
