package seedu.elisa.logic.commands;

import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.exceptions.IllegalListException;

/**
 * Toggle the state of ELISA between priority and non-priority mode.
 */
public class PriorityCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "priority";
    public static final String MESSAGE_USAGE = "Activates the priority mode of this application.\n"
            + "It can be activated indefinitely by using \"priority\" or priority 10.min.later";
    public static final String FINISHED_ALL_TASKS = "Congrats! You have finished all your tasks."
            + " Taking you out of priority mode now.";
    public static final String TIME_OUT = "Oops, guess you are out of time. Hope you have done enough!";

    private static final String PRIORITY_MODE_OFF = "Priority mode deactivated! Not so stressed anymore, are you?";
    private static final String PRIORITY_MODE_ON = "Priority mode activated, just manage this one task, that'll do.";
    private static final String NO_TASK_TO_DO = "You have no incomplete task. Go out and enjoy the sun.";
    private static final String PRIORITY_MODE_ERROR = "Priority mode can only be activated on task pane";

    @Override
    public CommandResult execute(ItemModel model) {
        try {
            boolean status = model.togglePriorityMode();
            return new CommandResult(status ? PRIORITY_MODE_ON :
                    (model.getExitStatus() == null ? PRIORITY_MODE_OFF : NO_TASK_TO_DO));
        } catch (IllegalListException e) {
            return new CommandResult(PRIORITY_MODE_ERROR);
        }
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        try {
            model.togglePriorityMode();
        } catch (IllegalListException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
