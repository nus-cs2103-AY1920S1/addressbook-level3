package seedu.address.logic.events.add;

import seedu.address.logic.commands.AddActivityCommand;
import seedu.address.logic.commands.DeleteActivityCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.events.Event;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * An event representing an 'add activity' command.
 */
public class AddActivityEvent implements Event {
    private final Activity activityAdded;

    public AddActivityEvent(Activity activityAdded) {
        this.activityAdded = activityAdded;
    }

    public UndoableCommand undo() {
        return new DeleteActivityCommand(activityAdded);
    }

    public UndoableCommand redo() {
        return new AddActivityCommand(activityAdded);
    }
}
