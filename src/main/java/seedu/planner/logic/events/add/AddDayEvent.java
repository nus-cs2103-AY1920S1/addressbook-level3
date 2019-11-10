package seedu.planner.logic.events.add;

import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.addcommand.AddDayCommand;
import seedu.planner.logic.commands.system.DeleteDaysCommand;
import seedu.planner.logic.events.Event;
//@@author OneArmyj
/**
 * An event representing an 'add day' command.
 */
public class AddDayEvent implements Event {
    private final int numberOfDays;

    public AddDayEvent(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public UndoableCommand undo() {
        return new DeleteDaysCommand(numberOfDays);
    }

    public UndoableCommand redo() {
        return new AddDayCommand(numberOfDays, true);
    }
}
