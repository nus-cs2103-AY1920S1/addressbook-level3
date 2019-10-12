package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.logic.commands.commandresult.TabCommandResult;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Model;

/**
 * Switches the main viewing tab to either the Dashboard, Online or Offline tab.
 */
public class TabCommand extends Command {

    public static final String COMMAND_WORD = "tab";

    public static final String MESSAGE_SWITCH_ACKNOWLEDGEMENT = "Switching view to tab %1$s.";
    public static final String MESSAGE_INVALID_INDEX = "Tab index should be 1, 2, or 3.";
    public static final String MESSAGE_INVALID_KEYWORD = "Tab keyword should be dash, on, or off.";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Switches to the tab specified by the given index or keyword. "
            + "Dashboard - '1' or 'dash'. Online - '2' or 'on'. Offline - '3' or 'off'.\n"
            + "Parameter: INDEX or KEYWORD\n"
            + "Example: " + COMMAND_WORD + " 1 \n";

    private final Tab tab;

    public TabCommand(Tab tab) {
        requireNonNull(tab);

        this.tab = tab;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        boolean isSwitchToOnline = false;
        boolean isSwitchToOffline = false;
        boolean isSwitchToDashboard = false;

        switch (tab) {
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
            assert false : "execute forced to handle invalid tab type.";
            break;
        }

        return new TabCommandResult(String.format(MESSAGE_SWITCH_ACKNOWLEDGEMENT, tab.toString()),
                isSwitchToDashboard, isSwitchToOnline, isSwitchToOffline);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TabCommand
                && ((TabCommand) other).tab == this.tab);
    }


    /**
     * Represents the tabs available.
     */
    public static enum Tab {
        DASHBOARD,
        ONLINE,
        OFFLINE;
    }

}
