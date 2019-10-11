package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;

/**
 * Switches the current view to the desired view.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String TASK_VIEW_COMMAND = "T";
    public static final String EVENT_VIEW_COMMAND = "E";
    public static final String REMINDER_VIEW_COMMAND = "R";
    public static final String CALENDAR_VIEW_COMMAND = "C";

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
            case TASK_VIEW_COMMAND:
                this.targetList = TASK_VIEW_COMMAND; //"TASK"
                break;
            case EVENT_VIEW_COMMAND:
            case CALENDAR_VIEW_COMMAND:
                this.targetList = EVENT_VIEW_COMMAND; //"EVENT"
                break;
            case REMINDER_VIEW_COMMAND:
                this.targetList = REMINDER_VIEW_COMMAND; //"REMINDER"
                break;
            default:
                this.targetList = null;
        }
    }

    @Override
    public CommandResult execute(ItemModel model) throws CommandException {
        requireNonNull(model);
        model.setVisualList(targetList); // should be T/E/R
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetView), targetView);
    }
}

