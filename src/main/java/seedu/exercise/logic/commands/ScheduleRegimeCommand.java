package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.exceptions.ScheduleException;
import seedu.exercise.model.Model;
import seedu.exercise.model.UniqueResourceList;
import seedu.exercise.model.property.Date;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;

/**
 * Schedules a regime at a specific date.
 */
public class ScheduleRegimeCommand extends ScheduleCommand {

    public static final String MESSAGE_SUCCESS = "Regime %1$s scheduled on %2$s";
    public static final String MESSAGE_REGIME_NOT_FOUND = "Regime %1$s not in regime book";
    public static final String MESSAGE_CONFLICT = "Regime to be scheduled conflicts with another regime. "
        + "Opening resolve window...";

    private Regime regime;
    private Date dateToSchedule;

    public ScheduleRegimeCommand(Name regimeName, Date date) {
        this.regime = new Regime(regimeName, new UniqueResourceList<>());
        dateToSchedule = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkExistenceOfRegime(model);
        Schedule toSchedule = checkSchedulingConflict(model);

        schedule(model, toSchedule);

        return new CommandResult(String.format(MESSAGE_SUCCESS, regime.getRegimeName(), dateToSchedule));
    }

    private void checkExistenceOfRegime(Model model) throws CommandException {
        if (!model.hasRegime(regime)) {
            throw new CommandException(String.format(MESSAGE_REGIME_NOT_FOUND, regime.getRegimeName()));
        }
    }

    /**
     * Checks for scheduling conflicts and returns a valid schedule if no conflicts are found.
     *
     * @throws ScheduleException when there is another regime scheduled on the same date as {@code dateToSchedule}
     */
    private Schedule checkSchedulingConflict(Model model) throws ScheduleException {
        int indexOfRegime = model.getRegimeIndex(regime);
        Regime regimeToSchedule = model.getFilteredRegimeList().get(indexOfRegime);
        Schedule toSchedule = new Schedule(regimeToSchedule, dateToSchedule);

        if (model.hasSchedule(toSchedule)) {
            throw new ScheduleException(MESSAGE_CONFLICT);
        }

        return toSchedule;
    }

    private void schedule(Model model, Schedule toSchedule) {
        model.addSchedule(toSchedule);
    }
}
