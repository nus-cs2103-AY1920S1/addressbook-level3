package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Switches the current view to the desired view.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the desired view.\n"
            + "Parameters: KEYWORD (T,E,R,C)\n"
            + "Example: " + COMMAND_WORD + " E";

    public static final String MESSAGE_SUCCESS = "Switched view to %1$s";

    private final String targetView;
    private final String targetList;

    public ShowCommand(String targetView) {
        this.targetView = targetView;
        switch(targetView) {
        case "T":
            this.targetList = "task";
            break;
        case "E":
            this.targetList = "event";
             break;
        case "R":
             this.targetList = "reminder";
             break;
        case "C":
             this.targetList = "event";
             break;
        default:
             this.targetList = null;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateViewList(targetList);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetView), targetView);
    }
}
