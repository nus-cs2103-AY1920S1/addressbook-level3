package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;


/**
 * Adds an Event to the schedule of a person.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "addevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " NAME "
            + PREFIX_EVENTNAME + " EVENTNAME "
            + PREFIX_TIMING + " STARTTIME-ENDTIME-LOCATION"
            + "\n"
            + "Time format: ddMMyyyy:HHmm";

    public static final String MESSAGE_SUCCESS = "New event added: %s";
    public static final String MESSAGE_FAILURE = "Unable to add event: %s";

    public static final String MESSAGE_WRONG_TIMINGS = "Invalid timing arguments\n"
            + "Time format: ddMMyyyy:HHmm";
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
                    model.updateScheduleWindowDisplay(LocalDateTime.now(), ScheduleWindowDisplayType.PERSON);

                } else {
                    model.addEvent(name, event);
                    model.updateScheduleWindowDisplay(name, LocalDateTime.now(), ScheduleWindowDisplayType.PERSON);
                }

                // updates main window
                model.updateScheduleWindowDisplay(name, LocalDateTime.now(), ScheduleWindowDisplayType.PERSON);

                // updates side panel
                model.updateSidePanelDisplay(SidePanelDisplayType.PERSON);

                return new CommandResult(String.format(MESSAGE_SUCCESS, event.getEventName().trim()));

            } catch (PersonNotFoundException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_UNABLE_TO_FIND_PERSON));
            } catch (EventClashException e) {
                return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_CLASH_IN_EVENTS));
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
