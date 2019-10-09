package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.logic.commands.commandresult.TabCommandResult;
import seedu.mark.model.Model;

public class TabCommand extends Command {

    public static final String COMMAND_WORD = "tab";

    public static final String MESSAGE_SWITCH_ACKNOWLEDGEMENT = "Switching view as requested.";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Switches view to the tab identified by the index"
            + "given. Dashboard tab is 1, Online tab is 2, Offline tab is 3.\n"
            + "Parameters: INDEX (must be either 1, 2 or 3)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private static final int DASHBOARD = 1;
    private static final int ONLINE = 2;
    private static final int OFFLINE = 3;

    private final Index index;

    public TabCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) {
        boolean isSwitchToOnline = false, isSwitchToOffline = false, isSwitchToDashboard = false;
        switch (index.getOneBased()) {
        case DASHBOARD:
            isSwitchToDashboard = true;
            break;
        case ONLINE:
            isSwitchToOnline = true;
            break;
        case OFFLINE:
            isSwitchToOffline = true;
            break;
        default:
            break;
        }
        return new TabCommandResult(MESSAGE_SWITCH_ACKNOWLEDGEMENT,
                isSwitchToDashboard, isSwitchToOnline, isSwitchToOffline);
    }

}
