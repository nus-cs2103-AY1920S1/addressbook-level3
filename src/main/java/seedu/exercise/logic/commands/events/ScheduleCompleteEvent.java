package seedu.exercise.logic.commands.events;

import java.util.Collection;

import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Schedule;

/**
 * Represents a particular schedule complete event that can be redone or undone.
 */
public class ScheduleCompleteEvent implements Event {

    public static final String KEY_TO_COMPLETE = "toComplete";
    private static final String EVENT_DESCRIPTION = "Completed: Regime %1$s\nOn: %2$s";

    private final Schedule toComplete;

    /**
     * Creates an ScheduleCompleteEvent to store the particular event of a schedule being marked as completed.
     *
     * @param eventPayload a data carrier that stores the essential information for undo and redo
     */
    public ScheduleCompleteEvent(EventPayload<Schedule> eventPayload) {
        this.toComplete = (Schedule) eventPayload.get(KEY_TO_COMPLETE);
    }

    @Override
    public void undo(Model model) {
        Collection<Exercise> scheduledExercises = toComplete.getExercises();
        ReadOnlyResourceBook<Exercise> exerciseBook = model.getExerciseBookData();
        for (Exercise exercise : scheduledExercises) {
            if (exerciseBook.hasResource(exercise)) {
                exerciseBook.removeResource(exercise);
            }
        }

        ReadOnlyResourceBook<Schedule> scheduleBook = model.getAllScheduleData();
        scheduleBook.addResource(toComplete);
    }

    @Override
    public void redo(Model model) {
        model.completeSchedule(toComplete);
    }

    @Override
    public String toString() {
        return String.format(EVENT_DESCRIPTION,
                toComplete.getRegimeName(),
                toComplete.getDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCompleteEvent // instanceof handles nulls
                && toComplete.equals(((ScheduleCompleteEvent) other).toComplete));
    }
}
