package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.logic.commands.events.ScheduleRegimeEvent.KEY_TO_SCHEDULE;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;

import java.util.Collection;

import seedu.exercise.MainApp;
import seedu.exercise.commons.core.State;
import seedu.exercise.logic.commands.events.EventHistory;
import seedu.exercise.logic.commands.events.EventPayload;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.SortedUniqueResourceList;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.model.util.DateChangerUtil;
import seedu.exercise.ui.ListResourceType;

//@@author t-cheepeng
/**
 * Schedules a regime at a specific date.
 */
public class ScheduleRegimeCommand extends ScheduleCommand implements PayloadCarrierCommand {

    public static final String MESSAGE_SUCCESS = "Regime %1$s scheduled on %2$s.";
    public static final String MESSAGE_REGIME_NOT_FOUND = "Regime %1$s not in regime book.";
    public static final String MESSAGE_CONFLICT = "Regime to be scheduled conflicts with another scheduled regime. "
        + "Opening resolve window...";
    public static final String MESSAGE_DATE_BEFORE_CURRENT_DATE = "Input date falls before today's date. \n"
        + "Please choose a date after the today's date: %1$s";
    public static final String UNIQUE_IDENTIFIER = "scheduleRegime";

    private Regime regime;
    private Date dateToSchedule;
    private EventPayload<Schedule> eventPayload;

    public ScheduleRegimeCommand(Name regimeName, Date date) {
        requireAllNonNull(regimeName, date);

        this.regime = new Regime(regimeName, new SortedUniqueResourceList<>(DEFAULT_EXERCISE_COMPARATOR));
        this.eventPayload = new EventPayload<>();
        dateToSchedule = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkInputDateAfterCurrentDate();
        checkExistenceOfRegime(model);
        Schedule toSchedule = getScheduleFromModel(model);

        if (toSchedule == null) {
            return new CommandResult(MESSAGE_CONFLICT, false, false, true, false);
        }

        schedule(model, toSchedule);
        eventPayload.put(KEY_TO_SCHEDULE, toSchedule);
        EventHistory.getInstance().addCommandToUndoStack(this);
        return new CommandResult(String.format(MESSAGE_SUCCESS, regime.getRegimeName(), dateToSchedule),
                ListResourceType.SCHEDULE);
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
                || (other instanceof ScheduleRegimeCommand // instanceof handles nulls
                && regime.equals(((ScheduleRegimeCommand) other).regime)
                && dateToSchedule.equals(((ScheduleRegimeCommand) other).dateToSchedule));
    }

    private void checkExistenceOfRegime(Model model) throws CommandException {
        if (!model.hasRegime(regime)) {
            throw new CommandException(String.format(MESSAGE_REGIME_NOT_FOUND, regime.getRegimeName()));
        }
    }

    /**
     * Checks if the input date if after the current date as given by {@link Date#getToday()}.
     */
    private void checkInputDateAfterCurrentDate() throws CommandException {
        Date currentDate = Date.getToday();
        if (!Date.isEndDateAfterStartDate(currentDate.toString(), dateToSchedule.toString())) {
            throw new CommandException(String.format(MESSAGE_DATE_BEFORE_CURRENT_DATE, currentDate.toString()));
        }
    }

    /**
     * Checks for scheduling conflicts and returns a valid schedule if no conflicts are found.
     * If a conflict is found, returns a null schedule
     */
    private Schedule getScheduleFromModel(Model model) {
        int indexOfRegime = model.getRegimeIndex(regime);
        Regime regimeToSchedule = getRegimeWithUpdatedExerciseDate(
                model.getSortedRegimeList().get(indexOfRegime));

        Schedule toSchedule = new Schedule(regimeToSchedule, dateToSchedule);

        if (model.hasSchedule(toSchedule)) {
            setConflictState();
            Conflict conflict = buildConflict(model, toSchedule);
            setConflictForModel(model, conflict);
            return null;
        }

        return toSchedule;
    }

    /**
     * Returns a regime with all exercises' Date updated to the given date to schedule.
     */
    private Regime getRegimeWithUpdatedExerciseDate(Regime regime) {
        Collection<Exercise> regimeExercises =
                DateChangerUtil
                        .changeAllDate(regime.getRegimeExercises().asUnmodifiableObservableList(), dateToSchedule);
        SortedUniqueResourceList<Exercise> exercisesWithUpdatedDate =
                new SortedUniqueResourceList<>(DEFAULT_EXERCISE_COMPARATOR);
        for (Exercise exercise : regimeExercises) {
            exercisesWithUpdatedDate.add(exercise);
        }
        return new Regime(regime.getRegimeName(), exercisesWithUpdatedDate);
    }

    private void schedule(Model model, Schedule toSchedule) {
        model.addSchedule(toSchedule);
    }

    private void setConflictState() {
        MainApp.setState(State.IN_CONFLICT);
    }

    private Conflict buildConflict(Model model, Schedule toSchedule) {
        int indexOfScheduled = model.getAllScheduleData().getResourceIndex(toSchedule);
        Schedule scheduled = model.getSortedScheduleList().get(indexOfScheduled);
        return new Conflict(scheduled, toSchedule);
    }

    private void setConflictForModel(Model model, Conflict conflict) {
        model.setConflict(conflict);
    }
}
