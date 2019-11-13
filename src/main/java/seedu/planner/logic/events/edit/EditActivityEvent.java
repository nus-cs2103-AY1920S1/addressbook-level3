package seedu.planner.logic.events.edit;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.editcommand.EditActivityCommand;
import seedu.planner.logic.commands.editcommand.EditActivityCommand.EditActivityDescriptor;
import seedu.planner.logic.events.Event;
import seedu.planner.model.activity.Activity;
//@@author OneArmyj
/**
 * An event representing a 'edit activity' command.
 */
public class EditActivityEvent implements Event {
    private final Index index;
    private final EditActivityDescriptor editInfo;
    private final Activity oldActivity;
    private final Logger logger = LogsCenter.getLogger(EditActivityEvent.class);

    public EditActivityEvent(Index index, EditActivityDescriptor editInfo, Activity oldActivity) {
        this.index = index;
        this.editInfo = editInfo;
        this.oldActivity = oldActivity;
    }

    /**
     * A method to undo the effects of an EditActivityCommand.
     * @return returns EditActivityCommand with the old Activity previously in the model.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new EditActivityCommand(index, null, oldActivity);
    }

    /**
     * A method to undo the effects of an EditActivityCommand.
     * @return returns EditActivityCommand with the initial user input parameters.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new EditActivityCommand(index, editInfo, true);
    }

    @Override
    public String toString() {
        return "EDIT ACTIVITY EVENT";
    }
}

