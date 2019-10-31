package seedu.address.logic.events.add;

import seedu.address.logic.commands.AddDayCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteDayCommand;
import seedu.address.logic.events.Event;

public class DeleteDayEvent implements Event {
    private final int ;

    public AddDayEvent(int daysAdded) {
        this.daysAdded = daysAdded;
    }

    public Command undo() {
        return new DeleteDayCommand(dayAdded);
    }

    public Command redo() {
        return new AddDayCommand(dayAdded);
    }
}



