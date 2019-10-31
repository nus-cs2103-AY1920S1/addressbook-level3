package seedu.address.logic.events.delete;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddDayCommand;
import seedu.address.logic.commands.DeleteDayCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.events.Event;
import seedu.address.model.Model;
import seedu.address.model.day.Day;

/**
 * An event representing a 'delete day' command.
 */
public class DeleteDayEvent implements Event {
    private final Index index;
    private final Day deletedDay;

    public DeleteDayEvent(Index index, Model model) {
        this.index = index;
        this.deletedDay = generateDeletedDay(model);
    }

    public UndoableCommand undo() {
        return new AddDayCommand(index, deletedDay);
    }

    public UndoableCommand redo() {
        return new DeleteDayCommand(index);
    }

    /**
     * A method to retrieve the Day object that is going to be deleted.
     * @param model Current model in the application.
     * @return Day to be deleted.
     */
    private Day generateDeletedDay(Model model) {
        List<Day> lastShownList = model.getFilteredItinerary();
        assert(index.getZeroBased() < lastShownList.size());
        Day dayToDelete = lastShownList.get(index.getZeroBased());
        return dayToDelete;
    }
}


