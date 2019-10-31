package seedu.address.logic.events;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AutoScheduleCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.OptimiseBudgetCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.UnscheduleCommand;
import seedu.address.logic.events.add.AddEventFactory;
import seedu.address.logic.events.clear.ClearEvent;
import seedu.address.logic.events.delete.DeleteEventFactory;
import seedu.address.logic.events.edit.EditEventFactory;
import seedu.address.logic.events.exceptions.EventException;
import seedu.address.logic.events.optimise.OptimiseBudgetEvent;
import seedu.address.logic.events.schedule.AutoScheduleEvent;
import seedu.address.logic.events.schedule.ScheduleEvent;
import seedu.address.logic.events.schedule.UnscheduleEvent;
import seedu.address.model.Model;

/**
 * A factory class to generate the corresponding events according to the command input.
 */
public class EventFactory {
    private static final String MESSAGE_COMMAND_ERROR = "\'%1$s\' command is not undoable.";

    /**
     * A static method to parse the command and generate the corresponding event.
     * @param command Command to be parsed.
     * @param model Current model of the application.
     * @return Corresponding event for the command parsed.
     * @throws EventException
     */
    public static Event parse(UndoableCommand command, Model model) throws EventException {
        requireNonNull(command);
        requireNonNull(model);

        String commandWord = command.getCommandWord();

        switch (commandWord) {
        case (AddCommand.COMMAND_WORD):
            return AddEventFactory.parse((AddCommand) command);

        case (DeleteCommand.COMMAND_WORD):
            return DeleteEventFactory.parse((DeleteCommand) command, model);

        case (EditCommand.COMMAND_WORD):
            return EditEventFactory.parse((EditCommand) command, model);

        case (ClearCommand.COMMAND_WORD):
            return generateClearEvent(model);

        case(ScheduleCommand.COMMAND_WORD):
            return generateScheduleEvent((ScheduleCommand) command);

        case(UnscheduleCommand.COMMAND_WORD):
            return generateUnscheduleEvent((UnscheduleCommand) command, model);

        case(AutoScheduleCommand.COMMAND_WORD):
            return generateAutoScheduleEvent((AutoScheduleCommand) command, model);

        case(OptimiseBudgetCommand.COMMAND_WORD):
            return generateOptimizeBudgetEvent((OptimiseBudgetCommand) command, model);

        default:
            throw new EventException(String.format(MESSAGE_COMMAND_ERROR, commandWord));
        }
    }

    private static Event generateClearEvent(Model model) {
        return new ClearEvent(model);
    }

    private static Event generateScheduleEvent(ScheduleCommand command) {
        return new ScheduleEvent(command.getActivityIndex(), command.getStartTime(), command.getDayIndex());
    }

    private static Event generateUnscheduleEvent(UnscheduleCommand command, Model model) throws EventException {
        return new UnscheduleEvent(command.getActivityIndexToUnschedule(), command.getDayIndex(), model);
    }

    private static Event generateAutoScheduleEvent(AutoScheduleCommand command, Model model) {
        return new AutoScheduleEvent(command.getDraftSchedule(), command.getAddress(), command.getDays(), model);
    }

    private static Event generateOptimizeBudgetEvent(OptimiseBudgetCommand command, Model model) {
        return new OptimiseBudgetEvent(command.getDayIndex(), model);
    }
}
