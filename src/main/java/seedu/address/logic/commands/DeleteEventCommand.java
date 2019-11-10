package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.EventNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes an Event from the Schedule of a Person.
 */
public class DeleteEventCommand extends Command {

    public static final String COMMAND_WORD = "deleteevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "[" + PREFIX_NAME + " PERSON_NAME]" + " "
            + PREFIX_EVENTNAME + "EVENT_NAME"
            + "\n" + "Note: Omitting PERSON_NAME will delete event from user";

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
                model.updateDisplayWithUser(LocalDateTime.now(), ScheduleState.PERSON);

            } else {
                model.deleteEvent(name, eventName);
                model.updateDisplayWithPerson(name, LocalDateTime.now(), ScheduleState.PERSON);

            }

            return new CommandResultBuilder(String.format(MESSAGE_SUCCESS, eventName)).build();

        } catch (EventNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_EVENT_NOT_FOUND)).build();
        } catch (PersonNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND)).build();
        }

    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof DeleteEventCommand)) {
            return false;
        } else if (((DeleteEventCommand) command).name.equals(this.name)
                && ((DeleteEventCommand) command).eventName.equals(this.eventName)) {
            return true;
        } else {
            return false;
        }
    }
}
