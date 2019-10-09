package seedu.mark.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.commandresult.CommandResult;
import seedu.mark.logic.commands.commandresult.TabCommandResult;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.model.Model;

public class TabCommand extends Command {

    public static final String COMMAND_WORD = "tab";

    public static final String MESSAGE_SWITCH_ACKNOWLEDGEMENT = "Switching view to tab %1$s.";
    public static final String MESSAGE_INVALID_INDEX = "Tab index should be 1, 2, or 3.";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Switches view to the tab identified by the given index. "
            + "The dashboard tab is index 1, the online tab is index 2, and the offline tab is index 3.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index index;

    public TabCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (index.getOneBased() < 1 || index.getOneBased() > 3) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Tab tabType = convertToTab(index);
        boolean isSwitchToOnline = false,
                isSwitchToOffline = false,
                isSwitchToDashboard = false;

        switch (tabType) {
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

        return new TabCommandResult(String.format(MESSAGE_SWITCH_ACKNOWLEDGEMENT, tabType.toString()),
                isSwitchToDashboard, isSwitchToOnline, isSwitchToOffline);
    }

    private Tab convertToTab(Index index) {
        Tab type = null;
        switch (index.getOneBased()) {
        case 1:
            type = Tab.DASHBOARD;
            break;
        case 2:
            type = Tab.ONLINE;
            break;
        case 3:
            type = Tab.OFFLINE;
            break;
        default:
            assert false : "convertToTab forced to handle invalid index.";
            break;
        }

        return type;
    }


    public static enum Tab {
        DASHBOARD,
        ONLINE,
        OFFLINE;
    }

}
