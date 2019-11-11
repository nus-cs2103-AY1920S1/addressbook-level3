package seedu.planner.logic.events.add;

import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
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
    private final Logger logger = LogsCenter.getLogger(AddDayEvent.class);

    public AddDayEvent(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    /**
     * A method to undo the effects of an AddDayCommand.
     * @return returns DeleteDaysCommand.
     */
    public UndoableCommand undo() {
        logger.info(String.format("----------------[UNDOING][%s]", this));
        return new DeleteDaysCommand(numberOfDays);
    }

    /**
     * A method to redo the effects of an AddDayCommand.
     * @return returns AddDayCommand with initial user input parameter.
     */
    public UndoableCommand redo() {
        logger.info(String.format("----------------[REDOING][%s]", this));
        return new AddDayCommand(numberOfDays, true);
    }

    @Override
    public String toString() {
        return "ADD DAY EVENT";
    }
}
