package seedu.planner.logic.events.delete;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
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
    private final Logger logger = LogsCenter.getLogger(DeleteActivityEvent.class);

    public DeleteActivityEvent(Index index, Activity deletedActivity) {
        this.index = index;
        this.deletedActivity = deletedActivity;
    }

    /**
     * A method to undo the effects of an DeleteActivityCommand.
     * @return returns AddActivityCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new AddActivityCommand(index, deletedActivity);
    }

    /**
     * A method to redo the effects of an DeleteActivityCommand.
     * @return returns DeleteActivityCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new DeleteActivityCommand(index, true);
    }

    @Override
    public String toString() {
        return "DELETE ACTIVITY EVENT";
    }
}
