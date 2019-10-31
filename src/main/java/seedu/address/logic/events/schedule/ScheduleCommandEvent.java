package seedu.address.logic.events.schedule;

import java.time.LocalTime;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.UnscheduleCommand;
import seedu.address.logic.events.Event;

public class ScheduleCommandEvent implements Event {
    private final Index activityIndex;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Index dayIndex;

    public ScheduleCommandEvent(Index activityIndex, LocalTime startTime, LocalTime endTime, Index dayIndex) {
        this.activityIndex = activityIndex;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayIndex = dayIndex;
    }

    public UndoableCommand undo() {
        return new UnscheduleCommand(activityIndex, dayIndex);
    }

    public UndoableCommand redo() {
        return new ScheduleCommand(activityIndex, startTime, endTime, dayIndex);
    }
}