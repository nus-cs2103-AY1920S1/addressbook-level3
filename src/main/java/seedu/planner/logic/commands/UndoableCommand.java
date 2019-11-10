package seedu.planner.logic.commands;

import seedu.planner.logic.CommandHistory;
import seedu.planner.logic.events.Event;
import seedu.planner.logic.events.EventFactory;
import seedu.planner.model.Model;
//@@author OneArmyj
/**
 * Represent a command then can be undone.
 */
public abstract class UndoableCommand extends Command {
    /**
     * Gets the command word of the undoable command.
     * @return command word of the undoable command.
     */
    public abstract String getCommandWord();

    /**
     * A method to generate a corresponding Event. Updates the undoEventStack and redoEventStack in CommandHistory
     * @param command The Command whose Event is to be generated.
     * @param model Current model of the application
     */
    public void updateEventStack(UndoableCommand command, Model model) {
        Event newEvent = EventFactory.parse(command, model);
        CommandHistory.addToUndoStack(newEvent);
        CommandHistory.clearRedoStack();
    }
}
