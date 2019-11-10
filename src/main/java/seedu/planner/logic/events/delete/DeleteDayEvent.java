package seedu.planner.logic.events.delete;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.addcommand.AddDayCommand;
import seedu.planner.logic.commands.deletecommand.DeleteDayCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.day.Day;
//@@author OneArmyj
/**
 * An event representing a 'delete day' command.
 */
public class DeleteDayEvent implements Event {
    private final Index index;
    private final Day deletedDay;

    public DeleteDayEvent(Index index, Day deletedDay) {
        this.index = index;
        this.deletedDay = deletedDay;
    }

    public UndoableCommand undo() {
        return new AddDayCommand(index, deletedDay);
    }

    public UndoableCommand redo() {
        return new DeleteDayCommand(index, true);
    }

}


