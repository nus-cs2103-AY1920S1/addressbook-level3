package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECORD;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.PerformanceSyntax.PREFIX_EVENT;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.performance.Event;
import seedu.address.model.person.Person;

/**
 * Deletes a record of an event from a person identified using it's displayed index from the address book.
 */
public class DeleteRecordCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + FLAG_RECORD
        + ": Deletes the performance record of a certain event, on a certain day for the person identified by the "
        + "index number used in the displayed person list.\n"
        + "INDEX must be a positive integer.\n"
        + "Parameters: INDEX " + PREFIX_EVENT + "EVENT_NAME " + PREFIX_DATE + "DDMMYYYY\n"
        + "Example: " + COMMAND_WORD + " " + FLAG_RECORD + " 1 "
        + PREFIX_EVENT + "freestyle 50m " + PREFIX_DATE + "02102019";
    public static final String MESSAGE_NO_RECORDS_ON_DAY = "%1$s has no records on %2$s.\n"
        + "Please make sure you have typed in the date correctly.";
    public static final String MESSAGE_SUCCESS = "Record for %1$s on %2$s under %3$s event has been successfully "
        + "deleted.";

    private String eventName;
    private AthletickDate date;
    private Index index;
    private Person athlete;

    public DeleteRecordCommand(Index index, String eventName, AthletickDate date) {
        requireNonNull(index);
        requireNonNull(eventName);
        requireNonNull(date);
        this.index = index;
        this.eventName = eventName;
        this.date = date;
    }

    public AthletickDate getDate() {
        return this.date;
    }
    /**
     * Checks if the event exists, and if there are any records to be deleted.
     */
    public void checkEvent(Model model) throws CommandException {
        if (!model.hasEvent(new Event(eventName))) {
            throw new CommandException(String.format(Event.MESSAGE_NO_SUCH_EVENT, eventName));
        }

        if (!model.getEvent(eventName).doesAthleteHavePerformanceOn(date, athlete)) {
            throw new CommandException(String.format(MESSAGE_NO_RECORDS_ON_DAY, athlete.getName().fullName, date));
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        athlete = lastShownList.get(index.getZeroBased());

        checkEvent(model);

        model.deleteRecord(eventName, athlete, date);
        return new CommandResult(String.format(MESSAGE_SUCCESS, athlete.getName().fullName, date, eventName));
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteRecordCommand)) {
            return false;
        }

        DeleteRecordCommand d = (DeleteRecordCommand) other;

        return eventName.equals(d.eventName)
            && date.equals(d.date)
            && index.equals(d.index);
    }
    @Override
    public String toString() {
        return "'Delete Performance " + this.eventName + " " + this.date + "' Command";
    }
}
