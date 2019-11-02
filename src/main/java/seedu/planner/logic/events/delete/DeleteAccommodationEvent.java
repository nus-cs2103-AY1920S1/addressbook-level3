package seedu.planner.logic.events.delete;

import java.util.List;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.AddAccommodationCommand;
import seedu.planner.logic.commands.DeleteAccommodationCommand;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.logic.events.exceptions.EventException;
import seedu.planner.model.Model;
import seedu.planner.model.accommodation.Accommodation;

/**
 * An event representing a 'delete accommodation' command.
 */
public class DeleteAccommodationEvent implements Event {
    private final Index index;
    private final Accommodation deletedAccommodation;

    public DeleteAccommodationEvent(Index index, Model model) throws EventException {
        this.index = index;
        this.deletedAccommodation = generateDeletedAccommodation(model);
    }

    public UndoableCommand undo() {
        return new AddAccommodationCommand(index, deletedAccommodation);
    }

    public UndoableCommand redo() {
        return new DeleteAccommodationCommand(index);
    }

    /**
     * A method to retrieve the Accommodation object that is going to be deleted.
     * @param model Current model in the application.
     * @return Accommodation to be deleted.
     */
    private Accommodation generateDeletedAccommodation(Model model) throws EventException {
        List<Accommodation> lastShownList = model.getFilteredAccommodationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new EventException(Messages.MESSAGE_INVALID_ACCOMMODATION_DISPLAYED_INDEX);
        }

        Accommodation accommodationToDelete = lastShownList.get(index.getZeroBased());
        return accommodationToDelete;
    }
}
