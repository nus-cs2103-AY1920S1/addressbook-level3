package seedu.address.logic.events.add;

import seedu.address.logic.commands.AddDayCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.system.DeleteDaysCommand;
import seedu.address.logic.events.Event;

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
        return new AddDayCommand(numberOfDays);
    }
}
