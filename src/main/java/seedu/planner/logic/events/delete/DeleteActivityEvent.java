package seedu.planner.logic.events.delete;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.addcommand.AddActivityCommand;
import seedu.planner.logic.commands.deletecommand.DeleteActivityCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.activity.Activity;
//@@author OneArmyj
/**
 * An event representing a 'delete activity' command.
 */
public class DeleteActivityEvent implements Event {
    private final Index index;
    private final Activity deletedActivity;

    public DeleteActivityEvent(Index index, Activity deletedActivity) {
        this.index = index;
        this.deletedActivity = deletedActivity;
    }

    public UndoableCommand undo() {
        return new AddActivityCommand(index, deletedActivity);
    }

    public UndoableCommand redo() {
        return new DeleteActivityCommand(index, true);
    }

}
