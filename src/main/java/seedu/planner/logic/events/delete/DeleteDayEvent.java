package seedu.planner.logic.events.delete;

import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.AddDayCommand;
import seedu.planner.logic.commands.DeleteDayCommand;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.logic.events.exceptions.EventException;
import seedu.planner.model.Model;
import seedu.planner.model.day.Day;

/**
 * An event representing a 'delete day' command.
 */
public class DeleteDayEvent implements Event {
    private final Index index;
    private final Day deletedDay;

    public DeleteDayEvent(Index index, Model model) throws EventException {
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
    private Day generateDeletedDay(Model model) throws EventException {
        List<Day> lastShownList = model.getFilteredItinerary();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new EventException(Messages.MESSAGE_INVALID_DAY_DISPLAYED_INDEX);
        }

        Day dayToDelete = lastShownList.get(index.getZeroBased());
        return dayToDelete;
    }
}


