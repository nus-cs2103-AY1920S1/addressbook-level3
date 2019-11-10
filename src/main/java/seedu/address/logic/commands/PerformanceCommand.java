package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_TIMING;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.performance.Event;
import seedu.address.model.performance.Record;
import seedu.address.model.performance.Timing;
import seedu.address.model.person.Person;
import seedu.address.ui.feature.Feature;

/**
 * Records a player's performance under a certain event.
 */
public class PerformanceCommand extends Command {

    public static final String COMMAND_WORD = "performance";
    public static final String MESSAGE_SUCCESS = "Performance record added for %1$s under %2$s event, on "
        + "%3$s with a timing of %4$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a player performance for an event to Athletick.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_EVENT + "EVENT "
            + PREFIX_DATE + "DDMMYYYY "
            + PREFIX_TIMING + "SECONDS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT + "freestyle 50m "
            + PREFIX_DATE + "02102019 "
            + PREFIX_TIMING + "30.32";

    private final Index index;
    private final String event;
    private final AthletickDate date;
    private final Timing time;

    /**
     * Creates a PerformanceCommand to add the record under the event.
     */
    public PerformanceCommand(Index index, String event, AthletickDate date, Timing time) {
        this.index = index;
        this.event = event;
        this.date = date;
        this.time = time;
    }
    public AthletickDate getDate() {
        return this.date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Event createdEvent = new Event(event);

        // if event does not exist
        if (!model.hasEvent(createdEvent)) {
            throw new CommandException(String.format(Event.MESSAGE_NO_SUCH_EVENT, event));
        }

        Person athlete = lastShownList.get(index.getZeroBased());

        // if record already exists (same athlete, same event, same day)
        if (model.getEvent(event).doesAthleteHavePerformanceOn(date, athlete)) {
            throw new CommandException(String.format(
                Event.MESSAGE_RECORD_EXISTS, athlete.getName().fullName, date, event));
        }

        Record record = new Record(date, time);
        date.setType(2);
        model.addRecord(event, athlete, record);
        return new CommandResult(
            String.format(MESSAGE_SUCCESS, athlete.getName().fullName, event, date, time),
                new Feature("calendar"), date, model);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
    @Override
    public String toString() {
        return "'Add Performance " + this.event + " " + this.date + " " + this.time + "' Command";
    }
}
