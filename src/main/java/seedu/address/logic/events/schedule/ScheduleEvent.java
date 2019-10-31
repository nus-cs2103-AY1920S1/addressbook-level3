package seedu.address.logic.events.schedule;

import java.time.LocalTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.UnscheduleCommand;
import seedu.address.logic.events.Event;

/**
 * An event representing a 'schedule' command.
 */
public class ScheduleEvent implements Event {
    private final Index activityIndex;
    private final LocalTime startTime;
    private final Index dayIndex;

    public ScheduleEvent(Index activityIndex, LocalTime startTime, Index dayIndex) {
        this.activityIndex = activityIndex;
        this.startTime = startTime;
        this.dayIndex = dayIndex;
    }

    public UndoableCommand undo() {
        return new UnscheduleCommand(activityIndex, dayIndex);
    }

    public UndoableCommand redo() {
        return new ScheduleCommand(activityIndex, startTime, dayIndex);
    }
}
