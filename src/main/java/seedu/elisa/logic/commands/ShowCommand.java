package seedu.elisa.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.elisa.commons.exceptions.IllegalValueException;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.item.VisualizeList;

/**
 * Switches the current view to the desired view.
 */
public class ShowCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "show";
    public static final String TASK_VIEW_COMMAND = "T";
    public static final String EVENT_VIEW_COMMAND = "E";
    public static final String REMINDER_VIEW_COMMAND = "R";
    public static final String CALENDAR_VIEW_COMMAND = "C";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the desired view.\n"
            + "Parameters: KEYWORD (T,E,R,C)\n"
            + "Example: " + COMMAND_WORD + " E";

    public static final String MESSAGE_SUCCESS = "Switched view to %1$s, because somebody couldn't use the mouse";

    private final String targetView;
    private final String targetList;
    private VisualizeList beforeSwitch;

    public ShowCommand(String unprocessedView) {
        String targetView = unprocessedView.toUpperCase();
        this.targetView = targetView;

        switch(targetView) {
        case TASK_VIEW_COMMAND:
            this.targetList = TASK_VIEW_COMMAND; //"TASK"
            break;
        case EVENT_VIEW_COMMAND:
            this.targetList = EVENT_VIEW_COMMAND; //"EVENT"
            break;
        case CALENDAR_VIEW_COMMAND:
            this.targetList = CALENDAR_VIEW_COMMAND; //"CALENDAR"
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

        beforeSwitch = model.getVisualList().deepCopy();
        try {
            model.setVisualList(targetList); // should be T/E/R
        } catch (IllegalValueException e) {
            throw new CommandException("Show command format is incorrect. It should be \"show T\"");
        }
        if (!isExecuted()) {
            model.getElisaCommandHistory().clearRedo();
            setExecuted(true);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetView));
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        model.setVisualizeList(beforeSwitch);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
