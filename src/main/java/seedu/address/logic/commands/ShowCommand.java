package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Switches the view to the desired view.
 */
public class ShowCommand extends Command {
    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the desired view and the list it contains."
            + "Parameters: KEYWORD"
            + "Example: " + COMMAND_WORD + " E";

    public static final String MESSAGE_INVALIDVIEW = "This view is invalid. \nThe only views available are: \n"
            + "  'T' for Task list\n  'E' for Event list\n  'C' for Event calendar\n  'R' for Reminder list.";
    public static final String MESSAGE_SUCCESS = "Showing %1$s";

    private final String targetView;
    private final String targetList;

    public ShowCommand(String targetView) throws CommandException {
        this.targetView = targetView;
        switch (targetView) {
            case "T":
                this.targetList = "task";
                break;
            case "E":
                this.targetList = "event";
                break;
            case "C":
                this.targetList = "event";
                break;
            case "R":
                this.targetList = "reminder";
                break;
            default:
                throw new CommandException(MESSAGE_INVALIDVIEW);
        }
    }

    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateViewingList(targetList);
        // Switch tabs

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetView), targetView);
    }
}
