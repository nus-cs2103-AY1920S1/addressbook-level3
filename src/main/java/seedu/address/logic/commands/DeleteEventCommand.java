package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.EventNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes an Event from the Schedule of a Person.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_NAME + " PERSON_NAME" + " "
            + PREFIX_EVENTNAME + "EVENTNAME";

    public static final String MESSAGE_SUCCESS = "Delete event success: %s deleted";
    public static final String MESSAGE_FAILURE = "Unable to delete event: %s";

    public static final String MESSAGE_EVENT_NOT_FOUND = "Event does not exist";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person does not exist";

    private Name name;
    private String eventName;

    public DeleteEventCommand(Name name, String eventName) {
        requireNonNull(eventName);

        this.name = name;
        this.eventName = eventName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {

            if (name == null) {
                model.deleteEvent(eventName);
                model.updateScheduleWindowDisplay(LocalDateTime.now(), ScheduleWindowDisplayType.PERSON);

            } else {
                model.deleteEvent(name, eventName);
                model.updateScheduleWindowDisplay(name, LocalDateTime.now(), ScheduleWindowDisplayType.PERSON);

            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, eventName));

        } catch (EventNotFoundException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_EVENT_NOT_FOUND));
        } catch (PersonNotFoundException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND));
        }

    }

    @Override
    public boolean equals(Command command) {
        return false;
    }
}
