package seedu.planner.logic.events.delete;

import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.AddActivityCommand;
import seedu.planner.logic.commands.DeleteActivityCommand;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.logic.events.exceptions.EventException;
import seedu.planner.model.Model;
import seedu.planner.model.activity.Activity;

/**
 * An event representing a 'delete activity' command.
 */
public class DeleteActivityEvent implements Event {
    private final Index index;
    private final Activity deletedActivity;

    public DeleteActivityEvent(Index index, Model model) throws EventException {
        this.index = index;
        this.deletedActivity = generateDeletedActivity(model);
    }

    public UndoableCommand undo() {
        return new AddActivityCommand(index, deletedActivity);
    }

    public UndoableCommand redo() {
        return new DeleteActivityCommand(index);
    }

    /**
     * A method to retrieve the Activity object that is going to be deleted.
     * @param model Current model in the application.
     * @return Activity to be deleted.
     */
    private Activity generateDeletedActivity(Model model) throws EventException {
        List<Activity> lastShownList = model.getFilteredActivityList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new EventException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
        }
        Activity activityToDelete = lastShownList.get(index.getZeroBased());
        return activityToDelete;
    }
}
