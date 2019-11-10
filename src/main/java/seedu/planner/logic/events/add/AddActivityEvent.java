package seedu.planner.logic.events.add;

import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.addcommand.AddActivityCommand;
import seedu.planner.logic.commands.deletecommand.DeleteActivityCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.activity.Activity;
//@@author OneArmyj
/**
 * An event representing an 'add activity' command.
 */
public class AddActivityEvent implements Event {
    private final Activity activityAdded;

    public AddActivityEvent(Activity activityAdded) {
        this.activityAdded = activityAdded;
    }

    public UndoableCommand undo() {
        return new DeleteActivityCommand(null, activityAdded);
    }

    public UndoableCommand redo() {
        return new AddActivityCommand(activityAdded, true);
    }
}
