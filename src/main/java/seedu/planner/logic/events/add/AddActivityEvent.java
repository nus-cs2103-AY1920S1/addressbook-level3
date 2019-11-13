package seedu.planner.logic.events.add;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
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
    private final Logger logger = LogsCenter.getLogger(AddActivityEvent.class);

    public AddActivityEvent(Activity activityAdded) {
        this.activityAdded = activityAdded;
    }

    /**
     * A method to undo the effects of an AddActivityCommand.
     * @return returns DeleteActivityCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new DeleteActivityCommand(null, activityAdded);
    }

    /**
     * A method to redo the effects of an AddActivityCommand.
     * @return returns AddActivityCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new AddActivityCommand(activityAdded, true);
    }

    @Override
    public String toString() {
        return "ADD ACTIVITY EVENT";
    }
}
