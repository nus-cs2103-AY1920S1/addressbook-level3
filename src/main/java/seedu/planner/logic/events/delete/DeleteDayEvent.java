package seedu.planner.logic.events.delete;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
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
    private final Logger logger = LogsCenter.getLogger(DeleteDayEvent.class);

    public DeleteDayEvent(Index index, Day deletedDay) {
        this.index = index;
        this.deletedDay = deletedDay;
    }
    /**
     * A method to undo the effects of an DeleteDayCommand.
     * @return returns AddDayCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new AddDayCommand(index, deletedDay);
    }

    /**
     * A method to redo the effects of an DeleteDayCommand.
     * @return returns DeleteDayCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new DeleteDayCommand(index, true);
    }

    @Override
    public String toString() {
        return "DELETE DAY EVENT";
    }
}


