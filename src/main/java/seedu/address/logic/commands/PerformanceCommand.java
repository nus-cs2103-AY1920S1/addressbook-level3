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

/**
 * Records a player's performance under a certain event.
 */
public class PerformanceCommand extends Command {

    public static final String COMMAND_WORD = "performance";
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

    public static final String getSuccessMessage(Person p, String e, AthletickDate d, Timing t) {
        return "Performance record added for " + p.getName().fullName + " under " + e + " event, on "
            + d.toString() + " with a timing of " + t.toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Event createdEvent = new Event(event);

        if (!model.hasEvent(createdEvent)) {
            throw new CommandException(String.format(Event.MESSAGE_CONSTRAINTS, event));
        }

        Person athlete = lastShownList.get(index.getZeroBased());
        Record record = createRecord();
        date.setType(2);
        model.addRecord(event, athlete, record);
        return new CommandResult(getSuccessMessage(athlete, event, date, time), date, model);
    }

    private Record createRecord() {
        return new Record(date, time);
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
