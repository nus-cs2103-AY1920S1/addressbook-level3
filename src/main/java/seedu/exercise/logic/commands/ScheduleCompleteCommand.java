package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.commands.events.ScheduleCompleteEvent.KEY_TO_COMPLETE;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.commons.core.index.IndexUtil;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.ui.ListResourceType;

/**
 * Completes a schedule and removes it from the schedule list.
 */
public class ScheduleCompleteCommand extends ScheduleCommand implements PayloadCarrierCommand {

    public static final String MESSAGE_SUCCESS = "Schedule %1$s completed and added to exercise tracker";
    public static final String UNIQUE_IDENTIFIER = "scheduleComplete";

    private Index index;
    private EventPayload<Schedule> eventPayload;

    public ScheduleCompleteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        this.eventPayload = new EventPayload<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkValidIndex(model);
        Schedule toComplete = completeSchedule(model);
        model.updateStatistic();
        eventPayload.put(KEY_TO_COMPLETE, toComplete);
        EventHistory.getInstance().addCommandToUndoStack(this);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Integer.toString(index.getOneBased())),
                ListResourceType.EXERCISE);
    }

    @Override
    public EventPayload<Schedule> getPayload() {
        return eventPayload;
    }

    @Override
    public String getCommandTypeIdentifier() {
        return UNIQUE_IDENTIFIER;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCompleteCommand // instanceof handles nulls
                && index.equals(((ScheduleCompleteCommand) other).index));
    }

    private Schedule completeSchedule(Model model) {
        Schedule toComplete = model.getFilteredScheduleList().get(index.getZeroBased());
        model.completeSchedule(toComplete);
        return toComplete;
    }

    private void checkValidIndex(Model model) throws CommandException {
        if (IndexUtil.isIndexOutOfBounds(index, model.getFilteredScheduleList())) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }
    }
}
