package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;


/**
 * Adds an Event to the schedule of a person.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "addevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "[" + PREFIX_NAME + "NAME] "
            + PREFIX_EVENTNAME + "EVENT_NAME "
            + PREFIX_TIMING + "DATE - START_TIME - END_TIME - [LOCATION]"
            + "\n" + "Note: Omitting NAME will add event to user"
            + "\n" + "Date format: ddMMyyyy     Time format: HHmm";

    public static final String MESSAGE_SUCCESS = "New event added: %s";
    public static final String MESSAGE_FAILURE = "Unable to add event: %s";

    public static final String MESSAGE_WRONG_TIMINGS = "Invalid timing arguments\n"
            + "Format: DATE - START_TIME - END_TIME - [LOCATION]"
            + "\n" + "Date format: ddMMyyyy     Time format: HHmm";
    public static final String MESSAGE_UNABLE_TO_FIND_PERSON = "Unable to find person";
    public static final String MESSAGE_CLASH_IN_EVENTS = "Clash in events";

    public final Event event;
    public final Name name;

    public AddEventCommand(Name name, Event event) {
        this.event = event;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (event == null) {
            return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_WRONG_TIMINGS));
        } else {

            try {
                if (name == null) {
                    model.addEvent(event);
                    model.updateDisplayWithUser(LocalDateTime.now(), ScheduleState.PERSON);
                } else {
                    model.addEvent(name, event);
                    model.updateDisplayWithPerson(name, LocalDateTime.now(), ScheduleState.PERSON);
                }
                return new CommandResultBuilder(String.format(MESSAGE_SUCCESS, event.getEventName())).build();

            } catch (PersonNotFoundException e) {
                return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_UNABLE_TO_FIND_PERSON)).build();
            } catch (EventClashException e) {
                return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_CLASH_IN_EVENTS)).build();
            }
        }
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof AddEventCommand)) {
            return false;
        } else if (((AddEventCommand) command).event.equals(this.event)
                && ((AddEventCommand) command).name.equals(this.name)) {
            return true;
        } else {
            return false;
        }
    }
}
