package seedu.address.logic.events.delete;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddActivityCommand;
import seedu.address.logic.commands.DeleteActivityCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.events.Event;
import seedu.address.model.Model;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * An event representing a 'delete activity' command.
 */
public class DeleteActivityEvent implements Event {
    private final Index index;
    private final Activity deletedActivity;

    public DeleteActivityEvent(Index index, Model model) {
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
    private Activity generateDeletedActivity(Model model) {
        List<Activity> lastShownList = model.getFilteredActivityList();
        assert(index.getZeroBased() < lastShownList.size());
        Activity activityToDelete = lastShownList.get(index.getZeroBased());
        return activityToDelete;
    }
}
