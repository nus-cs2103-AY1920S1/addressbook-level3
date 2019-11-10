package seedu.planner.logic.events;

import static java.util.Objects.requireNonNull;

import seedu.planner.logic.commands.ClearCommand;
import seedu.planner.logic.commands.OptimiseCommand;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.addcommand.AddCommand;
import seedu.planner.logic.commands.deletecommand.DeleteCommand;
import seedu.planner.logic.commands.editcommand.EditCommand;
import seedu.planner.logic.commands.schedulecommand.AutoScheduleCommand;
import seedu.planner.logic.commands.schedulecommand.ScheduleCommand;
import seedu.planner.logic.commands.schedulecommand.UnscheduleCommand;
import seedu.planner.logic.events.add.AddEventFactory;
import seedu.planner.logic.events.clear.ClearEvent;
import seedu.planner.logic.events.delete.DeleteEventFactory;
import seedu.planner.logic.events.edit.EditEventFactory;
import seedu.planner.logic.events.optimise.OptimiseBudgetEvent;
import seedu.planner.logic.events.schedule.AutoScheduleEvent;
import seedu.planner.logic.events.schedule.ScheduleEvent;
import seedu.planner.logic.events.schedule.UnscheduleEvent;
import seedu.planner.model.Model;
//@@author OneArmyj
/**
 * A factory class to generate the corresponding events according to the command input.
 */
public class EventFactory {

    /**
     * A static method to parse the command and generate the corresponding event.
     * @param command Command to be parsed.
     * @return Corresponding event for the command parsed.
     */
    public static Event parse(UndoableCommand command, Model model) {
        requireNonNull(command);

        String commandWord = command.getCommandWord();

        switch (commandWord) {
        case (AddCommand.COMMAND_WORD):
            return AddEventFactory.parse((AddCommand) command);

        case (DeleteCommand.COMMAND_WORD):
            return DeleteEventFactory.parse((DeleteCommand) command);

        case (EditCommand.COMMAND_WORD):
            return EditEventFactory.parse((EditCommand) command);

        case (ClearCommand.COMMAND_WORD):
            return generateClearEvent(model);

        case(ScheduleCommand.COMMAND_WORD):
            return generateScheduleEvent((ScheduleCommand) command, model);

        case(UnscheduleCommand.COMMAND_WORD):
            return generateUnscheduleEvent((UnscheduleCommand) command, model);

        case(AutoScheduleCommand.COMMAND_WORD):
            return generateAutoScheduleEvent((AutoScheduleCommand) command, model);

        case(OptimiseCommand.COMMAND_WORD):
            return generateOptimizeBudgetEvent((OptimiseCommand) command, model);

        default:
            return null;
        }
    }

    private static Event generateClearEvent(Model model) {
        return new ClearEvent(model);
    }

    private static Event generateScheduleEvent(ScheduleCommand command, Model model) {
        return new ScheduleEvent(command.getActivityIndex(), command.getStartTime(), command.getDayIndex(), model);
    }

    private static Event generateUnscheduleEvent(UnscheduleCommand command, Model model) {
        return new UnscheduleEvent(command.getActivityIndexToUnschedule(), command.getDayIndex(), model);
    }

    private static Event generateAutoScheduleEvent(AutoScheduleCommand command, Model model) {
        return new AutoScheduleEvent(command.getDraftSchedule(), command.getAddress(), command.getDays(), model);
    }

    private static Event generateOptimizeBudgetEvent(OptimiseCommand command, Model model) {
        return new OptimiseBudgetEvent(command.getDayIndex(), model);
    }
}
