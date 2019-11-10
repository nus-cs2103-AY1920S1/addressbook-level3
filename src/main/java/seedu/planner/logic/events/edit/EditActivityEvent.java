package seedu.planner.logic.events.edit;

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

    public EditActivityEvent(Index index, EditActivityDescriptor editInfo, Activity oldActivity) {
        this.index = index;
        this.editInfo = editInfo;
        this.oldActivity = oldActivity;
    }

    public UndoableCommand undo() {
        return new EditActivityCommand(index, null, oldActivity);
    }

    public UndoableCommand redo() {
        return new EditActivityCommand(index, editInfo, true);
    }

}

